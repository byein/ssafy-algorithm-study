import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int N, M, score, map[][], dx[] = { -1, 0, 1, 0 }, dy[] = { 0, -1, 0, 1 };
	static int mxCnt, mxRow, mxCol, mxIdx;
	static ArrayList<Block> block = new ArrayList<>();
	static ArrayList<Point>[] blockPos;
	static boolean[][] visited;

	static class Block implements Comparable<Block> {
		int cnt, rainbow, row, col;

		public Block(int cnt, int rainbow, int row, int col) {
			super();
			this.cnt = cnt;
			this.rainbow = rainbow;
			this.row = row;
			this.col = col;
		}

		@Override
		public int compareTo(Block o) {
			if (this.cnt == o.cnt) {
				if (this.rainbow == o.rainbow) {
					if (this.row == o.row) {
						return o.col - this.col;
					}
					return o.row - this.row;
				}
				return o.rainbow - this.rainbow;
			}
			return o.cnt - this.cnt;
		}

		@Override
		public String toString() {
			return "Block [cnt=" + cnt + ", rainbow=" + rainbow + ", row=" + row + ", col=" + col + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		visited = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 오토 플레이 진행.

		while (true) { // 블록 그룳이 남아있는 동안 진행.
			visited = new boolean[N][N];
			block = new ArrayList<>();
			if (!isRemainBlock())
				break;
			// 1. 크기가 가장 큰 블록 찾기, 여러 개면 무지개 블록 수 가장 많은 것 > 기준 블록 행 가장 큰 것 > 기준 클록 열 가장 큰 것.
			Collections.sort(block);
			// 2. 1에서 찾은 블록 제거하기.
			Block biggest = block.get(0);
			removeBFS(map[biggest.row][biggest.col], biggest.row, biggest.col);
			remove();
			// 3. 중력 작용. -1 제외 모든 걸 아래로 내리기.
			gravity();
			// 4. 90도 반시계 반향 회전.
			rotate();
			// 5. 다시 중력 작용.
			gravity();

		}
		System.out.println(score);
	}

	private static void remove() {
		int cnt = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (visited[i][j]) {
					cnt++;
					map[i][j] = -10;
				}
			}
		}
		score += Math.pow(cnt, 2);

	}

	private static boolean isRemainBlock() {

		// 0. 블록 구하기.
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] > 0 && !visited[i][j]) {
					bfs(map[i][j], i, j);
				}
			}
		}

		if (block.isEmpty())
			return false;
		return true;
	}

//	00 01 02 03 04
//	10 11 12 13 14
//	20 21 22 23 24
//	30 31 32 33 34 
//	40 41 42 43 44

// 04 14 24 34 44
//	03 13 23 33 43
//	02 12 22 32 42
//	01 11 21 31 41
//	00 10 20 30 40
	private static void rotate() {
		int[][] newMap = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				newMap[i][j] = map[j][N - i - 1];
			}
		}
		map = newMap;
	}

	private static void gravity() {
		for (int j = 0; j < N; j++) {
			for (int i = N - 1; i >= 0; i--) {
				if (map[i][j] == -10 || map[i][j] == -1)
					continue;
				int dest = i + 1;
				while (dest < N && map[dest][j] == -10) {
					dest++;
				}
				if (dest == i + 1)
					continue;
				map[dest - 1][j] = map[i][j];
				map[i][j] = -10;
			}
		}
	}

	private static void bfs(int idx, int x, int y) {

		Queue<Point> queue = new ArrayDeque<>();
		queue.add(new Point(x, y));
		visited[x][y] = true;

		int cnt = 1;
		int rainbow = 0;
		while (!queue.isEmpty()) {
			Point cur = queue.poll();
			for (int i = 0; i < 4; i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				if (nx < 0 || nx >= N || ny < 0 || ny >= N)
					continue;
				if (visited[nx][ny])
					continue;
				if (map[nx][ny] == idx || map[nx][ny] == 0) {
					if (map[nx][ny] == 0)
						rainbow++;
					cnt++;
					visited[nx][ny] = true;
					queue.offer(new Point(nx, ny));
				}
			}
		}

		if (cnt > 1) {
			block.add(new Block(cnt, rainbow, x, y));
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == 0)
					visited[i][j] = false;
			}
		}

	}

	private static void removeBFS(int idx, int x, int y) {
		visited = new boolean[N][N];

		Queue<Point> queue = new ArrayDeque<>();
		queue.add(new Point(x, y));
		visited[x][y] = true;

		int cnt = 1;
		int rainbow = 0;
		while (!queue.isEmpty()) {
			Point cur = queue.poll();
			for (int i = 0; i < 4; i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				if (nx < 0 || nx >= N || ny < 0 || ny >= N)
					continue;
				if (visited[nx][ny] || map[nx][ny] == -10)
					continue;
				if (map[nx][ny] == idx || map[nx][ny] == 0) {
					if (map[nx][ny] == 0)
						rainbow++;
					cnt++;
					visited[nx][ny] = true;
					queue.offer(new Point(nx, ny));
				}
			}
		}

		if (cnt > 1) {
			block.add(new Block(cnt, rainbow, x, y));
		}

	}

}
