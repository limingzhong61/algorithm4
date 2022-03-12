package com.mars.algorithms.chapter3_searching.chapter3_1;

import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Example {

    public static void main(String[] args) {
        ST<String, Integer> st;
        st = new ST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++)
        {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
    /*
S E A R C H E X A M P L E
^D
A 8
C 4
E 12
H 5
L 11
M 9
P 10
R 3
S 0
X 7
    * */
}
