

package com.school.management.util;

import java.util.regex.Pattern;

public class UtilService {
    public static boolean validateEmail(String email) {
        return Pattern.matches(Constants.EMAIL_PATTERN, email);
    }

    public static boolean validatePhone(String phone) {
        return Pattern.matches(Constants.PHONE_REGEX, phone);
    }
}































//// Regular expression for validating email addresses
//private static final String EMAIL_REGEX = "^[a-z0-9]+@[a-z0-9.-]+\\.[a-z]{2,6}$";
//
//// Regular expression for validating phone numbers
//private static final String PHONE_REGEX = "^(\\+91\\s)?(\\d{5}\\s?\\d{5}|\\d{3}\\s\\d{3}\\s\\d{4})$";
//
//public static boolean validateEmail(String email) {
//    boolean flag = false;
//    try {
//        // Compile regular expression to get the pattern
//        Pattern pattern = Pattern.compile(EMAIL_REGEX);
//        Matcher matcher = pattern.matcher(email);
//        // Create instance of matcher
//        flag = matcher.matches();
//        System.out.println(email + " : " + flag + "\n");
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    return flag;
//}
//
//public static boolean validatePhone(String phone) {
//    boolean flag = false;
//    try {
//        // Compile regular expression to get the pattern
//        Pattern pattern = Pattern.compile(PHONE_REGEX);
//        Matcher matcher = pattern.matcher(phone);
//        // Create instance of matcher
//        flag = matcher.matches();
//        System.out.println(phone + " : " + flag + "\n");
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    return flag;
//}
//}
