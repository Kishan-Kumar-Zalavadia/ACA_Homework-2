package src.question_2;

import java.util.Scanner;

public class Main {

    static int minCall(int R, int K)
    {
        int dp[] = new int[R + 1], m;
        for (m = 0; dp[R] < K; m++) {
            for (int x = R; x > 0; x--) {
                dp[x] += 1 + dp[x - 1];
            }
        }
        return m;
    }

    // Main function
    public static void main(String[] args)
    {
        int R, K;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of calls: ");
        R = sc.nextInt();
        System.out.print("Enter number of test spots: ");
        K = sc.nextInt();
        System.out.print("Minimum cost: "+ minCall(R, K));

    }
}