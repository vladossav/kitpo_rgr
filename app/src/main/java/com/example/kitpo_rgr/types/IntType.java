package com.example.kitpo_rgr.types;

import java.util.Comparator;

public class IntType implements UserType {
    private int value;

    public IntType(int val) {
        value = val;
    }
    public IntType() {}

    @Override
    public String typeName() {
        return "Int";
    }

    @Override
    public Object create() {
        return null;
    }

    @Override
    public Object clone() {
        return this;
    }

    @Override
    public Object readValue() {
        return value;
    }

    @Override
    public Object parseValue(String ss) {
        value = Integer.parseInt(ss);
        return this;
    }

    @Override
    public Comparator getTypeComparator() {
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int compare(UserType o1, UserType o2) {
        if (((IntType) o1).value == ((IntType) o2).value) return 0;
        if (((IntType) o1).value > ((IntType) o2).value) return 1;
        else return -1;
    }
}
