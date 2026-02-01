package model;

public class Accessory extends ProductItem {
    private String accessoryType;
    private String description;
    private Category category;

    public Accessory(int id, String name, double price, int stockQuantity, String manufacturer,
                     String accessoryType, String description, Category category) {
        super(id, name, price, stockQuantity, manufacturer);
        setAccessoryType(accessoryType);
        setDescription(description);
        setCategory(category);
    }

    public String getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(String accessoryType) {
        if (accessoryType == null || accessoryType.isBlank()) {
            throw new IllegalArgumentException("Accessory type cannot be null");
        }
        this.accessoryType = accessoryType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String getProductType() {
        return "Accessory";
    }

    @Override
    public String getProductInfo() {
        return String.format(
                "Product ID: %d%n" + "Name: %s%n" + "Product Type: %s%n" + "Accessory Type: %s%n" +
                        "Category: %s%n" + "Price: $%.2f%n" + "Stock Quantity: %d%n" +
                        "Manufacturer: %s%n" + "Description: %s",
                getId(),
                getName(),
                getProductType(),
                accessoryType,
                category,
                getPrice(),
                getStockQuantity(),
                getManufacturer(),
                description != null ? description : "No description available"
        );
    }

    @Override
    public String toString() {
        return "Accessory{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", stock=" + getStockQuantity() +
                ", manufacturer='" + getManufacturer() + '\'' +
                ", type='" + getAccessoryType() + '\'' +
                ", category=" + (getCategory() != null ? getCategory().getName() : "null") +
                '}';
    }
}