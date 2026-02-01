package controller;

import model.*;
import service.*;
import exception.*;
import service.interfaces.ProductService;
import utils.ReflectionUtils;
import utils.SortingUtils;

import java.util.List;
import java.util.Scanner;

public class ProductController {
    private final ProductService<Vinyl> vinylService;
    private final ProductService<Accessory> accessoryService;
    private final CategoryService categoryService;
    private final Scanner scanner = new Scanner(System.in);

    public ProductController(CategoryService categoryService,
                             ProductService<Vinyl> vinylService,
                             ProductService<Accessory> accessoryService) {
        this.categoryService = categoryService;
        this.vinylService = vinylService;
        this.accessoryService = accessoryService;
    }

    public void createVinyl() {
        try {
            System.out.print("Name: "); String name = scanner.nextLine();
            System.out.print("Price: "); double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Manufacturer: "); String m = scanner.nextLine();
            System.out.print("Stock: "); int stock = Integer.parseInt(scanner.nextLine());
            System.out.print("Artist: "); String artist = scanner.nextLine();
            System.out.print("Genre: "); String genre = scanner.nextLine();
            System.out.print("Year: "); int year = Integer.parseInt(scanner.nextLine());
            System.out.print("RPM: "); int rpm = Integer.parseInt(scanner.nextLine());
            System.out.print("Category ID: "); int catId = Integer.parseInt(scanner.nextLine());

            Category cat = categoryService.getCategoryById(catId);
            Vinyl v = new Vinyl(0, name, price, m, stock, artist, genre, year, rpm, cat);
            vinylService.create(v);
            System.out.println("✓ Vinyl created: " + v.getId());
        } catch (Exception e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void createAccessory() {
        try {
            System.out.print("Name: "); String name = scanner.nextLine();
            System.out.print("Price: "); double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Manufacturer: "); String m = scanner.nextLine();
            System.out.print("Stock: "); int stock = Integer.parseInt(scanner.nextLine());
            System.out.print("Type: "); String type = scanner.nextLine();
            System.out.print("Description: "); String desc = scanner.nextLine();
            System.out.print("Category ID: "); int catId = Integer.parseInt(scanner.nextLine());

            Category cat = categoryService.getCategoryById(catId);
            Accessory a = new Accessory(0, name, price, stock, m, type, desc, cat);
            accessoryService.create(a);
            System.out.println("✓ Accessory created: " + a.getId());
        } catch (Exception e) { System.err.println("Error: " + e.getMessage()); }
    }

    public void listVinyls() {
        try { vinylService.getAll().forEach(System.out::println); }
        catch (DatabaseOperationException e) { System.err.println(e.getMessage()); }
    }

    public void listAccessories() {
        try { accessoryService.getAll().forEach(System.out::println); }
        catch (DatabaseOperationException e) { System.err.println(e.getMessage()); }
    }

    public void updateStock(int type, int id, int newStock) {
        try {
            switch (type) {
                case 1 -> vinylService.updateStock(id, newStock);
                case 2 -> accessoryService.updateStock(id, newStock);
            }
            System.out.println("✓ Stock updated");
        } catch (Exception e) { System.err.println(e.getMessage()); }
    }

    public void delete(int type, int id) {
        try {
            switch (type) {
                case 1 -> vinylService.delete(id);
                case 2 -> accessoryService.delete(id);
                case 3 -> categoryService.deleteCategory(id);
            }
            System.out.println("✓ Deleted");
        } catch (Exception e) { System.err.println(e.getMessage()); }
    }

    public void demoPolymorphism() {
        try {
            List<Vinyl> vinyls = vinylService.getAll();
            List<Accessory> accessories = accessoryService.getAll();
            for (ProductItem p : vinyls) System.out.println(p.getProductType() + ": " + p.getName());
            for (ProductItem p : accessories) System.out.println(p.getProductType() + ": " + p.getName());
        } catch (DatabaseOperationException e) { System.err.println(e.getMessage()); }
    }

    public void demoSorting() {
        try {
            List<Vinyl> vinyls = vinylService.getAll();
            SortingUtils.sortByPrice(vinyls, Vinyl::getPrice);
            vinyls.forEach(v -> System.out.println(v.getName() + " $" + v.getPrice()));
        } catch (DatabaseOperationException e) { System.err.println(e.getMessage()); }
    }

    public void demoReflection() {
        try {
            List<Vinyl> vinyls = vinylService.getAll();
            if (!vinyls.isEmpty()) ReflectionUtils.inspectClass(vinyls.get(0));
        } catch (DatabaseOperationException e) {
            System.err.println(e.getMessage());
        }
    }
}