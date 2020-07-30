package com.PocketCare.pocketCare.Constants;

public final class AppConstants {
	public static final int RANDSTRINGLEN = 6;
	public static final int TOKENSTRINGLENGTH = 20;
	public static final String VBTPREFIX = "PCP-";
	public static final String DATANOTPRESENT = "Data not present";
	public static final String UPLOADSUCCESS = "Upload Successfull";
	public static final String UPLOADERROR = "Upload Error";
	public static final String UNAUTH = "Token Expired";
	public static final int UNAUTHCODE = -2;
	public static final int SUCCESS = 1;
	public static final int ERROR = -1;
	public static final long NUMBER_OF_MILLIS_IN_A_DAY = 86400000;//(long)24*60*60*1000;
	public static final long NUMBER_OF_MILLIS_IN_A_HOUR = (long)60*60*1000;
	public static final int TRACING_DAYS = 14;
	public static final String TYPE1TITLE = "Type 1 Notification";
	public static final String TYPE1BODY = "Type 1 Body";
	public static final String TYPE2TITLE = "Type 2 Notification";
	public static final String TYPE2BODY = "Type 2 Body";
	public static final String TYPE1DATAKEY = "Type 1 Data";
	public static final String CONTACTTYPE = "close";
	public static final String TYPE1DATAVAL = "Type 1 Data Body";
	public static final String TYPE2DATAKEY = "Type 2 Data";
	public static final String TYPE2DATAVAL = "Type 2 Data Body";
	public static final int CLOSECONTACTDURATION = 5;
	public static final int CONTACTTRACEDURATION = 5;

	public static final long TOKENEXPIRYTIME = 180; //Time in minutes check class USerService for unit
}
