import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

	static int N, M, X;
	static ArrayList<Vertex>[] adjList;
	static int[] distance, distanceX;
	static PriorityQueue<Vertex> pq = new PriorityQueue<>();

	static class Vertex {
		int v, w;

		public Vertex(int v, int w) {
			super();
			this.v = v;
			this.w = w;
		}

		@Override
		public String toString() {
			return "Vertex [v=" + v + ", w=" + w + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		adjList = new ArrayList[N + 1];
		distance = new int[N + 1];
		distanceX = new int[N + 1];
		Arrays.fill(distance, Integer.MAX_VALUE);
		for (int i = 0; i <= N; i++) {
			adjList[i] = new ArrayList<Vertex>();
		}
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());

			adjList[s].add(new Vertex(e, t));
		}

		int mx = 0, mxIdx = 0;
		dijkstra(X);
		for (int i = 0; i <= N; i++) {
			distanceX[i] = distance[i];
		}
		for (int i = 1; i <= N; i++) {
			dijkstra(i);

			if (mx < distance[X] + distanceX[i]) {
				mx = distance[X] + distanceX[i];
				mxIdx = i;
			}

		}

		System.out.println(mx);
	}

	private static void dijkstra(int start) {
		distance = new int[N + 1];
		Arrays.fill(distance, Integer.MAX_VALUE);
		PriorityQueue<Vertex> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.w, o2.w));
		distance[start] = 0;
		pq.offer(new Vertex(start, 0));
		while (!pq.isEmpty()) {
			Vertex cur = pq.poll();
			if (distance[cur.v] < cur.w) {
				continue;
			}
			for (Vertex nxt : adjList[cur.v]) {
				if (distance[nxt.v] > cur.w + nxt.w) {
					distance[nxt.v] = cur.w + nxt.w;
					pq.offer(new Vertex(nxt.v, distance[nxt.v]));
				}
			}
		}
	}
}