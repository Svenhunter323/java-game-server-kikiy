package org.menacheri.jetserver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.jetlang.core.Disposable;
import org.junit.Test;
import org.menacheri.jetserver.app.Game;
import org.menacheri.jetserver.app.GameRoom;
import org.menacheri.jetserver.app.PlayerSession;
import org.menacheri.jetserver.app.Session;
import org.menacheri.jetserver.app.impl.GameRoomSession.GameRoomSessionBuilder;
import org.menacheri.jetserver.app.impl.SimpleGame;
import org.menacheri.jetserver.event.Event;
import org.menacheri.jetserver.event.EventDispatcher;
import org.menacheri.jetserver.event.EventHandler;
import org.menacheri.jetserver.event.Events;
import org.menacheri.jetserver.event.impl.EventDispatchers;
import org.menacheri.jetserver.event.impl.JetlangEventDispatcher;
import org.menacheri.jetserver.protocols.Protocol;
import org.menacheri.jetserver.protocols.impl.DummyProtocol;
import org.menacheri.jetserver.util.SessionHandlerLatchCounter;
import org.menacheri.jetserver.util.TestGameRoom;

public class JetlangEventDispatcherTest {
	@Test
	public void specificEventReceiptOnSpecificEventHandler()
			throws InterruptedException {
		EventDispatcher dispatcher = EventDispatchers
				.newJetlangEventDispatcher(null, null);
		final CountDownLatch latch = new CountDownLatch(1);
		dispatcher.addHandler(new EventHandler() {

			@Override
			public void onEvent(Event event) {
				latch.countDown();
			}

			@Override
			public int getEventType() {
				return Events.SESSION_MESSAGE;
			}
		});

		Event event = Events.event(null, Events.SESSION_MESSAGE);
		dispatcher.fireEvent(event);
		assertTrue(latch.await(1, TimeUnit.SECONDS));
	}

	@Test
	public void eventReceiptOnANYTypeEventHandler() throws InterruptedException {
		EventDispatcher dispatcher = EventDispatchers
				.newJetlangEventDispatcher(null, null);
		final CountDownLatch latch = new CountDownLatch(5);
		dispatcher.addHandler(new EventHandler() {

			@Override
			public void onEvent(Event event) {
				latch.countDown();
			}

			@Override
			public int getEventType() {
				return Events.ANY;
			}
		});

		Event event = Events.event(null, Events.SESSION_MESSAGE);
		dispatcher.fireEvent(event);
		event = Events.event(null, Events.NETWORK_MESSAGE);
		dispatcher.fireEvent(event);
		event = Events.event(null, Events.EXCEPTION);
		dispatcher.fireEvent(event);
		event = Events.event(null, Events.LOG_IN);
		dispatcher.fireEvent(event);
		event = Events.event(null, Events.CONNECT);
		dispatcher.fireEvent(event);

		assertTrue(latch.await(1, TimeUnit.SECONDS));
	}

	@Test
	public void nonReceiptOfWrongEventOnSpecificEventHandler()
			throws InterruptedException {
		EventDispatcher dispatcher = EventDispatchers
				.newJetlangEventDispatcher(null, null);
		final CountDownLatch latch = new CountDownLatch(1);
		dispatcher.addHandler(new EventHandler() {

			@Override
			public void onEvent(Event event) {
				latch.countDown();
			}

			@Override
			public int getEventType() {
				return Events.SESSION_MESSAGE;
			}
		});

		Event event = Events.event(null, Events.NETWORK_MESSAGE);
		dispatcher.fireEvent(event);
		assertFalse(latch.await(1, TimeUnit.SECONDS));
	}

	@Test
	public void eventPublishingPerformance() throws InterruptedException {
		EventDispatcher dispatcher = EventDispatchers
				.newJetlangEventDispatcher(null, null);
		int countOfEvents = 5000000;
		final CountDownLatch latch = new CountDownLatch(countOfEvents);
		dispatcher.addHandler(new EventHandler() {

			@Override
			public void onEvent(Event event) {
				latch.countDown();
			}

			@Override
			public int getEventType() {
				return 0;
			}
		});
		long startTime = System.nanoTime();
		for (int i = 1; i <= countOfEvents; i++) {
			Event event = Events.event(null, Events.SESSION_MESSAGE);
			dispatcher.fireEvent(event);
		}
		long time = System.nanoTime() - startTime;
		latch.await(10, TimeUnit.SECONDS);
		System.out.printf("Took  %.3f seconds to send %d int events",
				time / 1e9, countOfEvents);
	}

