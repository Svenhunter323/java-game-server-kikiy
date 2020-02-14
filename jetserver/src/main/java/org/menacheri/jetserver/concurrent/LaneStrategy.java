package org.menacheri.jetserver.concurrent;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import org.menacheri.jetserver.app.GameRoom;

/**
 * A session choosing a Lane can be done based on a strategy. The enumeration
 * already has 2 default implementations. Users can implement their own
 * sophisticated ones based on use case.
 * 
 * @author Abraham Menacherry
 * 
 * @param <LANE_ID_TYPE>
 * @param <UNDERLYING_LANE>
 * @param <GROUP>
 */
public interface LaneStrategy<LANE_ID_TYPE, UNDERLYING_LANE, GROUP>
{
	Lane<LANE_ID_TYPE, UNDERLYING_LANE> chooseLane(GROUP group);

	public enum LaneStrategies implements
			LaneStrategy<String, ExecutorService, GameRoom>
	{

		ROUND_ROBIN
		{
			final AtomicInteger currentLane = new AtomicInteger(0);
			final int laneSize = lanes.length;
			
			@Override
			public Lane<String, ExecutorService> chooseLane(GameRoom group)
			{
				currentLane.compareAndSet(laneSize, 0);
				return lanes[currentLane.getAndIncrement()];
			}
		},
		/**
		 * This Strategy groups sessions by GameRoom on a lane. Each time a
		 * session is added, it will check for the session's game room and
		 * return back the lane on which the GameRoom is operating. This way all
		 * sessions will be running on the same thread and inter-session
		 * messaging will be fast synchronous message calls. The disadvantage is
		 * that if there are some GameRooms with huge number of sessions and
		 * some with few, possibility of uneven load on multiple CPU cores is
		 * possible.
		 * 
		 */
		GROUP_BY_ROOM
		{
			private final ConcurrentMap<GameRoom, Lane<String, ExecutorService>> roomLaneMap = new ConcurrentHashMap<GameRoom, Lane<String, ExecutorService>>();
			private final ConcurrentMap<Lane<String, ExecutorService>, AtomicInteger> laneSessionCounter = new ConcurrentHashMap<Lane<String, ExecutorService>, AtomicInteger>();

			@Override
			public Lane<String, ExecutorService> chooseLane(GameRoom room)
			{
				Lane<String, ExecutorService> lane = roomLaneMap.get(room);
				if (null == lane)
				{
					synchronized (laneSessionCounter)
					{
						if (laneSessionCounter.isEmpty())
						{
							for (Lane<String, ExecutorService> theLane : lanes)
							{
								laneSessionCounter.put(theLane,
										new AtomicInteger(0));
							}
						}
						Set<Lane<String, ExecutorService>> laneSet = laneSessionCounter
								.keySet();
						int min = 0;
						for (Lane<String, ExecutorService> theLane : laneSet)
						{
							AtomicInteger counter = laneSessionCounter
									.get(theLane);
							int numOfSessions = counter.get();
							// if numOfSessions is 0, then this lane/thread/core
							// is not tagged to a gameroom and it can be used.
							if (numOfSessions == 0)
							{
								lane = theLane;
								break;
							}
							else
							{
								// reset min for first time.
								if (min == 0)
								{
									min = numOfSessions;
									lane = theLane;
								}
								// If numOfSessions is less than min then
								// replace min with that value, also set the
								// lane.
								if (numOfSessions < min)
								{
									min = numOfSessions;
									lane = theLane;
								}
							}
						}
						
						roomLaneMap.put(room, lane);
					}
				}
				// A new session has chosen the lane, hence the session counter
				// needs to be incremented.
				laneSessionCounter.get(lane).incrementAndGet();
				// TODO how to reduce count on session close?
				return lane;
			}
			
		};

		final Lane<String, ExecutorService>[] lanes = Lanes.LANES
				.getJetLanes();
	}
	
	
}
# Change 1 on 2019-02-15
# Change 0 on 2019-04-03
# Change 1 on 2019-04-03
# Change 0 on 2019-04-25
# Change 3 on 2019-10-12
# Change 1 on 2020-01-17
# Change 2 on 2020-10-17
# Change 1 on 2020-12-12
# Change 0 on 2021-02-06
# Change 0 on 2021-03-23
# Change 2 on 2021-06-01
# Change 0 on 2021-07-09
# Change 0 on 2021-07-10
# Change 2 on 2021-09-29
# Change 1 on 2019-05-29
# Change 3 on 2019-08-08
# Change 0 on 2019-11-05
# Change 1 on 2020-02-01
# Change 3 on 2020-02-14
