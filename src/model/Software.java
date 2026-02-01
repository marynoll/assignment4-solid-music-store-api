package model;

public class Software extends ProductItem {
    private String developer;
    private String version;
    private String description;
    private Category category;

    public Software(int id, String name, double price, int stockQuantity, String manufacturer,
                    String developer, String version, String description, Category category) {
        super(id, name, price, stockQuantity, manufacturer);
        setDeveloper(developer);
        setVersion(version);
        setDescription(description);
        setCategory(category);
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        if (developer == null || developer.isBlank()) {
            throw new IllegalArgumentException("Developer cannot be null");
        }
        this.developer = developer;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        if (version == null || version.isBlank()) {
            throw new IllegalArgumentException("Version cannot be null");
        }
        this.version = version;
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
        return "Software";
    }

    @Override
    public String getProductInfo() {
        return String.format(
                "Product ID: %d%n" + "Name: %s%n" + "Product Type: %s%n" + "Developer: %s%n" +
                        "Version: %s%n" + "Category: %s%n" + "Price: $%.2f%n" + "Stock Quantity: %d%n" +
                        "Manufacturer: %s%n" + "Description: %s",
                getId(),
                getName(),
                getProductType(),
                developer,
                version,
                category != null ? category.getName() : "None",
                getPrice(),
                getStockQuantity(),
                getManufacturer(),
                description != null ? description : "No description available"
        );
    }
}