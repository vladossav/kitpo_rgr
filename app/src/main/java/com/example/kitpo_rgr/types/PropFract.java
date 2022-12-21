package com.example.kitpo_rgr.types;

import java.util.Comparator;

public class PropFract implements UserType {
    private int intPart;
    private int numerator; //числитель
    private int denominator; //знаменатель

    public PropFract() {}

    public PropFract(int intPart, int num, int denom) {
        num = Math.abs(num);
        denom = Math.abs(denom);
        if (num >= denom) {
            this.numerator = denom;
            this.denominator = num;
        } else {
            this.numerator = Math.abs(num);
            this.denominator = Math.abs(denom);
        }
        this.intPart = intPart;
    }

    @Override
    public String typeName() {
        return "ProperFraction";
    }

    @Override
    public Object create() {
        return null;
    }

    @Override
    public Object clone() {
        return null;
    }

    @Override
    public Object readValue() {
        return null;
    }

    @Override
    public Object parseValue(String ss) {
        String[] numStr = ss.split("/|'");
        intPart = Integer.parseInt(numStr[0]);
        numerator = Integer.parseInt(numStr[1]);
        denominator = Integer.parseInt(numStr[2]);
        return this;
    }

    @Override
    public String toString() {
        return intPart+"'"+numerator+"/"+denominator;
    }

    @Override
    public Comparator getTypeComparator() {
        return this;
    }

    @Override
    public int compare(UserType o1, UserType o2) {
        if (((PropFract) o1).intPart == ((PropFract) o2).intPart) {
            if (((PropFract) o1).numerator * ((PropFract) o2).denominator ==
                    ((PropFract) o2).numerator * ((PropFract) o1).denominator) return 0;
            if (((PropFract) o1).numerator * ((PropFract) o2).denominator >
                    ((PropFract) o2).numerator * ((PropFract) o1).denominator) return 1;
            else return -1;
        }
        if (((PropFract) o1).intPart > ((PropFract) o2).intPart) return 1;
        else return -1;
   }
}
