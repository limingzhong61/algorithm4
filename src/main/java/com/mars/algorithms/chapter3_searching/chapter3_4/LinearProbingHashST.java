package com.mars.algorithms.chapter3_searching.chapter3_4;

/**
 * @author Lenovo
 */
public class LinearProbingHashST<Key, Value> {
	private int N;				// 符号表中键值对的总数
	private int M = 16;			// 线性探测表的大小
	private Key[] keys;			// 键
	private Value[] vals;		// 值

	public LinearProbingHashST(int M) {
		this.M = M;
		keys = (Key[]) new Object[M];
		vals = (Value[]) new Object[M];
	}

	private int hash(Key key) {
		return (key.hashCode() & 0x7fffffff) % M;
	}

	private void resize(int cap) {
		LinearProbingHashST<Key, Value> t = new LinearProbingHashST<Key, Value>(cap);
		for (int i = 0; i < M; i++) {
			if (keys[i] != null) {
				t.put(keys[i], vals[i]);
			}
		}
		keys = t.keys;
		vals = t.vals;
		M = t.M;
	}

	public void put(Key key, Value val) {
		//调整数组大小的方法来
		//保证散列表的使用率永远都
		//不会超过1/2。
		if (N >= M / 2) {
			resize(2 * M);
		}

		int i;
		for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
			if (keys[i].equals(key)) {
				vals[i] = val;
				return;
			}
		}
		keys[i] = key;
		vals[i] = val;
		N++;
	}

	public Value get(Key key) {
		for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
			if (keys[i].equals(key)) {
				return vals[i];
			}
		}
		return null;
	}

	public boolean contains(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to contains() is null");
		}
		return get(key) != null;
	}

	public void delete(Key key) {
		if (!contains(key)) {
			return;
		}
		int i = hash(key);
		while (!key.equals(keys[i])) {
			i = (i + 1) % M;
		}
		keys[i] = null;
		vals[i] = null;
		i = (i + 1) % M;
		while (keys[i] != null) {
			Key keyToRedo = keys[i];
			Value valToRedo = vals[i];
			keys[i] = null;
			vals[i] = null;
			N--;
			put(keyToRedo, valToRedo);
			i = (i + 1) % M;
		}
		N--;
		//保证所使用的内存量和表中的键值对数量的比例总在一定范围之内
		if (N > 0 && N <= M / 8) {
			resize(M / 2);
		}
	}
}
