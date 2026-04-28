import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 플로이드 와샬 알고리즘. 모든 정점 -> 모든 정점. 거쳐가는 정점 기준으로 알고리즘 수행!
 * 
 * @author SSAFY
 *
 */
public class Main {

	static int N, M;
	static long[][] distance;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		distance = new long[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				distance[i][j] = i == j ? 0 : Integer.MAX_VALUE;
			}
		}
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			distance[a - 1][b - 1] = distance[a - 1][b - 1] != 0 ? Math.min(distance[a - 1][b - 1], c) : c;
		}

		for (int k = 0; k < N; k++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
				}
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(distance[i][j] == Integer.MAX_VALUE ? 0 +" ": distance[i][j] + " ");
			}
			System.out.println();
		}
	}

}
