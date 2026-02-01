package controller;

import exception.DatabaseOperationException;
import model.Vinyl;
import model.Category;
import service.VinylService;

import java.util.List;
import java.util.Scanner;

public class VinylController {

    private VinylService service = new VinylService();
    private Scanner sc = new Scanner(System.in);

    public void listAllAccessories() {
        try {
            List<Vinyl> list = service.getByPrice();
            if (list.isEmpty()) System.out.println("No accessories found.");
            else list.forEach(System.out::println);
        } catch (DatabaseOperationException e) {
            System.err.println(e.getMessage());
        }
    }
}