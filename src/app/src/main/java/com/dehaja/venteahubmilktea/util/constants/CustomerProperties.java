package com.dehaja.venteahubmilktea.util.constants;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerProperties {
    private static final  HashMap<Integer, String> CUSTOMER_MENU = new HashMap();

    public static HashMap<Integer, String> getCustomerMenu() {
        HashMap<Integer, String> menu = new HashMap<>();
        menu.put(1001, "Product List");
        menu.put(1002, "Order Product");
        menu.put(1003, "Account");
        return CUSTOMER_MENU;
    }
}
