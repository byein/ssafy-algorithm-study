import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

// KMP 알고리즘(Knuth–Morris–Pratt Algorithm) 
// O(N+M)

public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char[] input = br.readLine().toCharArray();

		int inputLen = input.length;
		int mx = 0;
		int[] pi = new int[inputLen];
		// 부분일치 테이블 만들기.
		for (int idx = 0; idx < inputLen; idx++) {
			char[] str = new char[inputLen - idx];
			int len = str.length;
			for (int i = 0; i < len; i++) {
				str[i] = input[idx + i];
			}
			pi = new int[len];
			for (int i = 1, j = 0; i < len; i++) {
				while (j > 0 && str[i] != str[j])
					j = pi[j - 1];

				if (str[i] == str[j]) {
					pi[i] = ++j;
				} else {
					pi[j] = 0;
				}
			}
			for (int i = 0; i < len; i++) {
				mx = Math.max(mx, pi[i]);
			}

		}
		System.out.println(mx);

	}
}