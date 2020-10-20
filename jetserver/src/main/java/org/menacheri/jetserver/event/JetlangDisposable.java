package org.menacheri.jetserver.event;

import org.jetlang.channels.ChannelSubscription;
import org.jetlang.core.Disposable;

/**
 * If the Event dispatcher uses Jetlang internally then it would require to
 * dispose of Jetlang {@link ChannelSubscription}s using the dispose method
 * during cleanup.
 * 
 * @author Abraham Menacherry
 * 
 */
public interface JetlangDisposable
{
	public Disposable getDisposable();

	public void setDisposable(Disposable disposable);
}
# Change 0 on 2019-11-22
# Change 1 on 2019-12-20
# Change 2 on 2020-07-18
# Change 0 on 2020-09-04
# Change 0 on 2020-10-14
# Change 0 on 2020-10-20
