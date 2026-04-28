import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int R, C, N;
	static char[][] map;
	static int[] stick;
	static boolean[][] visited;
	static boolean[][] canDownMap;
	static int[] dx = { -1, 0, 1, 0 }, dy = { 0, -1, 0, 1 };
	static ArrayList<Point> minerals = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		// 입력 처리 및 초기화.
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		map = new char[R][C];
		canDownMap = new boolean[R][C];
		for (int i = 0; i < R; i++) {
			map[i] = br.readLine().toCharArray();
		}

		N = Integer.parseInt(br.readLine());
		stick = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			stick[i] = Integer.parseInt(st.nextToken());
		}

		// 반복

		for (int i = 0; i < N; i++) {

			// 미네랄 던지기
			shoot(i);

			visited = new boolean[R][C];
			// 클러스터가 따로 공기 중에 떠 있으면 떨어뜨리기
			bfs();

		}
		// 출력
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}

	// 미네랄 던지는 함수.
	static void shoot(int idx) {
		if (idx % 2 == 0) {
			// 왼쪽 -> 오른쪽으로 던지는 경우
			for (int i = 0; i < C; i++) {
				// 미네랄이 있으면
				if (map[R - stick[idx]][i] == 'x') {
					map[R - stick[idx]][i] = '.';
					break;
				}
			}
		} else {
			// 오른쪽 -> 왼쪽을 던지는 경우
			for (int i = C - 1; i >= 0; i--) {
				// 미네랄이 있으면
				if (map[R - stick[idx]][i] == 'x') {
					map[R - stick[idx]][i] = '.';
					break;
				}
			}
		}
	}

	// 따로 떨어진 클러스터 체크.
	static void bfs() {
		Queue<Point> queue = new ArrayDeque<>();

		// 땅에 붙어 있는 클러스트 먼저 방문.
		for (int i = 0; i < C; i++) {
			if (map[R - 1][i] == 'x' && !visited[R - 1][i]) {
				queue.add(new Point(R - 1, i));
				visited[R - 1][i] = true;
			}

			while (!queue.isEmpty()) {
				Point cur = queue.poll();

				for (int d = 0; d < 4; d++) {
					int nx = cur.x + dx[d];
					int ny = cur.y + dy[d];

					if (nx < 0 || nx >= R || ny < 0 || ny >= C || visited[nx][ny])
						continue;

					if (map[nx][ny] == 'x') {
						queue.add(new Point(nx, ny));
						visited[nx][ny] = true;
					}
				}
			}
		}

		// 공중에 있는 미네랄 따로 담고 떨어뜨리기.
		minerals = new ArrayList<>();
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (map[i][j] == 'x' && !visited[i][j]) {
					minerals.add(new Point(i, j));
					map[i][j] = '.';
					visited[i][j] = true;
				}
			}
		}

		if (!minerals.isEmpty()) {
			gravity();
		}
	}

	// 미네랄에 중력 작용하는 함수
	static void gravity() {
		boolean canDown = true;
		while (canDown) {
			for (Point nxt : minerals) {
				if (nxt.x + 1 >= R || map[nxt.x + 1][nxt.y] == 'x') {
					canDown = false;
					break;
				}
			}
			if (canDown) {
				for (Point nxt : minerals) {
					nxt.x++;
				}
			}
		}

		for (Point nxt : minerals) {
			map[nxt.x][nxt.y] = 'x';
		}
	}

}