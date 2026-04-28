import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static int N, M, input[];
	static int[][] dp;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		N = Integer.parseInt(br.readLine());
		input = new int[N];
		dp = new int[N][N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			input[i] = Integer.parseInt(st.nextToken());
			Arrays.fill(dp[i], -1);
		}
		M = Integer.parseInt(br.readLine());

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken()) - 1;
			int e = Integer.parseInt(st.nextToken()) - 1;
			sb.append(isPalindrome(s, e)).append("\n");
		}
		System.out.println(sb.toString());

	}

	private static int isPalindrome(int s, int e) {

		if (s > e)
			return 1;
		if (dp[s][e] != -1)
			return dp[s][e];
		if (s == e)
			return dp[s][e] = 1;
		if (input[s] != input[e])
			return dp[s][e] = 0;
		else
			return dp[s][e] = isPalindrome(s + 1, e - 1);

	}

}