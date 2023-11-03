package src.question_3;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static int minCandiesDistribution(int[] ratings) {
        int n = ratings.length;

        // Left
        int[] left = new int[n];
        Arrays.fill(left,1);
        for(int i=1; i<n; i++) {
            if (ratings[i] > ratings[i - 1])
                left[i] = left[i - 1] + 1;
        }

        // Right
        int[] right = new int[n];
        Arrays.fill(right,1);
        for(int i=n-2; i>=0; i--){
            if(ratings[i] > ratings[i+1])
                right[i] = right[i+1]+1;
        }

        // Answer
        int totalCandies = 0;
        int[] candyDistribution = new int[n];
        for(int i=0; i<n; i++){
//            totalCandies += Math.max(left[i],right[i]);
            if(left[i]>right[i]){
                totalCandies+=left[i];
                candyDistribution[i] = left[i];
            }
            else{
                totalCandies+=right[i];
                candyDistribution[i] = right[i];
            }
        }
        System.out.println("Candy Distribution");
        for(int i=0;i<n;i++){
            System.out.print(candyDistribution[i]+" ");
        }
        System.out.println();
        return totalCandies;
    }

    // Main function
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of children: ");
        int N = scanner.nextInt();
        int[] ratings = new int[N];

        System.out.println("Enter the ratings of children:");
        for (int i = 0; i < N; i++) {
            ratings[i] = scanner.nextInt();
        }

        scanner.close();

        int result = minCandiesDistribution(ratings);

        System.out.println("Total candies: " + result);
    }
}
