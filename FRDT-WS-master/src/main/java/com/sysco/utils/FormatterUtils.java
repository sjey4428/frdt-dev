package com.sysco.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class FormatterUtils {

    static DecimalFormat myFormatter = new DecimalFormat("#,##0;(#,##0)");
    static DecimalFormat myFormatterWithCents = new DecimalFormat("#,##0.000;(#,##0.000)");
    static DecimalFormat myOtherFormatterWithCents = new DecimalFormat("#,##0.000;-#,##0.000");
    static DecimalFormat formatterWithTwoDecimals = new DecimalFormat("#,##0.00;(#,##0.00)");

    //Setting CST TimeZone
    public static TimeZone theApplicationTimeZone = TimeZone.getTimeZone("CST");

    /**
     * CIPWAA-TZ004
     * This method returns the date in the format MM/dd/yyyy
     * Time Zone will be CST
     *
     * @return java.lang.String
     * @throws ParseException
     */
    public static String getCurrentDateAsCST() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //Setting CST TimeZone
        df.setTimeZone(theApplicationTimeZone);
        return df.format(date);
    }

    /**
     * CIPWAA-TZ004
     * This method returns the date in the format HH:mm:ss
     * Time Zone will be CST
     *
     * @return java.lang.String
     */
    public static String getCurrentTimeAsCST() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(theApplicationTimeZone);
        return df.format(date);
    }

    public static Date getNowDate(String day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            return df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getReviewDate(String day) {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yy");
        Date date = new Date();
        try {
            return df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getNowTime(String day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            return df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String strToDateFormat(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        formatter.setLenient(false);
        Date newDate= formatter.parse(date);
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(newDate);
    }

    public static String strToStrFormat(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy");
        formatter.setLenient(false);
        Date newDate= formatter.parse(date);
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(newDate);
    }

    /**
     * This method returns the formatted time stamp to be appended to the approver's comments.
     *
     * @return String
     * @throws ParseException
     */
    public static String getFormattedCommentTimeStamp() {
        StringBuffer timeStamp = new StringBuffer();
        //Calendar todaysDate = Calendar.getInstance();
        timeStamp.append(" [");
        //CIPWAA-TZ001
        timeStamp.append(FormatterUtils.getCurrentDateAsCST());
        timeStamp.append(" ");
        //CIPWAA-TZ001
        timeStamp.append(FormatterUtils.getCurrentTimeAsCST());
        timeStamp.append("]");

        return timeStamp.toString();
    }

    /**
     * This method returns the date in the format MM/dd/yyyy
     * Creation date: (10/25/00 2:54:21 PM)
     *
     * @param date java.util.Date
     * @return java.lang.String
     */
    public static String parseDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        if (date != null) {
            return df.format(date);
        } else {
            return StringUtils.trimToEmpty(null);
        }
    }

    /**
     * This method returns the date in the format HH:mm:ss
     * Creation date: (10/25/00 2:54:21 PM)
     *
     * @param date java.util.Date
     * @return java.lang.String
     */
    public static String parseTime(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        if (date != null) {
            return df.format(date);
        } else {
            return StringUtils.trimToEmpty(null);
        }
    }

    /**
     * This method string to a double
     */
    public static double getDouble(String aDoubleStr) {

        Number aNumber = null;
        if (aDoubleStr == null || aDoubleStr.length() == 0) {
            aDoubleStr = "0.00";
        }

        try {
            aNumber = myFormatterWithCents.parse(aDoubleStr);
            /*
					char[] removeChars = ",()".toCharArray();
					double aDouble = Double.parseDouble(TextUtilities.remove(aDoubleStr,removeChars));
			*/
        } catch (ParseException e) {
            try {
                // try using myOtherFormatterWithCents
                aNumber = myOtherFormatterWithCents.parse(aDoubleStr);
            } catch (ParseException ex) {
                throw new NumberFormatException();
            }
        }
        return aNumber.doubleValue();
    }

    /**
     * This method accepts a string containing a business unit number and formats
     * it for display as a string with leading zeroes.
     *
     * @param businessUnit A String containing the business unit number such as 4.
     * @return String A string formatted for an OpCo number such as 004.
     */
    public static String getFormattedOpCoString(String businessUnit) {

        StringBuffer buffer = new StringBuffer();

        //Check to see if opco is at least 3 chars long if not add leading 0
        int cnt = businessUnit.length();

        while (cnt < 3) {
            buffer.append("0");
            cnt++;
        }

        buffer.append(businessUnit);

        return buffer.toString();

    }

    /**
     * This method coverts a double to a currentcy formatted string
     */
    public static String formatCurrencyWithCents(double aDouble) {
        return formatterWithTwoDecimals.format(aDouble);
    }

    /**
     * This method coverts a double to a currentcy formatted string
     */
    public static String formatCurrency(double aDouble) {
        return myFormatter.format(aDouble);
    }

    /**
     * Returns zero if parameter is null other wise it converts the value to int
     *
     * @param value java.lang.String
     * @return int
     */
    public static int getInt(String value) {

        return getInteger(value).intValue();

    }

    /**
     * Safely converts a string to an Integer.
     *
     * @param value A String containing a number.
     * @return Integer If the conversion fails, contains a default value of zero.
     */
    public static Integer getInteger(String value) {
        String val = "0";

        if (value != null) {
            val = value.trim();
            if (val.length() == 0) {
                val = "0";
            }
        }

        Integer result = null;
        try {
            result = new Integer(val);
        } catch (NumberFormatException nfe) {
            result = new Integer("0");
        }

        return result;
    }

    public static Integer getInteger(String value, int defaultValue) {
        String val = String.valueOf(defaultValue);

        if (value != null) {
            val = value.trim();
            if (val.length() == 0) {
                val = String.valueOf(defaultValue);
            }
        }

        Integer result = null;
        try {
            result = new Integer(val);
        } catch (NumberFormatException nfe) {
            result = new Integer(defaultValue);
        }

        return result;
    }


    public static Long getLong(String value, long defaultValue) {
        String val = String.valueOf(defaultValue);

        if (value != null) {
            val = value.trim();
            if (val.length() == 0) {
                val = String.valueOf(defaultValue);
            }
        }

        Long result = null;
        try {
            result = new Long(val);
        } catch (NumberFormatException nfe) {
            result = new Long(defaultValue);
        }

        return result;
    }


    public static Boolean getBoolean(String value, boolean defaultValue) {
        String val = String.valueOf(defaultValue);

        if (value != null) {
            val = value.trim();
            if (val.length() == 0) {
                val = String.valueOf(defaultValue);
            }
        }

        return new Boolean(val);
    }

    /**
     * This method string to a double
     */
    public static double getRoundedDouble(String aDoubleStr) {
        return Double.parseDouble(getRoundedDoubleAsString(aDoubleStr));
    }

    /**
     * This method string to a double
     */
    public static String getRoundedDoubleAsString(String aDoubleStr) {
        return String.valueOf(Math.round(getDouble(aDoubleStr)));
    }

    /**
     * This method rounds up a double, then converts it to string
     */
    public static String getRoundedDoubleAsString(double aDouble) {
        return String.valueOf(Math.round(aDouble));
    }

    /**
     * This method rounds up a double, then converts it to string
     */
    public static double getRoundedDouble(double aDouble) {
        return (double) Math.round(aDouble);
    }

    public static String trimSafely(String s) {
        return StringUtils.trimToEmpty(s);
    }

    /**
     * This method coverts a string from an int
     * Creation date: (10/25/00 2:54:21 PM)
     */
    public static String getStringFromInt(int val) {

        return String.valueOf(val);
    }

    public static String parseExcel(Cell cell) {
        String result;
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = null;
                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
                            .getBuiltinFormat("h:mm")) {
                        sdf = new SimpleDateFormat("HH:mm");
                    } else {// date
                        sdf = new SimpleDateFormat("MM/dd/yy");
                    }
                    Date date = cell.getDateCellValue();
                    result = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 58) {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil
                            .getJavaDate(value);
                    result = sdf.format(date);
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    result = cell.getStringCellValue();
                }
                break;
            case HSSFCell.CELL_TYPE_STRING:
                result = cell.getRichStringCellValue().toString();
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                result = "";
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue() ? "TRUE" : "FALSE";
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                result = ErrorEval.getText(cell.getErrorCellValue());
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                result = cell.getCellFormula();
                break;
            default:
                result = "";
                break;
        }
        return result;
    }

    public static BigDecimal formatterBigDeci(String ex){
        if(StringUtils.isBlank(ex)){
           return new BigDecimal("0").setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        BigDecimal bigDecimal = new BigDecimal(ex).setScale(2,BigDecimal.ROUND_HALF_UP);
        return bigDecimal;
    }

}
