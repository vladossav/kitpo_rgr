package com.example.kitpo_rgr;

import com.example.kitpo_rgr.types.IntType;
import com.example.kitpo_rgr.types.PropFract;
import com.example.kitpo_rgr.types.UserType;

import java.util.ArrayList;
import java.util.Arrays;


public class UserFactory {
    public static ArrayList<String> getTypeNameList() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("Int","ProperFraction"));
        return list;
    }

    public static UserType getBuilderByName(String name){
        switch(name) {
            case "Int":
                return new IntType();
            case "ProperFraction":
                return new PropFract();
            default:
                return null;
        }

    }
}
