package org.menacheri.jetclient.event;

import org.menacheri.jetclient.communication.DeliveryGuaranty;

/**
 * This interface is specifically used for events that will get transmitted to
 * remote machine/vm. It contains the {@link DeliveryGuaranty} associated with
 * the event so that messages can be transmitted either using TCP or UDP
 * transports based on the guaranty defined. Implementations can use RELIABLE as
 * default.
 * 
 * @author Abraham Menacherry
 * 
 */
public interface NetworkEvent extends Event
{
	DeliveryGuaranty getDeliveryGuaranty();

	void setDeliveryGuaranty(DeliveryGuaranty deliveryGuaranty);
}
# Change 0 on 2019-03-26
# Change 3 on 2019-04-25
# Change 0 on 2020-11-19
# Change 0 on 2020-12-26
# Change 0 on 2021-01-15
# Change 0 on 2021-03-27
# Change 0 on 2021-04-15
# Change 0 on 2021-07-08
# Change 1 on 2019-05-16
# Change 0 on 2019-08-17
# Change 1 on 2019-12-11
# Change 2 on 2020-01-28
# Change 0 on 2020-03-20
# Change 1 on 2020-10-24
