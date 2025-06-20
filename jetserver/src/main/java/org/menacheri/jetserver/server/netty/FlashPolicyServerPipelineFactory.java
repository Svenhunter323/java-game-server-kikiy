package org.menacheri.jetserver.server.netty;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.util.Timer;
import org.menacheri.jetserver.handlers.netty.FlashPolicyServerDecoder;
import org.menacheri.jetserver.handlers.netty.FlashPolicyServerHandler;


/**
 * @author <a href="http://www.waywardmonkeys.com/">Bruce Mitchener</a>
 */
public class FlashPolicyServerPipelineFactory implements ChannelPipelineFactory
{
	private Timer timer;

    public ChannelPipeline getPipeline() throws Exception {
        // Create a default pipeline implementation.
        ChannelPipeline pipeline = pipeline();
        pipeline.addLast("timeout", new ReadTimeoutHandler(timer, 30));
        pipeline.addLast("decoder", new FlashPolicyServerDecoder());
        pipeline.addLast("handler", getFlashPolicyServerHandler());
        return pipeline;
    }

    /**
	 * Spring will return the actual prototype bean from its context here. It
	 * uses method lookup here.
	 * 
	 * @return a new instance of the {@link FlashPolicyServerHandler}
	 */
    protected FlashPolicyServerHandler getFlashPolicyServerHandler()
    {
    	return null;
    }
    
	public Timer getTimer()
	{
		return timer;
	}

	public void setTimer(Timer timer)
	{
		this.timer = timer;
	}
}
# Change 0 on 2019-05-31
# Change 3 on 2019-10-02
# Change 0 on 2019-11-30
# Change 0 on 2020-06-25
# Change 2 on 2020-07-16
# Change 2 on 2020-10-17
# Change 0 on 2020-11-17
# Change 2 on 2020-12-12
# Change 2 on 2021-08-11
# Change 1 on 2021-10-14
# Change 2 on 2021-10-15
