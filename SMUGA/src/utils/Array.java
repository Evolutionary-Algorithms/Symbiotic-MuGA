/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Random;

/**
 *
 * @author Zulu
 */
public class Array {

    public static int[] createLinearArray(int size) {
        int[] a = new int[size];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;;
        }
        return a;
    }

    public static void shuffle(int[] array) {
        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }
    public static int indexOf(int[] array, int val) {
        for (int i = 0; i < array.length; i++) {
            if( array[i]== val) return i;
        }
        return -1;
    }
    
    public static int[] clone( int[] a){
        int []b = new int[a.length]; 
        System.arraycopy(a, 0, b, 0, a.length);
        return b;
    }
    public static boolean contains(int[] array, int val) {
       return indexOf(array, val)!=-1;
    }

    public static boolean equals(int[] a, int[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < b.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }
}
