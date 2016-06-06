package com.example.admin.developcasttv.utils;

/**
 * Created by admin on 16/05/2016.
 */
public class teste {
    private static teste ourInstance = new teste();

    public static teste getInstance() {
        return ourInstance;
    }

    private teste() {
    }
}
