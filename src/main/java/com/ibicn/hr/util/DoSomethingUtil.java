package com.ibicn.hr.util;

public class DoSomethingUtil implements Runnable {
    private String name;

    public DoSomethingUtil(String name) {
        this.name = name;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            for (long k = 0; k < 100000000; k++) ;
        }
    }
}