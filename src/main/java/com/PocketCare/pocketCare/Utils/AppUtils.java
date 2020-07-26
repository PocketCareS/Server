package com.PocketCare.pocketCare.Utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.PocketCare.pocketCare.Constants.AppConstants;
import com.PocketCare.pocketCare.model.ContactVbtInfo;

public class AppUtils {

	private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
	private static final String NUMBER = "0123456789";

	private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
	private static SecureRandom random = new SecureRandom();

	public static String getRandomString(int length) {
		if (length < 1)
			throw new IllegalArgumentException();

		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {

			int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
			char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
			sb.append(rndChar);
		}

		return sb.toString();

	}

	public static byte[] longToBytes(long l) {
		byte[] result = new byte[8];
		for (int i = 7; i >= 0; i--) {
			result[i] = (byte) (l & 0xFF);
			l >>= 8;
		}
		return result;
	}

	/*
	 * //for generating VBT long hournumber = System.currentTimeMillis() / (1000 *
	 * 3600); byte[] HourNo = longToBytes(hournumber);
	 * 
	 * byte[] VBTfull = calHmacSha256(DKey, HourNo);//VBT_full is 32B long
	 * ByteBuffer VBTbuffer = ByteBuffer.allocate(4); //here the size should be 4
	 * not 16 VBTbuffer.put(VBTfull, 0, 4); byte[] VBT = VBTbuffer.array(); for (int
	 * index = 0; index < VBT.length; index++) { Log.i("VBT:",
	 * String.format("0x%20x", VBT[index])); }
	 *
	 */
	private static int getIntFromByte(byte[] VBT, int index) {
		return ((VBT[index] & 0xff) << 8) | (VBT[index + 1] & 0xff);
	}

	private static byte[] calcHmacSha256(byte[] secretKey, byte[] message) {
		byte[] hmacSha256 = null;
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "HmacSHA256");// ios/swift might be different
																						// here
			mac.init(secretKeySpec);
			hmacSha256 = mac.doFinal(message);
		} catch (Exception e) {
			throw new RuntimeException("Failed to calculate hmac-sha256", e);
		}
		return hmacSha256;
	}

	public static String getVbtName(String dailyKey, long hour) {
		byte[] dk = getByteArrFromHexString(dailyKey);
		byte[] hourNo = longToBytes(hour / (1000 * 3600));
		byte[] VBTfull; // VBT_full is 32B long
		int vbt_p = 0;
		Integer minorInt = 0, majorInt = 0;
		boolean minor_zero = true;
		boolean major_zero = true;
		while (minor_zero || major_zero) {
			if (vbt_p > 0) {
				hour -= 100000;
				hourNo = longToBytes(hour);
			}
			VBTfull = calcHmacSha256(dk, hourNo);
			vbt_p = 0;
			if (minor_zero) {
				for (int i = vbt_p; i < 31; i += 2) {
					minorInt = getIntFromByte(VBTfull, i);
					if (minorInt != 0) {
						vbt_p += 2;
						minor_zero = false;
						break;
					}
					vbt_p += 2;
				}
			}
			for (int i = vbt_p; i < 31; i += 2) {
				majorInt = getIntFromByte(VBTfull, i);
				if (majorInt != 0) {
					vbt_p += 2;
					major_zero = false;
					break;
				}
				vbt_p += 2;
			}
		}

		String vbtFull = Integer.toString(majorInt).concat(Integer.toString(minorInt));
		return vbtFull;
	}

	/*
	 * Testing function
	 */
	private static void formatterConverter() throws NoSuchAlgorithmException {
		String name = "PANKAJ";
		byte[] byteArray1 = name.getBytes();
		String formatterString = getHexStringFromByteArray(byteArray1);
		System.out.println(formatterString);
		System.out.println(getByteArrFromHexString(formatterString));
		// print the byte[] elements
	}

	private static String getHexStringFromByteArray(byte[] byteArr) throws NoSuchAlgorithmException {
		Formatter formatter = new Formatter();
		for (byte b : byteArr) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}

	private static byte[] getByteArrFromHexString(String hexString) {
		byte[] val = new byte[hexString.length() / 2];
		for (int i = 0; i < val.length; i++) {
			int index = i * 2;
			int j = Integer.parseInt(hexString.substring(index, index + 2), 16);
			val[i] = (byte) j;
		}
		return val;
	}

	public static ContactVbtInfo compare(ContactVbtInfo o1, ContactVbtInfo o2) {
		// TODO Auto-generated method stub
		ContactVbtInfo processedInfo = null;
		if (o1 != null && o2 != null) {
			processedInfo = (o1.getCountTwo() > o2.getCountTwo()) ? o1 : o2;
		} else if (o1 == null && o2 != null) {
			processedInfo = o2;
		} else if (o1 != null && o2 == null) {
			processedInfo = o1;
		}
		return processedInfo;
	}

	public static List<Long> getDates(Long startDate, Long endDate){
		List<Long> dates = new ArrayList<Long>();
		while (startDate <= endDate) {
			dates.add(startDate);
			startDate = startDate + AppConstants.NUMBER_OF_MILLIS_IN_A_DAY;
		}
		return dates;
	}
	
	public static long getNextDate(Long startDate){
		long date = startDate + AppConstants.NUMBER_OF_MILLIS_IN_A_DAY;
		
		return date;
	}

}
