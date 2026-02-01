
import controller.AccessoryController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AccessoryController controller = new AccessoryController();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== ACCESSORY MANAGEMENT ===");
            System.out.println("1. Create Accessory");
            System.out.println("2. List All Accessories");
            System.out.println("3. Update Accessory Name");
            System.out.println("4. Delete Accessory");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> controller.createAccessory();
                case "2" -> controller.listAllAccessories();
                case "3" -> controller.updateAccessory();
                case "4" -> controller.deleteAccessory();
                case "5" -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}