package com.fit2081.viettran_33810672_fit2081_a2.utils;

public class AppUtils {
    // Private constructor to prevent instantiation
    private AppUtils() {
        // This constructor is intentionally empty
    }
    // SMS validation check functions
    public static boolean isBoolean(String s){
        return s.equalsIgnoreCase("TRUE") || s.equalsIgnoreCase("FALSE");
    }
    public static boolean containsOnlyLetters(String s){
        return s.matches("[a-zA-Z ]+");
    }
    public static boolean containsAtLeastOneAlpha(String s){return s.matches(".*[a-zA-Z]+.*");}
    public static boolean isNumeric(String s){
        return s.matches("\\d+");
    }
    public static boolean isPositiveInt(String s) {return s.matches("^[0-9]\\d*$");
    }

    public static boolean isAlphaNumericContainsWhiteSpace(String s){return s.matches("[a-zA-Z0-9 ]+");}
    public static boolean containsOnlySpace(String s){return s.matches(".*[a-zA-Z0-9].*");}
    public static int countOccurrences(String s, char c){
        int count = 0;
        for (int i = 0; i <s.length();i++){
            if(s.charAt(i) == c){
                count++;
            }
        }
        return count;
    }
}
