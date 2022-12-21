package com.example.kitpo_rgr;

import com.example.kitpo_rgr.types.UserType;

import java.util.ArrayList;
import java.util.Arrays;


public class BinaryTreeAsArray implements BinaryTree {
    private UserType[] arr;
    private int size; //размер массива
    private int level;

    public BinaryTreeAsArray() {
        init();
    }

    public void init() {
        size = 1;
        level = 0;
        arr = new UserType[size];
    }

    BinaryTreeAsArray(UserType[] array) {
        level = calculateLevelFromSize(array.length);
        size = calculateSizeFromLevel(level);
        arr = Arrays.copyOf(array, size);
    }

    int sizerLSS(int n) {
        if (n>=size || arr[n]==null) return 0;
        return 1 + sizerLSS(2*n+1)+sizerLSS(2*n+2);
    }

    public UserType getByIndex(int m, int n) {
        if (m < 0 || m >= size || m >= sizerLSS(n)) return null;
        int ll = sizerLSS(2 * n + 1);
        if (m < ll) return getByIndex(m, 2 * n + 1);
        m -= ll;
        if (m-- == 0) return arr[n];
        return getByIndex(m, 2 * n + 2);
    }

    public void insertByIndex(int n, UserType element) {
        if (element == null) return;

        if (n >= size) {
            level++;
            size += calculateSizeOfLevel(level);
            arr = Arrays.copyOf(arr, size);
        }

        if (arr[n] == null) {
            arr[n] = element;
            return;
        }

        if (element.getTypeComparator().compare(element, arr[n]) > 0)
            insertByIndex(2*n + 2, element); //право
        else
            insertByIndex(2*n + 1, element); //влево
    }

    int getSize() {return size;}

    //балансировка
    void balance(ArrayList<UserType> array, int a, int b) {
        if (a>b) return;
        int m = (a + b) / 2;
        insertByIndex(0, array.get(m));
        balance(array, a, m-1);
        balance(array, m+1, b);
    }

    public int sizer(int n, ArrayList<UserType> arrayList) {//подсчет потомков
        if (n>=size || arr[n]==null) return 0;
        arrayList.add(arr[n]);
        return 1 + sizer(2*n+1,arrayList)+sizer(2*n+2,arrayList);
    }

    public void balance() {
        ArrayList<UserType> list = new ArrayList<>();
        int  size1 = sizer(0,list);
        list.sort(list.get(0).getTypeComparator());
        init();
        balance(list, 0, size1-1);
    }

    //удаление
    public void deleteByIndex(int index) {
        if (arr[index] == null) return;
        delete(0,arr[index]);
    }

    UserType delete(int root, UserType element) {
        if (2*root+2>=size) return null;
        if (arr[root] == null) return arr[root];


        if (element.getTypeComparator().compare(element, arr[root]) < 0) { //если элемент < зн. узла = к левому потомку
            arr[2*root + 1] = delete(2*root + 1, element);
            return arr[root];
        }

        if (element.getTypeComparator().compare(element, arr[root]) > 0) {//если элемент > зн. узла = к правому потомку
            arr[2 * root + 2] = delete(2 * root + 2, element);
            return arr[root];
        }
        // если element найден

        if (arr[2*root+1] == null) {
            int temp = 2*root+2;
            arr[root] = null;
            return arr[temp];
        }
        if (arr[2*root+2] == null) {
            int temp = 2*root+1;
            arr[root] = null;
            return arr[temp];
        }

        arr[2*root+2] = deleteHelper(2*root+2,root); //если есть оба потомка
        return arr[root];
    }

    UserType deleteHelper(int root, int root0) {
        if (2*root+2<size) {
            if (arr[2*root+1] != null) {
                arr[2*root+1] = deleteHelper(2*root+1, root0);
                return arr[root];
            }
        }

        arr[root0] = arr[root];
        arr[root] = null;
        if (2*root+2<size)  return arr[2*root + 2];
        else return null;
    }

    public void show() {
        for (int i = 0, cnt = 0, lvl = 0; i < size; i++) {
            if (i == cnt) {
                cnt += Math.pow(2, lvl);
                lvl++;
                System.out.println();
            }

            if (arr[i] == null) {
                System.out.print("N ");
                continue;
            }
            System.out.print(arr[i].toString() + " ");
        }
    }

    public String toString() {
        String str ="";
        for (int i = 0, cnt = 0, lvl = 0; i < size; i++) {
            if (i == cnt) {
                cnt += Math.pow(2, lvl);
                lvl++;
                str+="\n";
            }

            if (arr[i] == null) {
                str+="N ";
                continue;
            }
            str += (arr[i].toString() + " ");
        }
        return str;
    }

    private int calculateLevelFromSize(int sz) {
        int sum = 0;
        int level = -1;
        while (sum < sz) {
            level++;
            sum += Math.pow(2, level);
        }
        return level;
    }

    private int calculateSizeFromLevel(int level) {
        int sz = 0;
        for (int i = 0; i <= level; i++) {
            sz += Math.pow(2, i);
        }
        return sz;
    }

    private int calculateSizeOfLevel(int level) {
        return (int) Math.pow(2, level);
    }

    public void forEach(DoWith action) {
        for (int i=0; i<arr.length;i++) {
            String str;
            if (arr[i] == null) str = "null ";
            else str = arr[i].toString() + " ";
            action.doWith(str);
        }
    }

    public String getTreeStream() {
        StringBuilder str = new StringBuilder();
        for (UserType userType : arr) {
            if (userType == null) str.append("null ");
            else str.append(userType.toString()).append(" ");
        }
        return str.toString();
    }

}
