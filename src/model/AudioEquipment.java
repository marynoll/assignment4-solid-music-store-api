package model;

import java.util.Objects;

public class AudioEquipment extends ProductItem {
    private String brand;
    private String model;
    private String description;
    private Category category;

    public AudioEquipment(int id, String name, double price, int stockQuantity, String manufacturer,
                          String brand, String model, String description, Category category) {
        super(id, name, price, stockQuantity, manufacturer);
        setBrand(brand);
        setModel(model);
        setDescription(description);
        setCategory(category);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand cannot be empty");
        }
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model cannot be empty");
        }
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNullElse(description, "No description available");
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String getProductType() {
        return "Audio Equipment";
    }

    @Override
    public String getProductInfo() {
        return String.format(
                "Product ID: %d%n" + "Name: %s%n" + "Product Type: %s%n" + "Brand: %s%n" +
                        "Model: %s%n" + "Category: %s%n" + "Price: $%.2f%n" + "Stock Quantity: %d%n" +
                        "Manufacturer: %s%n" + "Description: %s",
                getId(),
                getName(),
                getProductType(),
                brand,
                model,
                category != null ? category.getName() : "None",
                getPrice(),
                getStockQuantity(),
                getManufacturer(),
                description != null ? description : "No description available"
        );
    }
}