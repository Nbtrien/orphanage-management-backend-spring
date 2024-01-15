package com.graduatebackend.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class ConvertUtils {
    /**
     * Convert from byte array to hex string
     *
     * @param bytes byte[]
     * @return String
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02x", b));
        }
        return hexStringBuilder.toString();
    }

    /**
     * Convert from hex String to bytes array
     *
     * @param hex String
     * @return byte[]
     */
    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * convert from double to currency format
     *
     * @param amount
     * @return
     */
    public static String formatToVND(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###.## VND");
        return decimalFormat.format(amount);
    }

    /**
     * format timestamp
     *
     * @param timestamp
     * @return
     */
    public static String formatTimestamp(Timestamp timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date(timestamp.getTime());
        return simpleDateFormat.format(date);
    }

    /**
     * generate Date from age
     *
     * @param age
     * @return
     */
    public static java.sql.Date generateFromAge(Integer age) {
        if (age == null || age < 0) {
            throw new IllegalArgumentException("Age must be a non-negative integer.");
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate minBirthDate = currentDate.minusYears(age);
        return java.sql.Date.valueOf(minBirthDate);
    }

    /**
     * get first date of month
     *
     * @param month
     * @param year
     * @return
     */
    public static java.sql.Date getFirstDayOfMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        long millis = calendar.getTimeInMillis();
        return new java.sql.Date(millis);
    }

    /**
     * get last date of month
     *
     * @param month
     * @param year
     * @return
     */
    public static java.sql.Date getLastDayOfMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        long millis = calendar.getTimeInMillis();
        return new java.sql.Date(millis);
    }

    /**
     * check is Hire Date After Current Date
     *
     * @param hireDate
     * @return
     */
    public static boolean isHireDateAfterCurrentDate(int month, int year, java.sql.Date hireDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        long millis = calendar.getTimeInMillis();
        java.sql.Date currentDate = new java.sql.Date(millis);

        return hireDate.after(currentDate);
    }
}