	@Test
	public void sessionDisconnectValidation() throws InterruptedException {
		// create necessary setup objects.
		Game game = new SimpleGame(1, "Test");
		Protocol dummyProtocol = new DummyProtocol();
		GameRoomSessionBuilder sessionBuilder = new GameRoomSessionBuilder();
		sessionBuilder.parentGame(game).gameRoomName("Zombie_ROOM_1")
				.protocol(dummyProtocol);
		CountDownLatch latch = new CountDownLatch(1);
		AtomicLong counter = new AtomicLong(1l);
		Session gameRoomSession = new TestGameRoom(sessionBuilder, counter,
				latch);
		GameRoom gameRoom = (GameRoom) gameRoomSession;
		PlayerSession playerSession = gameRoom.createPlayerSession(null);
		gameRoom.connectSession(playerSession);
		playerSession.addHandler(new SessionHandlerLatchCounter(playerSession,
				counter, latch));

		// start test
		gameRoom.disconnectSession(playerSession);
		JetlangEventDispatcher gameDispatcher = (JetlangEventDispatcher) gameRoomSession
				.getEventDispatcher();
		assertNoListeners(gameDispatcher);
		Event event = Events.event(null, Events.SESSION_MESSAGE);
		playerSession.onEvent(event);
		assertFalse(latch.await(500, TimeUnit.MILLISECONDS));

		// Connect to another game room
		sessionBuilder.gameRoomName("Zombie_ROOM_2");

		Session gameRoomSession2 = new TestGameRoom(sessionBuilder, counter,
				latch);
		GameRoom gameRoom2 = (GameRoom) gameRoomSession2;
		gameRoom2.connectSession(playerSession);
		playerSession.addHandler(new SessionHandlerLatchCounter(playerSession,
				counter, latch));
		playerSession.onEvent(event);
		assertTrue(latch.await(500, TimeUnit.MILLISECONDS));
	}

	@Test
	public void multiSessionDisconnectValidation() throws InterruptedException {
		// create necessary setup objects.
		Game game = new SimpleGame(1, "Test");
		Protocol dummyProtocol = new DummyProtocol();
		GameRoomSessionBuilder sessionBuilder = new GameRoomSessionBuilder();
		sessionBuilder.parentGame(game).gameRoomName("Zombie_ROOM_1")
				.protocol(dummyProtocol);
		CountDownLatch latch1 = new CountDownLatch(2);
		CountDownLatch latch2 = new CountDownLatch(2);
		AtomicLong counter = new AtomicLong(0l);
		Session gameRoomSession = new TestGameRoom(sessionBuilder, counter,
				latch1);
		GameRoom gameRoom = (GameRoom) gameRoomSession;
		PlayerSession playerSession = gameRoom.createPlayerSession(null);
		PlayerSession playerSession2 = gameRoom.createPlayerSession(null);
		PlayerSession playerSession3 = gameRoom.createPlayerSession(null);
		gameRoom.connectSession(playerSession);
		gameRoom.connectSession(playerSession2);
		gameRoom.connectSession(playerSession3);
		playerSession.addHandler(new SessionHandlerLatchCounter(playerSession,
				counter, latch1));
		playerSession2.addHandler(new SessionHandlerLatchCounter(playerSession,
				counter, latch2));
		playerSession3.addHandler(new SessionHandlerLatchCounter(playerSession,
				counter, latch2));
		// start test
		Event event1 = Events.event(null, Events.DISCONNECT);
		playerSession.onEvent(event1);// disconnect session 1.
		assertFalse(latch1.await(1000, TimeUnit.MILLISECONDS));// This is just a wait
		Event message = Events.event(null, Events.SESSION_MESSAGE);
		playerSession.onEvent(message);
		assertFalse(latch1.await(500, TimeUnit.MILLISECONDS));// Ensure that the message is not sent.
		Event event2 = Events.event(null, Events.DISCONNECT);
		Event event3 = Events.event(null, Events.DISCONNECT);
		playerSession2.onEvent(event2);
		playerSession3.onEvent(event3);

		assertTrue(latch2.await(500, TimeUnit.MILLISECONDS));
		// 1 ondisconnect(session1) + 0 onnetwork(session1) + 2 ondisconnect(session2 and 3)
		assertTrue(counter.get() == 3);
	}

	private void assertNoListeners(JetlangEventDispatcher dispatcher) {
		Map<Integer, List<EventHandler>> listeners = dispatcher
				.getListenersByEventType();
		Collection<List<EventHandler>> eventHandlers = listeners.values();
		for(List<EventHandler> handlers: eventHandlers){
			assertEquals(0,handlers.size());
		}
		List<EventHandler> anyHandler = dispatcher.getHandlers(Events.ANY);
		assertEquals(0, anyHandler.size());
		Map<EventHandler, Disposable>  disposableMap = dispatcher.getDisposableHandlerMap();
		assertEquals(0,disposableMap.entrySet().size());
	}

}
# Change 1 on 2019-06-14
# Change 0 on 2019-07-25
# Change 0 on 2019-08-06
# Change 1 on 2019-08-28
# Change 0 on 2019-10-08
# Change 1 on 2019-12-17
# Change 2 on 2020-11-06
# Change 2 on 2020-11-14
# Change 2 on 2021-06-05
# Change 2 on 2021-06-08
# Change 3 on 2021-07-13
# Change 0 on 2021-08-21
# Change 0 on 2021-10-19
