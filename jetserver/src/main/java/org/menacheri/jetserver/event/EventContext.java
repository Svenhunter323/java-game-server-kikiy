package org.menacheri.jetserver.event;

import org.menacheri.jetserver.app.Session;

public interface EventContext
{
	Session getSession();
	void setSession(Session session);
	
	/**
     * Retrieves an object which is {@link #setAttachment(Object) attached} to
     * this context.
     *
     * @return {@code null} if no object was attached or
     *                      {@code null} was attached
     */
    Object getAttachment();
    
    /**
     * Attaches an object to this context to store a stateful information
     * specific to the {@link Event} which is associated with this
     * context.
     */
    void setAttachment(Object attachement);
}
# Change 1 on 2019-02-22
# Change 0 on 2019-02-27
# Change 1 on 2019-03-27
# Change 1 on 2019-04-16
# Change 1 on 2019-07-16
# Change 0 on 2019-09-26
# Change 1 on 2019-10-12
# Change 0 on 2019-11-20
# Change 0 on 2020-08-14
# Change 3 on 2021-06-01
# Change 1 on 2019-03-06
# Change 1 on 2019-06-05
# Change 2 on 2019-06-05
