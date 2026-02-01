package model;

public class Instrument extends ProductItem{
    private String instrumentType;
    private String model;
    private String description;
    private String soundDemo;
    private Category category;

    public Instrument(int id, String name, double price, int stockQuantity,
                      String manufacturer, String instrumentType, String model,
                      String description, String soundDemo, Category category) {
        super(id, name, price, stockQuantity, manufacturer);
        setInstrumentType(instrumentType);
        setModel(model);
        setDescription(description);
        setSoundDemo(soundDemo);
        setCategory(category);
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        if (instrumentType == null || instrumentType.isBlank()) {
            throw new IllegalArgumentException("Instrument type cannot be empty");
        }
        this.instrumentType = instrumentType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Material cannot be empty");
        }
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSoundDemo() {
        return soundDemo;
    }

    public void setSoundDemo(String soundDemo) {
        this.soundDemo = soundDemo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String getProductType() {
        return "Instrument";
    }

    @Override
    public String getProductInfo() {
        return String.format(
                "Product ID: %d%n" + "Name: %s%n" + "Product Type: %s%n" + "Model: %s%n" +
                        "Category: %s%n" + "Price: $%.2f%n" + "Stock Quantity: %d%n" +
                        "Manufacturer: %s%n" + "Description: %s",
                getId(),
                getName(),
                instrumentType,
                model,
                category != null ? category.getName() : "None",
                getPrice(),
                getStockQuantity(),
                getManufacturer(),
                description != null ? description : "No description available"
        );
    }

    public void demonstrateSound() {
        if (soundDemo != null) {
            System.out.println( "--🎵🎶" + "Demonstrating sound for " + getName() + ": " + soundDemo + "🎵🎶--");
        } else {
            System.out.println(getName() + " has no sound demo available.");
        }
    }
}
