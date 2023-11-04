package src.question_2;

import java.util.Scanner;

public class Main {

    /* Function to get minimum number cost needed in worst case with R calls and K test spots */
    static int minCall(int R, int K)
    {
        // If there are no test spots, then no trials needed. OR if there is one test spot, one dollar (one call) needed.
        if (K == 1 || K == 0)
            return K;

        // We need K dollars for one call and K test spots
        if (R == 1)
            return K;

        int min = Integer.MAX_VALUE;
        int x, res;

        // Consider all calls from 1st test spot to kth test spots and return the minimum of these values plus 1.
        for (x = 1; x <= K; x++) {
            res = Math.max(minCall(R - 1, x - 1), minCall(R, K - x));
            if (res < min)
                min = res;
        }

        return min + 1;
    }

    // Main function
    public static void main(String args[])
    {
        int R, K;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of eggs:");
        R = sc.nextInt();
        System.out.print("Enter number of floors:");
        K = sc.nextInt();
        System.out.print("Minimum number of cost in worst case with " + R + " dollars and " + K + " test spots is " + minCall(R, K));
    }
}