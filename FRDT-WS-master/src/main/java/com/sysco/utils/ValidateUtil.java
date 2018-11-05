package com.sysco.utils;

import com.sysco.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {

	private final static String MUA = "MUA";

	private final static String NON_MUA = "NON-MUA";

	private static Pattern pattern = Pattern.compile("(\\d){4}-(\\d){2}-(\\d){2}");

	/**
	 * Determine whether a string is a number
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check customerType format
	 * 
	 * @param customerType
	 * @return
	 */
	public static boolean validateCustomerType(String customerType) {
		if (StringUtils.isBlank(customerType)) {
			throw new ValidationException(HttpStatus.BAD_REQUEST, "This value is a required item");
		}
		if (MUA.equals(customerType) || NON_MUA.equals(customerType)) {
			return true;
		} else {
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					"Please check your customer type，values(MUA & NON-MUA) ");
		}
	}

	/**
	 * Check groupId format
	 * 
	 * @param groupId
	 * @return
	 */
	public static boolean validateGroupId(String groupId) {
		if (StringUtils.isBlank(groupId)) {
			throw new ValidationException(HttpStatus.BAD_REQUEST, "This value is a required item");
		}
		if (groupId.length() > 0 && groupId.length() < 10) {
			return true;
		} else {
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					"Please check your tpid,length can be between 1 and 9");
		}
	}

	/**
	 * Check date format 'YYYY-mm-dd'
	 * 
	 * @param date
	 * @return
	 */
	public static boolean validateDate(String date) {
		if (StringUtils.isBlank(date)) {
			throw new ValidationException(HttpStatus.BAD_REQUEST, "This value is a required item");
		}
		Matcher matcher = pattern.matcher(date);
		if (matcher.find()) {
			return true;
		} else {
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					"Please check your date format. The format must be YYYY-mm-hh");
		}
	}

	/**
	 * Check opco format
	 * 
	 * @param opco
	 * @return
	 */
	public static boolean validateOpco(String opco) {
		if (StringUtils.isBlank(String.valueOf(opco))) {
			throw new ValidationException(HttpStatus.BAD_REQUEST, "Opco value is a required item");
		}
		if (String.valueOf(opco).length() > 3) {
			throw new ValidationException(HttpStatus.BAD_REQUEST, "Please check your opco, please enter 3 digits");
		} else {
			try {
				Integer.parseInt(opco);
			} catch (NumberFormatException e) {
				throw new ValidationException(HttpStatus.BAD_REQUEST, "Opco# provided is invalid.");
			}

			return true;
		}
	}

	/**
	 * Check customer format
	 * 
	 * @param customer
	 * @return
	 */
	public static boolean validateCustomer(String customer) {
		if (StringUtils.isBlank(customer)) {
			throw new ValidationException(HttpStatus.BAD_REQUEST, "This value is a required item");
		}
		if (String.valueOf(customer).length() > 6) {
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					"Please check your customer id, A maximum of 6 digits");
		} else {
			if (!isNumeric(customer)) {
				throw new ValidationException(HttpStatus.BAD_REQUEST, "please enter a number ");
			}
		}
		return true;
	}

	/**
	 * Check reason format
	 *
	 * @param reason
	 * @return
	 */
	public static boolean validateReason(String reason) {
		if (StringUtils.isBlank(reason)) {
			throw new ValidationException(HttpStatus.BAD_REQUEST, "This value is a required item");
		}
		if (reason.length() > 200) {
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					"Please check your reason, around 200 varchar");
		}
		return true;
	}
}
