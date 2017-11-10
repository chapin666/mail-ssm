package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Utf7Coder {
	private static char PAD = '=';
	private static char[] bytesToChars = new char[] { 'A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', '+', '/' };
	private static byte[] charsToBytes;

	static {
		charsToBytes = new byte[0x80];
		// first, fill the table with 0s. padding char(=) is also set to 0.
		for (int i = 0; i < charsToBytes.length; i++)
			charsToBytes[i] = 0;

		// setup chars to bytes mapping.
		for (int c = 0; c < bytesToChars.length; c++)
			charsToBytes[bytesToChars[c]] = (byte) c;
	}

	private Utf7Coder() {
	}

	/**
	 * encodes to utf-7 encoded string.
	 * 
	 * @param charSeq
	 * @return utf-7 encoded string.
	 */
	public static String encode(CharSequence charSeq) {
		return encode(charSeq, 0, charSeq.length());
	}

	/**
	 * encodes to utf-7 encoded string.
	 * 
	 * @param charSeq
	 * @param start
	 *            offset of the charSeq.
	 * @param length
	 *            length of the charSeq.
	 * @return utf-7 encoded string.
	 */
	public static String encode(CharSequence charSeq, int start, int length) {
		int end = start + length;
		if (charSeq.length() < end)
			throw new IndexOutOfBoundsException();

		StringBuilder buf = new StringBuilder(length);

		for (int i = start; i < end;) {
			char c = charSeq.charAt(i);
			if (isSetD(c) || isSetO(c)) {
				buf.append(c);
				i++;
			} else {
				i += encodeSetB(charSeq, i, end, buf);
			}
		}

		return buf.toString();
	}

	/**
	 * encodes to utf-7 encoded byte array.
	 * 
	 * @param charSeq
	 * @return utf-7 encoded string.
	 */
	public static byte[] encodeToByteArray(CharSequence charSeq) {
		String e = encode(charSeq);
		try {
			return e.getBytes("iso-8859-1");
		} catch (UnsupportedEncodingException unused) {
		}
		return e.getBytes();
	}

	/**
	 * encodes to utf-7 encoded byte array.
	 * 
	 * @param charSeq
	 * @param start
	 *            offset of the charSeq.
	 * @param length
	 *            length of the charSeq.
	 * @return utf-7 encoded string.
	 */
	public static byte[] encodeToByteArray(CharSequence charSeq, int start,
			int length) {
		String e = encode(charSeq, start, length);
		try {
			return e.getBytes("iso-8859-1");
		} catch (UnsupportedEncodingException unused) {
		}
		return e.getBytes();
	}

	private static int encodeSetB(CharSequence charSeq, int start, int end,
			StringBuilder buf) {
		int count = 0;

		for (int i = start; i < end; i++) {
			char c = charSeq.charAt(i);
			if (isSetD(c) || isSetO(c))
				break;

			count++;
		}

		byte[] bytes = getBytes(charSeq, start, start + count);
		buf.append('&');
		buf.append(encodeBase64(bytes, 0, bytes.length, true));
		buf.append('-'); // TODO always adds '-' now. but if next is not base64
							// char, it's not needed.

		return count;
	}

	private static byte[] getBytes(CharSequence charSeq, int start, int end) {
		byte[] bytes = new byte[(end - start) * 2];
		int j = 0;
		for (int i = start; i < end; i++) {
			char c = charSeq.charAt(i);
			bytes[j++] = (byte) ((c >> 8) & 0xff);
			bytes[j++] = (byte) (c & 0xff);
		}

		return bytes;
	}

	private static boolean isSetD(char c) {
		if ('A' <= c && c <= 'Z')
			return true;
		if ('a' <= c && c <= 'z')
			return true;
		if ('0' <= c && c <= '9')
			return true;
		if (c == 9 || c == 10 || c == 13 || c == 32 || c == 39 || c == 40
				|| c == 41 || c == 44 || c == 45 || c == 46 || c == 47
				|| c == 58 || c == 63)
			return true;

		return false;
	}

	private static boolean isSetO(char c) {
		return (c == 33 || c == 34 || c == 35 || c == 36 || c == 37 || c == 38
				|| c == 42 || c == 59 || c == 60 || c == 61 || c == 62
				|| c == 64 || c == 91 || c == 93 || c == 94 || c == 95
				|| c == 96 || c == 123 || c == 124 || c == 125);
	}

	/**
	 * decodes utf-7 encoding.
	 * 
	 * @param charSeq
	 *            utf-7 encoded string.
	 * @return decoded string.
	 */
	public static String decode(CharSequence charSeq) {
		return decode(charSeq, 0, charSeq.length());
	}

	/**
	 * decodes utf-7 encoding.
	 * 
	 * @param charSeq
	 *            utf-7 encoded string.
	 * @param start
	 *            offset of the charSeq.
	 * @param length
	 *            length of the charSeq.
	 * @return decoded string.
	 */
	public static String decode(CharSequence charSeq, int start, int length) {
		int end = start + length;
		if (charSeq.length() < end)
			throw new IndexOutOfBoundsException();

		StringBuilder buf = new StringBuilder(length);

		for (int i = start; i < end; i++) {
			char c = charSeq.charAt(i);
			if (c == '+') {
				try {
					i += decodeSetB(charSeq, i + 1, end, buf);
				} catch (IOException unused) {
				} // since buf is StringBuilder, so Exception never been thrown.
			} else {
				buf.append(c);
			}
		}

		return buf.toString();
	}

	/**
	 * decodes utf-7 encoding.
	 * 
	 * @param bytes
	 *            utf-7 encoded string.
	 * @return decoded string.
	 */
	public static String decode(byte[] bytes) {
		String e;
		try {
			e = new String(bytes, "iso-8859-1");
		} catch (UnsupportedEncodingException unused) {
			e = new String(bytes);
		}
		return decode(e);
	}

	/**
	 * decodes utf-7 encoding.
	 * 
	 * @param bytes
	 *            utf-7 encoded string.
	 * @param start
	 *            offset of the charSeq.
	 * @param length
	 *            length of the charSeq.
	 * @return decoded string.
	 */
	public static String decode(byte[] bytes, int start, int length) {
		String e;
		try {
			e = new String(bytes, start, length, "iso-8859-1");
		} catch (UnsupportedEncodingException unused) {
			e = new String(bytes, start, length);
		}
		return decode(e);
	}

	/**
	 * decodes utf-7 set 'B'.
	 */
	private static int decodeSetB(CharSequence charSeq, int start, int end,
			Appendable buf) throws IOException {
		// special case '+-', it represents '+'.
		if (start < end && charSeq.charAt(start) == '-') {
			buf.append('&');
			return 1;
		}

		int count = 0;
		boolean isTerminatedWithMinus = false;

		for (int i = start; i < end; i++) {
			char c = charSeq.charAt(i);
			if (c == '-') {
				isTerminatedWithMinus = true;
				break;
			}
			if (!isBase64Char(c))
				break;

			count++;
		}

		byte[] bytes = decodeBase64(charSeq, start, count);
		for (int i = 0; i < bytes.length; i += 2) {
			int c = (((int) bytes[i] & 0xff) << 8)
					+ ((int) bytes[i + 1] & 0xff);
			buf.append((char) c);
		}

		return count + ((isTerminatedWithMinus) ? 1 : 0); // if terminated with
															// '-', absorb it.
	}

	private static boolean isBase64Char(char c) {
		if ('A' <= c && c <= 'Z')
			return true;
		if ('a' <= c && c <= 'z')
			return true;
		if ('0' <= c && c <= '9')
			return true;
		if (c == '+' || c == '/')
			return true;

		return false;
	}

	public static String encodeBase64(byte[] bytes) {
		return encodeBase64(bytes, 0, bytes.length);
	}

	public static String encodeBase64(byte[] bytes, int start, int length) {
		return encodeBase64(bytes, start, length, false);
	}

	private static String encodeBase64(byte[] bytes, int start, int length,
			boolean omitsPad) {
		// converts each 3 bytes: xxxxxxxx yyyyyyyy zzzzzzzz
		// into 4 characters: xxxxxx xxyyyy yyyyzz zzzzzz

		int end = start + length;
		if (bytes.length < end)
			throw new IndexOutOfBoundsException();

		StringBuilder buf = new StringBuilder((length / 3) * 4);

		for (int i = start; i < end; i += 3) {
			int x = (int) bytes[i] & 0xff;
			int y = (i + 1 < end) ? (int) bytes[i + 1] & 0xff : 0;
			int z = (i + 2 < end) ? (int) bytes[i + 2] & 0xff : 0;

			if (i + 2 < end) {
				buf.append(bytesToChars[(x & 0xfc) >> 2]);
				buf.append(bytesToChars[((x & 0x3) << 4) | ((y & 0xf0) >> 4)]);
				buf.append(bytesToChars[((y & 0x0f) << 2) | ((z & 0xc0) >> 6)]);
				buf.append(bytesToChars[z & 0x3f]);
			} else if (i + 1 < end) {
				buf.append(bytesToChars[(x & 0xfc) >> 2]);
				buf.append(bytesToChars[((x & 0x3) << 4) | ((y & 0xf0) >> 4)]);
				buf.append(bytesToChars[((y & 0x0f) << 2)]);
				if (!omitsPad)
					buf.append(PAD);
			} else {
				buf.append(bytesToChars[(x & 0xfc) >> 2]);
				buf.append(bytesToChars[((x & 0x3) << 4)]);
				if (!omitsPad) {
					buf.append(PAD);
					buf.append(PAD);
				}
			}
		}

		return buf.toString();
	}

	public static byte[] decodeBase64(CharSequence charSeq) {
		return decodeBase64(charSeq, 0, charSeq.length());
	}

	public static byte[] decodeBase64(CharSequence charSeq, int start,
			int length) {
		// converts each 4 characters: wwwwww xxxxxx yyyyyy zzzzzz
		// into 3 bytes: wwwwwwxx xxxxyyyy yyzzzzzz
		// 3rd and/or 4th characters are may be padding char(=).
		// if 4th char is '='(or end of sequence) then decodes 2 bytes.
		// if 3rd char is '=' then decodes 1 byte.

		int end = start + length;
		if (charSeq.length() < end)
			throw new IndexOutOfBoundsException();

		ByteArrayOutputStream baos = new ByteArrayOutputStream((length / 4) * 3);

		for (int i = start; i < end; i += 4) {
			int w = charsToBytes[charSeq.charAt(i)];
			int x = ((i + 1) < end) ? charsToBytes[charSeq.charAt(i + 1)] : 0;
			int y = ((i + 2) < end) ? charsToBytes[charSeq.charAt(i + 2)] : 0;
			int z = ((i + 3) < end) ? charsToBytes[charSeq.charAt(i + 3)] : 0;
			int b1 = w << 2 | (x & 0x30) >> 4;
			int b2 = (x & 0xf) << 4 | (y & 0x3c) >> 2;
			int b3 = (y & 0x3) << 6 | z;

			baos.write(b1);
			if (i + 2 < end && charSeq.charAt(i + 2) != PAD)
				baos.write(b2);
			if (i + 3 < end && charSeq.charAt(i + 3) != PAD)
				baos.write(b3);
		}

		return baos.toByteArray();
	}
}