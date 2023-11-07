package src.question_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Item {
    int id;
    double weight;
    double cost;
    char type;

    public Item(int id, double weight, double cost, char type) {
        this.id = id;
        this.weight = weight;
        this.cost = cost;
        this.type = type;
    }
}

public class Main {
    public static List<Item> knapsackCombined(List<Item> items, double weightLimit) {
        // Separate whole and fractional items into different lists
        List<Item> wholeItems = new ArrayList<>();
        List<Item> fractionalItems = new ArrayList<>();

        for (Item item : items) {
            if (item.type == 'W') {
                wholeItems.add(item);
            } else {
                fractionalItems.add(item);
            }
        }

        // Sort whole items in descending order of cost-to-weight ratio
        Collections.sort(wholeItems, Comparator.comparingDouble(item -> -item.cost / item.weight));

        List<Item> selectedItems = new ArrayList<>();
        double totalWeight = 0;
        double totalCost = 0;

        for (Item item : wholeItems) {
            // Add whole items to the knapsack while staying within the weight limit
            if (totalWeight + item.weight <= weightLimit) {
                selectedItems.add(item);
                totalWeight += item.weight;
                totalCost += item.cost;
            }
        }

        double remainingCapacity = weightLimit - totalWeight;

        // Sort fractional items in descending order of cost-to-weight ratio
        Collections.sort(fractionalItems, Comparator.comparingDouble(item -> -item.cost / item.weight));
        for (Item item : fractionalItems) {
            if (remainingCapacity > 0) {
                // Add as much of the current fractional item as possible to the knapsack
                double fraction = Math.min(remainingCapacity / item.weight, 1);
                selectedItems.add(new Item(item.id, fraction * item.weight, fraction * item.cost, item.type));
                totalCost += fraction * item.cost;
                remainingCapacity -= fraction * item.weight;
            }
        }

        return selectedItems;
    }

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, 3, 50, 'W'));
        items.add(new Item(2, 2, 25, 'W'));
        items.add(new Item(3, 4, 60, 'W'));
        items.add(new Item(4, 1, 15, 'W'));
        items.add(new Item(5, 3, 40, 'W'));
        items.add(new Item(6, 2, 30, 'W'));
        items.add(new Item(7, 3, 50, 'F'));
        items.add(new Item(8, 2, 30, 'F'));
        items.add(new Item(9, 4, 50, 'F'));
        items.add(new Item(10, 1, 10, 'F'));

        double weightLimit = 10;
        List<Item> selectedItems = knapsackCombined(items, weightLimit);

        System.out.println("Selected Items:");
        for (Item item : selectedItems) {
            System.out.println("ID: " + item.id + ", Weight: " + item.weight + ", Cost: " + item.cost);
        }

        double totalCost = selectedItems.stream().mapToDouble(item -> item.cost).sum();
        System.out.println("Total Cost: " + totalCost);
    }
}
