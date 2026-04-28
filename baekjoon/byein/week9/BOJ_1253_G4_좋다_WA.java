/*
 * ⚠️ 미해결 (WA) — BOJ 1253 좋다 (G4)
 * 제출번호: 59096435 / https://www.acmicpc.net/problem/1253
 * 마지막 시도 코드. 통과하지 못한 풀이라 정답 아님.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static int N, cnt, A[];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());

		A = new int[N];
		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < N; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(A);
		for (int i = 0; i < N; i++) {
			boolean flag = false;
			for (int j = 0; j < N; j++) {
				if (i == j)
					continue;
				if (flag)
					break;
				int res = Arrays.binarySearch(A, A[i] - A[j]);

				if (res > 0) {
					if (i != res && j != res) {
						cnt++;
						flag = true;
					}
					if ((i + 1 < N && A[i + 1] == A[i] && i == res) || (j + 1 < N && A[j + 1] == A[j] && j == res)) {
						cnt++;
						flag = true;
					}
				}
			}
			if (flag)
				continue;
		}
		System.out.println(cnt);
	}

}