package com.orazbek_tulaganov.test.checker;

import java.util.ArrayList;

public class Tests{
    ArrayList<Test> TestList;

    public Tests() {
    }

    public Tests(ArrayList<Test> tests) {
        TestList = tests;
    }

    public ArrayList<Test> getTests() {
        return TestList;
    }

    public void setTests(ArrayList<Test> tests) {
        TestList = tests;
    }
}
