package org.menacheri.jetclient.handlers.netty;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

public class UDPPipelineFactory implements ChannelPipelineFactory
{
	/**
	 * This pipeline will be shared across all the channels. In Netty UDP
	 * implementation it does not make sense to have different pipelines for
	 * different channels as the protocol is essentials "connection-less" for a
	 * bootstrap.
	 */
	private static final ChannelPipeline pipeline;
	
	private static final MessageBufferEventDecoder EVENT_DECODER;
	private static final MessageBufferEventEncoder EVENT_ENCODER;
	private static final UDPUpstreamHandler UDP_UPSTREAM_HANDLER;
	
	private static final UDPPipelineFactory INSTANCE;
	static {
		EVENT_DECODER = new MessageBufferEventDecoder();
		EVENT_ENCODER = new MessageBufferEventEncoder();
		UDP_UPSTREAM_HANDLER = new UDPUpstreamHandler();
		pipeline = init();
		INSTANCE = new UDPPipelineFactory();
	}
	
	static ChannelPipeline init()
	{
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("eventDecoder", EVENT_DECODER);
		pipeline.addLast("eventEncoder", EVENT_ENCODER);
		pipeline.addLast("UDPUpstreamHandler",UDP_UPSTREAM_HANDLER);
		return pipeline;
	}
	
	@Override
	public ChannelPipeline getPipeline() throws Exception
	{
		return pipeline;
	}

	public static UDPPipelineFactory getInstance()
	{
		return INSTANCE;
	}
}
# Change 1 on 2019-05-30
# Change 2 on 2020-08-14
# Change 2 on 2020-09-02
# Change 0 on 2020-10-17
# Change 1 on 2020-11-03
# Change 2 on 2021-06-12
# Change 0 on 2021-08-27
# Change 2 on 2021-08-27
