package com.sysco.common;

/**
 * This class contains static properties that are used throughout Sysco Market.
 * <p>
 * The properties defined here are independent of any business model objects. Static properties specific to a business model
 * object will be in that object.
 * 
 * <p>
 * Copyright (C) 2012 Sysco Corporation. All rights reserved.
 * <p>
 */
public final class Constants {
	
	// SYSCO copyright
	public final static String COPYRIGHT = "Copyright (C) 2018 SYSCO Corp. All Rights Reserved.";
	public final static String UTC = "UTC";
	
	/**
	 * Private constructor in order to avoid initialization of this class.
	 */
	private Constants() {
		
	}

	/**
	 * This is used to indicate yes flag for the application
	 */
	public static final String YES_FLAG = "Y";
	
	/**
	 * This is used to indicate no flag for the application
	 */
	public static final String NO_FLAG = "N";
	
	/**
	 * This is used to indicate Out of stock which has alternative flag for the application
	 */
	public static final String O_FLAG = "O";
	
	/**
	 * This is used to denote '.' as a delimiter
	 */
	public static final String DOT_DELIMITER = ".";
	
	/**
	 * This is used to denote '~' as a delimiter
	 */
	public static final String TILDES_DELIMITER = "~";
	
	/**
	 * This is used to denote '-' as a delimiter
	 */
	public static final String HYPHEN_DELIMITER = "-";
	/**
	 * This is used to denote ',' as a delimiter
	 */
	public static final String COMMA_DELIMITER = ",";
	/**
	 * This is used to denote '&' as a constant
	 */
	public static final String AMPERSAND = "&";
	/**
	 * This is used to denote '/' as a constant
	 */
	public static final String FWD_SLASH = "/";
	
	/**
	 * This is a formatter for date
	 * 
	 */
	public static final String DATE_FORMAT = "MM/dd/yyyy";
	
	public static final String TIME_FORMAT = "HH:mm:ss";
	
	public static final String DATE_TIME_FORMAT = "MM/dd/yyyy 'at' KK:mm:ss "; // KK : shows 00-11
	public static final String DATE_TIME_FORMAT2 = "MM/dd/yyyy 'at' hh:mm:ss "; // hh : shows 01-12
	
	public static final String MMDDYYYY = "MMddyyyy";
	
	public static final String YYYYMMDD = "yyyyMMdd";
	
	public static final String DATE_TIME_FORMAT3 = "yyyyMMddHHmmss";
	
	public static final String DATE_TIME_FORMAT4 = "MM/dd/yyyy 'at' HH:mm:ss ";
	/**
	 * Date format used in the calendar
	 */
	public static final String ITEM_HISTORY_CALENDAR_DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * This constant value to be reused instead of using the same case value in multiple places.
	 */
	public static final String EMPTY_STRING = "";
	
	/**
	 * This constant value to be reused instead of using the same case value in multiple places.
	 */
	public static final String SPACE_STRING = " ";
	
	/**
	 * This constant value to be reused instead of using the same case value in multiple places.
	 */
	public static final String ZERO_STRING = "0";
	
	public static final String SORT_DESC = "DESC";
	public static final String SORT_ASC = "ASC";
	
	public static final String DEV_ENVIRONMENT = "dev";
	public static final String QA_ENVIRONMENT = "qa";
	public static final String PROD_ENVIRONMENT = "prod";

	public static final String BUCKET_NAME = "frdt-ws";
	public static final String TEMPLATE = "template";
	public static final String TEMPLATE_EXCEL = "FRDT_Vendor_Upload_Template.xlsx";
}
