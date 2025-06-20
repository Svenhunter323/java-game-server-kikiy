package org.menacheri.jetserver.communication;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.menacheri.jetserver.communication.DeliveryGuaranty.DeliveryGuarantyOptions;
import org.menacheri.jetserver.communication.MessageSender.Reliable;
import org.menacheri.jetserver.event.Events;
import org.menacheri.jetserver.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class that transmits messages reliably to remote machines/vm's. Internally
 * this class uses Netty tcp {@link Channel} to transmit the message.
 * 
 * @author Abraham Menacherry
 * 
 */
public class NettyTCPMessageSender implements Reliable
{
	private final Channel channel;
	private static final DeliveryGuaranty DELIVERY_GUARANTY = DeliveryGuarantyOptions.RELIABLE;
	private static final Logger LOG = LoggerFactory
			.getLogger(NettyTCPMessageSender.class);

	public NettyTCPMessageSender(Channel channel)
	{
		super();
		this.channel = channel;
	}

	@Override
	public Object sendMessage(Object message)
	{
		return channel.write(message);
	}

	@Override
	public DeliveryGuaranty getDeliveryGuaranty()
	{
		return DELIVERY_GUARANTY;
	}

	public Channel getChannel()
	{
		return channel;
	}

	/**
	 * Writes an the {@link Events#DISCONNECT} to the client, flushes
	 * all the pending writes and closes the channel.
	 * 
	 */
	@Override
	public void close()
	{
		LOG.debug("Going to close tcp connection in class: {}", this
				.getClass().getName());
		Event event = Events.event(null, Events.DISCONNECT);
		if (channel.isConnected())
		{
			channel.write(event).addListener(ChannelFutureListener.CLOSE);
		}
		else
		{
			channel.close();
			LOG.trace("Unable to write the Event {} with type {} to socket",
					event, event.getType());
		}
	}

	@Override
	public String toString()
	{
		String channelId = "TCP channel with Id: ";
		if (null != channel)
		{
			channelId += channel.getId().toString();
		}
		else
		{
			channelId += "0";
		}
		String sender = "Netty " + channelId;
		return sender;
	}
}
# Change 3 on 2019-11-28
# Change 1 on 2019-12-14
# Change 1 on 2020-04-03
# Change 0 on 2020-05-27
# Change 0 on 2020-07-16
# Change 0 on 2021-02-12
# Change 3 on 2021-02-12
# Change 2 on 2021-07-13
# Change 1 on 2021-08-20
