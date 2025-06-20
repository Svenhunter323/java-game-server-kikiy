package org.menacheri.zombieclient;

import org.jboss.netty.buffer.ChannelBuffer;
import org.menacheri.jetclient.app.Session;
import org.menacheri.jetclient.communication.DeliveryGuaranty.DeliveryGuarantyOptions;
import org.menacheri.jetclient.communication.MessageBuffer;
import org.menacheri.jetclient.communication.NettyMessageBuffer;
import org.menacheri.jetclient.event.Event;
import org.menacheri.jetclient.event.Events;
import org.menacheri.zombie.domain.IAM;
import org.menacheri.zombie.domain.ZombieCommands;

public class GamePlay implements Runnable
{
	private final IAM iam;
	private final Session session;
	
	public GamePlay(IAM iam, Session session)
	{
		this.iam = iam;
		this.session = session;
	}
	
	@Override
	public void run()
	{
		int type = IAM.getInt(iam);
		int operation = 0;
		switch(iam)
		{
		case DEFENDER:
			operation = ZombieCommands.SHOT_GUN.getCommand();
			break;
		case ZOMBIE:
			operation = ZombieCommands.EAT_BRAINS.getCommand();
			break;
		}
		
		MessageBuffer<ChannelBuffer> messageBuffer = new NettyMessageBuffer();
		messageBuffer.writeInt(type);
		messageBuffer.writeInt(operation);
		Event event = Events.networkEvent(messageBuffer,DeliveryGuarantyOptions.FAST);
		session.onEvent(event);
	}
}
# Change 2 on 2019-03-07
# Change 1 on 2019-06-07
# Change 0 on 2020-03-21
# Change 0 on 2020-05-14
# Change 1 on 2020-08-11
# Change 2 on 2021-04-29
# Change 1 on 2021-08-06
# Change 0 on 2019-05-17
# Change 0 on 2019-10-03
# Change 2 on 2020-01-14
# Change 2 on 2020-02-14
# Change 0 on 2020-06-06
# Change 3 on 2020-08-12
# Change 2 on 2021-02-12
# Change 0 on 2021-03-06
