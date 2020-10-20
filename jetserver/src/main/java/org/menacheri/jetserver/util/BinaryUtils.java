package org.menacheri.jetserver.util;

public class BinaryUtils
{

	private static final String HEXES = "0123456789ABCDEF";

	public static String getHexString(byte[] raw)
	{
		return getHexString(raw, null);
	}

	public static String getHexString(byte[] raw, String separator)
	{
		boolean sep = (null != separator) && !("".equals(separator));

		if (raw == null)
		{
			return null;
		}
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw)
		{
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(
					HEXES.charAt((b & 0x0F)));
			if (sep)
			{
				hex.append(separator);
			}
		}
		return hex.toString();
	}
}
# Change 0 on 2019-05-30
# Change 1 on 2019-07-05
# Change 0 on 2020-04-07
# Change 0 on 2020-10-17
# Change 0 on 2020-10-29
# Change 0 on 2020-11-12
# Change 0 on 2020-12-29
# Change 1 on 2021-03-27
# Change 2 on 2021-08-06
# Change 0 on 2019-04-03
# Change 2 on 2019-09-28
# Change 1 on 2019-10-17
# Change 1 on 2019-11-21
# Change 0 on 2020-01-09
# Change 3 on 2020-01-29
# Change 0 on 2020-08-26
# Change 1 on 2020-10-20
# Change 2 on 2020-10-20
