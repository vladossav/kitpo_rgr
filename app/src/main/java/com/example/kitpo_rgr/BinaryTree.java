package com.example.kitpo_rgr;

import com.example.kitpo_rgr.types.UserType;

public interface BinaryTree {
    void init();
    UserType getByIndex(int m, int n);
    void insertByIndex(int n, UserType element);
    void balance();
    void deleteByIndex(int index);
    void show();
    String toString();
    void forEach(DoWith action);
}
