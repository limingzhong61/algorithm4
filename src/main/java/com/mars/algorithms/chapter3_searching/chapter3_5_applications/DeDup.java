package com.mars.algorithms.chapter3_searching.chapter3_5_applications;

import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class DeDup {

	public static void main(String[] args) {
		SET<String> set = new SET<String>();
		while (!StdIn.isEmpty()) {
			String key = StdIn.readString();
			if (!set.contains(key)) {
				set.add(key);
				StdOut.print(key + " ");
			}
		}
	}
}
/*
it was the best of times it was the worst of times
it was the age of wisdom it was the age of foolishness
it was the epoch of belief it was the epoch of incredulity
it was the season of light it was the season of darkness
it was the spring of hope it was the winter of despair
output:
it was the best of times worst age wisdom foolishness epoch belief incredulity season light darkness spring hope winter despair
* */