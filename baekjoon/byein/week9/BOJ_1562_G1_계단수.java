import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	static final int MOD = 1_000_000_000;
	static int N;
	static long[][][] dp;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());

		dp = new long[N + 1][10][(1 << 10)+1]; // 자리, 현재 수, 사용한 수 정보.

		for (int i = 1; i < 10; i++) {
			dp[1][i][1 << i] = 1;
		}

		for (int i = 2; i <= N; i++) {
			for (int j = 0; j <= 9; j++) {
				for (int k = 0; k < (1 << 10); k++) {
					int nk = k | (1 << j);

					if (j == 0) {
						dp[i][j][nk] += dp[i - 1][j + 1][k] % MOD;
					} else if (j == 9) {
						dp[i][j][nk] += dp[i - 1][j - 1][k] % MOD;
					} else {
						dp[i][j][nk] += (dp[i - 1][j - 1][k] + dp[i - 1][j + 1][k]) % MOD;
					}
					
					dp[i][j][nk] %= MOD;
				}
			}
		}

		long sum = 0;
		for (int i = 0; i < 10; i++) {
			sum = (sum + dp[N][i][(1 << 10)-1]) % MOD;
		}
		System.out.println(sum);

	}

}
