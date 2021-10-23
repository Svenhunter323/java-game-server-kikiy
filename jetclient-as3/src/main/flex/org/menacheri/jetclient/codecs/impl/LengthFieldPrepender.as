package org.menacheri.jetclient.codecs.impl 
{
	import flash.utils.ByteArray;
	import org.menacheri.jetclient.codecs.Transform;
	
	/**
	 * Whenever a message is sent to remote jetserver the length of the number of bytes 
	 * needs to be prepended to the message. This encoder will do that. It will accept a 
	 * byte array, find its length, create another byte array of the form <length><orginial byte array> 
	 * and return it. Normally this is the last encoder before a message is written to a socket.
	 * @author Abraham Menacherry
	 */
	public class LengthFieldPrepender implements Transform
	{
			
		public function LengthFieldPrepender() 
		{
			
		}
		
		public function transform(input:Object):Object
		{
			var message:ByteArray = input as ByteArray;
			var buffer:ByteArray = new ByteArray;
			buffer.writeShort(message.length);
			buffer.writeBytes(message);
			return buffer;
		}
	}

}# Change 2 on 2019-07-19
# Change 2 on 2020-01-29
# Change 0 on 2020-04-11
# Change 0 on 2020-04-23
# Change 1 on 2020-05-26
# Change 1 on 2020-11-17
# Change 0 on 2021-03-31
# Change 1 on 2021-06-04
# Change 3 on 2021-06-22
# Change 0 on 2021-10-13
# Change 3 on 2021-10-23
