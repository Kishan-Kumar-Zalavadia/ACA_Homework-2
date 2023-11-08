package src.question_1;

import java.util.*;

class Item {
    int id;
    double weight;
    double value;
    char type;
    boolean status = false;


    public Item(int id, double weight, double value, char type) {
        this.id = id;
        this.weight = weight;
        this.value = value;
        this.type = type;
    }
    public void setStatus(boolean status){
        this.status = status;
    }
}

public class Main {
    public static class SelectedItem {
        Item item;
        double takenWeight;
        char type;

        public SelectedItem(Item item, double takenWeight) {
            this.item = item;
            this.takenWeight = takenWeight;
        }
    }

    public static double fractionalKnapsack(List<Item> items, double weightLimit, List<SelectedItem> selectedItems) {
        // Sort items by value-to-weight ratio in descending order
        Collections.sort(items, Comparator.comparingDouble(item -> -item.value / item.weight));

        double totalValue = 0;
        double remainingWeight = weightLimit;

        for (Item item : items) {
            if (remainingWeight <= 0) {
                break; // Knapsack is full
            }

            double fraction = Math.min(1, remainingWeight / item.weight);
            double addedWeight = fraction * item.weight;
            double addedValue = fraction * item.value;
            totalValue += addedValue;
            remainingWeight -= addedWeight;
            selectedItems.add(new SelectedItem(item, addedWeight));
        }

        return totalValue;
    }
    public static double knapsack(List<Item> items, double weightLimit, List<SelectedItem> selectedItems) {
        int n = items.size();
        double[][] dp = new double[n + 1][(int) weightLimit + 1];

        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= weightLimit; w++) {
                if (i == 0 || w == 0) {
                    dp[i][w] = 0;
                } else if (items.get(i - 1).weight <= w) {
                    dp[i][w] = Math.max(items.get(i - 1).value + dp[i - 1][(int) (w - items.get(i - 1).weight)], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        int remainingWeight = (int) weightLimit;
        for (int i = n; i > 0 && remainingWeight > 0; i--) {
            if (dp[i][remainingWeight] != dp[i - 1][remainingWeight]) {
                Item item = items.get(i - 1);
                double takenWeight = Math.min(remainingWeight, item.weight);
                selectedItems.add(new SelectedItem(item, takenWeight));
                item.setStatus(true);
                remainingWeight -= (int) takenWeight;
            }
        }

        List<Item> fractionalItems = new ArrayList<>();

        for (Item item : items) {
            if (item.type == 'F') {
                fractionalItems.add(item);
            }
        }
        Collections.sort(fractionalItems, Comparator.comparingDouble(item -> -item.value / item.weight));
        fractionalItems.removeIf(item -> item.status);

        double wholeItemAnswer = dp[n][(int) weightLimit];

        double fractionalItemAnswer = fractionalKnapsack(fractionalItems, remainingWeight, selectedItems);


        return wholeItemAnswer + fractionalItemAnswer;
    }

//    public static void main(String[] args) {
//
//        List<Item> items = new ArrayList<>();
//        items.add(new Item(1, 3, 50, 'W'));
//        items.add(new Item(2, 2, 30, 'F'));
//        items.add(new Item(3, 1, 20, 'W'));
//        items.add(new Item(4, 2, 40, 'F'));
//        items.add(new Item(5, 1, 10, 'W'));
//
//        double weightLimit = 7;
//
//
//        List<SelectedItem> selectedItems = new ArrayList<>();
//        double maxValue = knapsack(items, weightLimit, selectedItems);
//
//        System.out.println("Maximum value for the knapsack with weight limit " + weightLimit + " is: " + maxValue);
//        System.out.println("Selected items:");
//
//        for (SelectedItem selectedItem : selectedItems) {
//            System.out.println("Item " + selectedItem.item.id + " - Weight: " + selectedItem.takenWeight + ", Value: " + selectedItem.item.value + ", Type: " + selectedItem.item.type);
//        }
//    }

    public static void main(String[] args) {
        // Example code for generating random inputs
        List<Item> randomItems = generateRandomItems(10);
        int randomWeightLimit = generateRandomWeightLimit(10);

        List<SelectedItem> selectedItems = new ArrayList<>();
        double maxValue = knapsack(randomItems, randomWeightLimit, selectedItems);

        System.out.println("Selected items:");
        printItems(randomItems);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("Selected items in the knapsack:");
        printSelectedItems(selectedItems);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("Maximum value for the knapsack with weight limit " + randomWeightLimit + " is: " + maxValue);
    }

    public static List<Item> generateRandomItems(int count) {
        List<Item> items = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= count; i++) {
            int weight = random.nextInt(10) + 1; // Random weight between 1 and 10
            int value = random.nextInt(100); // Random value between 0 and 100
            char type = random.nextBoolean() ? 'W' : 'F'; // Randomly choose 'W' or 'F' type
            items.add(new Item(i, weight, value, type));
        }
        return items;
    }

    public static int generateRandomWeightLimit(int maxLimit) {
        Random random = new Random();
        return random.nextInt(maxLimit) + 1; // Random weight limit between 1 and maxLimit
    }

    public static void printItems(List<Item> items) {
        System.out.println("Input Items:");
        for (Item item : items) {
            System.out.println("Item " + item.id + " - Weight: " + item.weight + ", Value: " + item.value + ", Type: " + item.type);
        }
    }

    public static void printSelectedItems(List<SelectedItem> selectedItems) {
        for (SelectedItem selectedItem : selectedItems) {
            System.out.println("Item " + selectedItem.item.id + " - Weight: " + selectedItem.takenWeight + ", Value: " + selectedItem.item.value + ", Type: " + selectedItem.item.type);
        }
    }

}
