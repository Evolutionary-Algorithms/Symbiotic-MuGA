/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Zulu
 */
public class Debug {

    public static boolean IN_DEBUG = true;

    public static void print(String str) {
        if (IN_DEBUG) {
            System.out.print(str);
        }
    }

    public static void println(String str) {
        print(str + "\n");
    }
}
