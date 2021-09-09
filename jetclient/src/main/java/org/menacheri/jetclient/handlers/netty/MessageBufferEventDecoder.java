package org.menacheri.jetclient.handlers.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;
import org.menacheri.jetclient.communication.NettyMessageBuffer;
import org.menacheri.jetclient.event.Events;

/**
 * This decoder will convert a Netty {@link ChannelBuffer} to a
 * {@link NettyMessageBuffer}. It will also convert
 * {@link Events#NETWORK_MESSAGE} events to {@link Events#SESSION_MESSAGE}
 * event.
 * 
 * @author Abraham Menacherry
 * 
 */
@Sharable
public class MessageBufferEventDecoder extends OneToOneDecoder
{
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception
	{
		if (null == msg)
		{
			return msg;
		}
		ChannelBuffer buffer = (ChannelBuffer) msg;
		byte opcode = buffer.readByte();
		if (opcode == Events.NETWORK_MESSAGE)
		{
			opcode = Events.SESSION_MESSAGE;
		}
		return Events.event(new NettyMessageBuffer(buffer), opcode);
	}
	
}
# Change 2 on 2020-08-12
# Change 1 on 2021-02-12
# Change 0 on 2021-04-14
# Change 0 on 2021-04-22
# Change 0 on 2021-06-26
# Change 0 on 2021-09-09
