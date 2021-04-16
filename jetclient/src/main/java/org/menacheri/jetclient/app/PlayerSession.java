package org.menacheri.jetclient.app;

/**
 * This interface abstracts a user session to a {@link Game}.
 * 
 * @author Abraham Menacherry
 * 
 */
public interface PlayerSession extends Session
{
	/**
	 * Each session is associated with a {@link Player}. This is the actual
	 * human or machine using this session.
	 * 
	 * @return Returns that associated Player object or null if it is not
	 *         associated yet.
	 */
	public abstract Player getPlayer();

}
# Change 2 on 2019-08-08
# Change 0 on 2019-12-14
# Change 2 on 2020-06-12
# Change 0 on 2020-06-27
# Change 2 on 2020-07-10
# Change 2 on 2020-08-19
# Change 2 on 2020-09-12
# Change 0 on 2021-04-16
