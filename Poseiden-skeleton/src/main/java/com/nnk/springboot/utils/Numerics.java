package com.nnk.springboot.utils;

public class Numerics {

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (java.lang.NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
