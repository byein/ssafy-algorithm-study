import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 벨만 포드 알고리즘
 * 
 * 1번 도시에서 다른 도시로 가는 경우 가장 빠른 시간 구하기. 음수 가중치 존재.
 * 
 * @author SSAFY
 *
 */
public class Main {

	static int N, M; // 정점수, 간선수
	static long[] distance; // 1번노드에서 현재노드까지 도달하는 최소 거리(0-index)
	static ArrayList<Edge> adjList = new ArrayList();; // 인접리스트 Edge는 to, cost

	static class Edge {
		int s, e, w;

		public Edge(int s, int e, int w) {
			super();
			this.s = s;
			this.e = e;
			this.w = w;
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 입력 및 초기화
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

//		adjList = new ArrayList();
//		for (int i = 0; i < N; i++) {
//			adjList[i] = new ArrayList<>();
//		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			adjList.add(new Edge(a - 1, b - 1, c));
		}

		// 벨만포드 - 사이클이 있다면 -1, 없다면 도시마다 최소 거리 출력.
		if (BellmanFord()) {
			for (int i = 1; i < N; i++) {
				if (distance[i] == Long.MAX_VALUE) {
					distance[i] = -1;
				}
				System.out.println(distance[i]);
			}
		} else {
			System.out.println(-1);
		}
	}

	/**
	 * 벨만포드 함수
	 * 
	 * @return
	 */
	private static boolean BellmanFord() {
		// 모든 노드에서 모든 간선으로 가는 경우를 정점의 개수만큼 실행.

		distance = new long[N];
		Arrays.fill(distance, Long.MAX_VALUE);
		distance[0] = 0;
		for (int i = 0; i < N; i++) {
			for (Edge cur : adjList) {
				if (distance[cur.s] != Long.MAX_VALUE && distance[cur.e] > distance[cur.s] + cur.w) {
					distance[cur.e] = distance[cur.s] + cur.w;

					if (i == N - 1) {
						// 한 번 더 모든 간선을 확인하며 최소 거리가 갱신된 경우를 찾음
						// 이때 해당 정점을 방문한 횟수가 N번 이상이면 음수 사이클이 존재한다고 판단
						return false;
					}
				}
			}
		}
		return true;
	}
}
