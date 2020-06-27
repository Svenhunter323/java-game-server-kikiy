package org.menacheri.jetserver.handlers.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Sharable
public class NulEncoder extends OneToOneEncoder {

	private static final Logger LOG = LoggerFactory.getLogger(NulEncoder.class);
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		if(!(msg instanceof ChannelBuffer))
		{
			LOG.error("Expected channel buffer but recieved: {}", msg
					.getClass().getCanonicalName());
			return msg;
		}
		ChannelBuffer nulBuffer = ChannelBuffers.wrappedBuffer(new byte[] { 0 });
		ChannelBuffer buffer = ChannelBuffers.wrappedBuffer((ChannelBuffer)msg,nulBuffer);
		return buffer;
	}
	
}
# Change 0 on 2019-03-05
# Change 2 on 2019-03-06
# Change 3 on 2019-05-30
# Change 0 on 2019-08-08
# Change 1 on 2020-02-05
# Change 2 on 2020-06-27
