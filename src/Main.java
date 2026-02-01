import controller.ProductController;
import model.Accessory;
import model.Vinyl;
import service.*;
import repository.*;
import utils.DatabaseConnection;
import service.interfaces.ProductService;

import java.util.Scanner;

public class Main {
    private static ProductController controller;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== MUSIC STORE API ===");

        if (!DatabaseConnection.testConnection()) {
            System.err.println("DB connection failed.");
            return;
        }
        System.out.println("✓ DB connected");

        initDependencies();

        boolean running = true;
        while (running) {
            displayMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> controller.createVinyl();
                case "2" -> controller.listVinyls();
                case "3" -> controller.createAccessory();
                case "4" -> controller.listAccessories();
                case "5" -> controller.demoPolymorphism();
                case "6" -> controller.demoSorting();
                case "7" -> controller.demoReflection();
                case "8" -> {
                    System.out.print("Type (1=Vinyl, 2=Accessory): ");
                    int type = Integer.parseInt(scanner.nextLine());
                    System.out.print("ID: "); int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("New stock: "); int stock = Integer.parseInt(scanner.nextLine());
                    controller.updateStock(type, id, stock);
                }
                case "9" -> {
                    System.out.print("Type (1=Vinyl, 2=Accessory, 3=Category): ");
                    int type = Integer.parseInt(scanner.nextLine());
                    System.out.print("ID: "); int id = Integer.parseInt(scanner.nextLine());
                    controller.delete(type, id);
                }
                case "0" -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Invalid choice");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    private static void initDependencies() {
        CategoryRepository categoryRepo = new CategoryRepository();
        VinylRepository vinylRepo = new VinylRepository();
        AccessoryRepository accessoryRepo = new AccessoryRepository();

        CategoryService categoryService = new CategoryService(categoryRepo);

        ProductService<Vinyl> vinylService = new VinylService(vinylRepo, categoryRepo);
        ProductService<Accessory> accessoryService = new AccessoryService(accessoryRepo, categoryRepo);

        controller = new ProductController(categoryService, vinylService, accessoryService);

        System.out.println("✓ Dependencies initialized");
    }

    private static void displayMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Create Vinyl");
        System.out.println("2. List Vinyls");
        System.out.println("3. Create Accessory");
        System.out.println("4. List Accessories");
        System.out.println("5. Polymorphism Demo");
        System.out.println("6. Lambda Sorting Demo");
        System.out.println("7. Reflection Demo");
        System.out.println("8. Update Stock");
        System.out.println("9. Delete Product");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }
}