package org.menacheri.jetserver.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomStringGenerator
{
	public static final int DEFAULT_LENGTH = 8;

	static char[] alphaNumberic = new char[] { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', '0' };

	public static String generateRandomString(int length)
	{
		String random = "ACK";
		
		int len = DEFAULT_LENGTH;
		if (length > 0)
		{
			len = length;
		}
		
		char[] randomChars = new char[len];
		try
		{
			SecureRandom wheel = SecureRandom.getInstance("SHA1PRNG");
			for (int i = 0; i < len; i++)
			{
				int nextChar = wheel.nextInt(alphaNumberic.length);
				randomChars[i] = alphaNumberic[nextChar];
			}
			random = new String(randomChars);
		}
		catch (NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return random;
	}
}
# Change 1 on 2019-02-27
# Change 2 on 2019-06-07
# Change 0 on 2019-07-16
# Change 3 on 2019-09-24
# Change 1 on 2019-12-07
# Change 0 on 2020-08-15
# Change 1 on 2020-09-17
# Change 1 on 2021-06-01
# Change 1 on 2021-10-26
# Change 0 on 2019-03-09
# Change 1 on 2019-05-31
# Change 3 on 2019-07-26
