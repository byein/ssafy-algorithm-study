import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static int N;
	static int[] A;
	static int[] LIS;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());

		A = new int[N];
		LIS = new int[N];
		int size = 0;
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < N; i++) {
			int tmp = Arrays.binarySearch(LIS, 0, size, A[i]);

			if (tmp < 0)
				tmp = Math.abs(tmp) - 1;
			LIS[tmp] = A[i];
			if (size == tmp) {
				size++;
			}
		}
		System.out.println(size);

//		int mx = 0;
//		for (int i = 0; i < N; i++) {
//			LIS[i] = 1;
//			for (int j = 0; j < i; j++) {
//				if (A[j] < A[i] && LIS[i] < LIS[j] + 1) {
//					LIS[i] = LIS[j] + 1;
//				}
//			}
//			if (mx < LIS[i])
//				mx = LIS[i];
//		}
//
//		System.out.println(mx);

	}

}
