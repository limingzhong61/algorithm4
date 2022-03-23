package com.mars.algorithms.chapter6_context;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Particle;
import edu.princeton.cs.algs4.StdDraw;

/**
 * 基于事件模拟互相碰撞的粒子（框架）
 */
public class CollisionSystem {
	private class Event implements Comparable<Event> {
		private final double time;
		private final Particle a, b;
		private final int countA, countB;

		public Event(double t, Particle a, Particle b) {
			// 创造一个发生在时间t且与a和b相关的新事件
			this.time = t;
			this.a = a;
			this.b = b;
			if (a != null) {
				countA = a.count();
			} else {
				countA = -1;
			}
			if (b != null) {
				countB = b.count();
			} else {
				countB = -1;
			}
		}

		@Override
		public int compareTo(Event that) {
			if (this.time < that.time) {
				return -1;
			} else if (this.time > that.time) {
				return +1;
			} else {
				return 0;
			}
		}

		public boolean isValid() {
			if (a != null && a.count() != countA) {
				return false;
			}
			if (b != null && b.count() != countB) {
				return false;
			}
			return true;
		}
	}

	private MinPQ<Event> pq; // 优先队列
	private double t = 0.0; // 模拟时钟
	private Particle[] particles; // 粒子数组

	public CollisionSystem(Particle[] particles) {
		this.particles = particles;
	}

	/**
	 * 预测其他粒子的碰撞事件
	 * @param a
	 * @param limit
	 */
	private void predictCollisions(Particle a, double limit) {
		if (a == null) {
			return;
		}
		for (int i = 0; i < particles.length; i++) {
			// 将与particles[i]发生碰撞的事件插入pq中
			double dt = a.timeToHit(particles[i]);
			if (t + dt <= limit) {
				pq.insert(new Event(t + dt, a, particles[i]));
			}
		}
		double dtX = a.timeToHitVerticalWall();
		if (t + dtX <= limit) {
			pq.insert(new Event(t + dtX, a, null));
		}
		double dtY = a.timeToHitHorizontalWall();
		if (t + dtY <= limit) {
			pq.insert(new Event(t + dtY, null, a));
		}
	}

	public void redraw(double limit, double Hz) {
		// 重绘事件：重新画出所有粒子
		StdDraw.clear();
		for (int i = 0; i < particles.length; i++) {
			particles[i].draw();
		}
		StdDraw.show(20);
		if (t < limit) {
			pq.insert(new Event(t + 1.0 / Hz, null, null));
		}
	}

	/**
	 * 基于事件模拟互相碰撞的粒子（主循环）
	 * @param limit
	 * @param Hz
	 */
	public void simulate(double limit, double Hz) {
		pq = new MinPQ<Event>();
		for (int i = 0; i < particles.length; i++) {
			predictCollisions(particles[i], limit);
		}
		pq.insert(new Event(0, null, null));	// 添加重绘事件
		while (!pq.isEmpty()) {
			// 处理一个事件
			Event event = pq.delMin();
			if (!event.isValid()) {
				continue;
			}
			for (int i = 0; i < particles.length; i++) {
				particles[i].move(event.time - t);		// 更新粒子的位置
			}
			t = event.time;									// 和时间
			Particle a = event.a, b = event.b;
			if (a != null && b != null) {
				a.bounceOff(b);
			} else if (a != null && b == null) {
				a.bounceOffVerticalWall();
			} else if (a == null && b != null) {
				b.bounceOffHorizontalWall();
			} else if (a == null && b == null) {
				redraw(limit, Hz);
			}
			predictCollisions(a, limit);
			predictCollisions(b, limit);
		}
	}

	public static void main(String[] args) {
		StdDraw.show(0);
		int N = Integer.parseInt(args[0]);
		Particle[] particles = new Particle[N];

		for (int i = 0; i < N; i++) {
			particles[i] = new Particle();
		}
		CollisionSystem system = new CollisionSystem(particles);
		system.simulate(10000, 0.5);
	}
}
