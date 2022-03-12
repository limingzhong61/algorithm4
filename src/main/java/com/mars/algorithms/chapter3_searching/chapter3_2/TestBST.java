package com.mars.algorithms.chapter3_searching.chapter3_2;

import edu.princeton.cs.algs4.StdIn;

public class TestBST {

	public static void main(String[] args) {
		BST<String, Integer> bst = new BST<>();
		for (int i = 0; !StdIn.isEmpty(); i++) {
			String key = StdIn.readString();
			bst.putRecur(key, i);
		}
		for (String s : bst.keys()) {
			System.out.println(s + " " + bst.get(s));
		}
		System.out.println("min(): " + bst.min());
		System.out.println("max(): " + bst.max());
		System.out.println("floor(\"F\"): " + bst.floor("F"));
		System.out.println("ceiling(\"C\"): " + bst.ceiling("C"));
		System.out.println("select(1): " + bst.select(1));
		System.out.println("rank(\"R\"): " + bst.rank("R"));
		System.out.println("delete(\"D\"):");
		bst.delete("D");
		for (String s : bst.keys()) {
			System.out.println(s + " " + bst.get(s));
		}
		System.out.println("deleteMin():");
		bst.deleteMin();
		for (String s : bst.keys()) {
			System.out.println(s + " " + bst.get(s));
		}
		System.out.println("deleteMax():");
		bst.deleteMax();
		for (String s : bst.keys()) {
			System.out.println(s + " " + bst.get(s));
		}
	}
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
min(): A
max(): X
floor("F"): E
ceiling("C"): C
select(1): C
rank("R"): 7
delete("D"):
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
deleteMin():
C 4
E 12
H 5
L 11
M 9
P 10
R 3
S 0
X 7
deleteMax():
C 4
E 12
H 5
L 11
M 9
P 10
R 3
S 0
* */