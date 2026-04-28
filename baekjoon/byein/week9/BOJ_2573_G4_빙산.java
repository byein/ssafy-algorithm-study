import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int N, M, icebergCnt, ans, map[][];
	static boolean flag, visited[][];
	static Queue<Point> queue = new ArrayDeque<>();
	static int[] dx = { -1, 0, 1, 0 }, dy = { 0, -1, 0, 1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		flag = false; // bfs를 진행하면서 빙하가 갈아진 경우 flag true 처리 후 while 문 빠져나오기
		while (!flag) {
			init(); // 초기 세팅.
			// 빙하가 없으면 0 리턴.
			if (queue.size() == 0) {
				System.out.println(0);
				return;
			}
			// bfs
			bfs();
			// 플래그 설정된 경우 빠져나오기
			if (flag)
				break;
			// 답 증가
			ans++;
		}
		// 답 출력.
		System.out.println(ans);
	}

	/**
	 * 현재 빙하 개수 세고 큐에 빙하 값 하나 넣어두는 함수.
	 */
	private static void init() {
		icebergCnt = 0;
		Point tmp = null;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] > 0) {
					icebergCnt++;
					tmp = new Point(i, j);
				}
			}
		}
		// 만약 빙하가 있다면 큐에 빙하 좌표 넣기.
		if (tmp != null)
			queue.add(tmp);
	}

	/**
	 * 빙하를 돌면서 4방 주위에 빙하가 없는 경우는 cnt 증가해서 빼주고 아니면 bfs 탐색 계속 진행.
	 */
	private static void bfs() {
		int[][] diff = new int[N][M];
		int tmpCnt = 0; // bfs로 세는 빙하 개수
		visited = new boolean[N][M];
		while (!queue.isEmpty()) {
			Point cur = queue.poll();
			int cnt = 0; // 현재 빙하의 4방 주위 0의 개수.
			tmpCnt++;
			visited[cur.x][cur.y] = true;
			for (int i = 0; i < 4; i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				if (nx < 0 || ny < 0 || nx >= N || ny >= M || visited[nx][ny])
					continue;
				// 0이면 cnt 증가
				if (map[nx][ny] == 0) {
					cnt++;
				} else {
					// 아니면 큐에 넣고 방문처리
					queue.add(new Point(nx, ny));
					visited[nx][ny] = true;
				}

			}
			// diff 배열에 현재 빙하에서 cnt 뺀 값 저장.
			diff[cur.x][cur.y] = map[cur.x][cur.y] - cnt <= 0 ? 0 : map[cur.x][cur.y] - cnt;
		}

		// map을 diff로 교체
		map = diff;
		// 만약 bfs로 구한 빙하 개수가 현재 빙하 개수와 다르면 flag를 true로 처리.
		if (tmpCnt != icebergCnt) {
			flag = true;
		}
	}

}