package controller;

import model.Accessory;
import model.Category;
import service.AccessoryService;
import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;

import java.util.List;
import java.util.Scanner;

public class AccessoryController {

    private AccessoryService service = new AccessoryService();
    private Scanner sc = new Scanner(System.in);

    public void createAccessory() {
        try {
            System.out.print("Name: "); String name = sc.nextLine();
            System.out.print("Price: "); double price = Double.parseDouble(sc.nextLine());
            System.out.print("Stock: "); int stock = Integer.parseInt(sc.nextLine());
            System.out.print("Manufacturer: "); String m = sc.nextLine();
            System.out.print("Type: "); String t = sc.nextLine();
            System.out.print("Description: "); String d = sc.nextLine();
            System.out.print("Category ID: "); int cId = Integer.parseInt(sc.nextLine());

            Accessory a = new Accessory(0, name, price, stock, m, t, d, new Category(cId, "Placeholder", "Desc"));
            service.createAccessory(a);
            System.out.println("Created ID: " + a.getId());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void listAllAccessories() {
        try {
            List<Accessory> list = service.getAllAccessories();
            if (list.isEmpty()) System.out.println("No accessories found.");
            else list.forEach(System.out::println);
        } catch (DatabaseOperationException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateAccessory() {
        try {
            System.out.print("ID to update: "); int id = Integer.parseInt(sc.nextLine());
            System.out.print("New name: "); String newName = sc.nextLine();
            service.updateAccessoryName(id, newName);
            System.out.println("Updated");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteAccessory() {
        try {
            System.out.print("ID to delete: "); int id = Integer.parseInt(sc.nextLine());
            service.deleteAccessory(id);
            System.out.println("Deleted");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void listVinyls() {
        try {
            List<Accessory> list = service.getAllAccessories();
            if (list.isEmpty()) System.out.println("No accessories found.");
            else list.forEach(System.out::println);
        } catch (DatabaseOperationException e) {
            System.err.println(e.getMessage());
        }
    }
}