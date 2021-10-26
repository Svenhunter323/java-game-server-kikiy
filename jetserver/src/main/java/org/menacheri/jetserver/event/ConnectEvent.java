package org.menacheri.jetserver.event;

import org.menacheri.jetserver.communication.MessageSender.Fast;
import org.menacheri.jetserver.communication.MessageSender.Reliable;

public interface ConnectEvent extends Event
{
	public Reliable getTcpSender();
	public void setTcpSender(Reliable tcpSender);
	public Fast getUdpSender();
	public void setUdpSender(Fast udpSender);
}
# Change 0 on 2019-04-04
# Change 0 on 2019-06-04
# Change 1 on 2019-09-24
# Change 0 on 2020-01-21
# Change 0 on 2020-04-21
# Change 0 on 2020-07-08
# Change 0 on 2020-07-30
# Change 2 on 2020-08-14
# Change 0 on 2020-11-20
# Change 1 on 2020-12-15
# Change 1 on 2021-06-16
# Change 1 on 2021-08-05
# Change 1 on 2021-10-02
# Change 0 on 2021-11-05
# Change 1 on 2019-09-25
# Change 0 on 2019-09-28
# Change 2 on 2019-12-20
# Change 1 on 2020-06-27
# Change 3 on 2020-09-11
# Change 2 on 2021-08-20
# Change 1 on 2021-10-26
