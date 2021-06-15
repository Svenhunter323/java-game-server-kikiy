package org.menacheri.jetserver.service.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.menacheri.jetserver.app.Session;
import org.menacheri.jetserver.app.Task;
import org.menacheri.jetserver.service.TaskManagerService;
import org.menacheri.jetserver.util.JetConfig;

/**
 * A session registry that will do auto cleanup of the {@link Session} after
 * waiting for a specified amount of time for reconnection from remote client.
 * 
 * @author Abraham Menacherry
 * 
 */
public class ReconnectSessionRegistry extends SessionRegistry<String>
{
	TaskManagerService taskManagerService;
	int reconnectDelay = JetConfig.DEFAULT_RECONNECT_DELAY;

	@Override
	public boolean putSession(String key, Session session)
	{
		taskManagerService.schedule(new ClearSessionTask(key, sessions),
				reconnectDelay, TimeUnit.MILLISECONDS);
		return super.putSession(key, session);
	}

	protected static class ClearSessionTask implements Task
	{

		final String reconnectKey;
		final Map<String, Session> sessions;

		protected ClearSessionTask(String reconnectKey,
				Map<String, Session> sessions)
		{
			this.reconnectKey = reconnectKey;
			this.sessions = sessions;
		}

		@Override
		public void run()
		{
			Session session = sessions.get(reconnectKey);
			if (null != session)
			{
				synchronized (session)
				{
					// at this point it could have been removed by re-connect
					// handler already, hence another null check required.
					Session removeSession = sessions.remove(reconnectKey);
					if (null != removeSession)
						removeSession.close();
				}
			}
		}

		@Override
		public Object getId()
		{
			return null;
		}

		@Override
		public void setId(Object id)
		{
		}

	}

	public TaskManagerService getTaskManagerService()
	{
		return taskManagerService;
	}

	public void setTaskManagerService(TaskManagerService taskManagerService)
	{
		this.taskManagerService = taskManagerService;
	}

	public int getReconnectDelay()
	{
		return reconnectDelay;
	}

	public void setReconnectDelay(int reconnectDelay)
	{
		this.reconnectDelay = reconnectDelay;
	}

}
# Change 0 on 2019-03-20
# Change 0 on 2019-08-01
# Change 0 on 2020-01-28
# Change 2 on 2020-02-01
# Change 0 on 2020-03-18
# Change 1 on 2020-05-28
# Change 0 on 2020-09-02
# Change 1 on 2020-09-02
# Change 3 on 2021-04-14
# Change 0 on 2021-06-15
