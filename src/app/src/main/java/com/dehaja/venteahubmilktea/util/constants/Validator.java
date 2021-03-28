package com.dehaja.venteahubmilktea.util.constants;

public class Validator {

    public static boolean isEmpty(String ...inputs) {
        boolean isEmpty = false;
        for (String i : inputs) {
            if (i.isEmpty()) {
                isEmpty = true;
                break;
            }
        }
        return isEmpty;
    }

    public static boolean isResponseSuccess(String response) {
        return !response.isEmpty() && response.equalsIgnoreCase(Properties.SUCCESS);
    }
}
