import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int N, K, cnt;
	static int[] dist = new int[200000];
	static int[] dx = { 1, -1, 2 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		ArrayDeque<Integer> queue = new ArrayDeque<>();
		queue.add(N);
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[N] = 0;
		while (!queue.isEmpty()) {
			int cur = queue.poll();
			if (cur == K)
				break;
			for (int i = 0; i < 3; i++) {
				if (i == 2) {
					int nxt = cur * dx[i];
					if (nxt < 0 || nxt > 100000)
						continue;
					if (dist[nxt] > dist[cur]) {
						dist[nxt] = dist[cur];
						queue.addFirst(nxt);
					}
				} else {
					int nxt = cur + dx[i];
					if (nxt < 0 || nxt > 100000)
						continue;
					if (dist[nxt] > dist[cur] + 1) {
						dist[nxt] = dist[cur] + 1;
						queue.addLast(nxt);
					}
				}
			}
		}
		System.out.println(dist[K]);
	}

}
