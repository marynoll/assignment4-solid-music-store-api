package model;

public abstract class ProductItem implements Validation, FinalPrice {
    private int id;
    private String name;
    private double price;
    private int stockQuantity;
    private String manufacturer;
    private double discountPercent = 0;

    public ProductItem(int id, String name, double price, int stockQuantity, String manufacturer){
        setId(id);
        setName(name);
        setPrice(price);
        setStockQuantity(stockQuantity);
        setManufacturer(manufacturer);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        if (manufacturer == null) {
            throw new IllegalArgumentException("Manufacturer cannot be empty");
        }
        this.manufacturer = manufacturer;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.stockQuantity = stockQuantity;
    }

    public void setDiscountPercent(double discountPercent) {
        if (discountPercent < 0 || discountPercent > 100)
            throw new IllegalArgumentException("Invalid discount value");
        this.discountPercent = discountPercent;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public abstract String getProductInfo();

    public abstract String getProductType();

    @Override
    public boolean isValid() {
        return name != null && price > 0 && manufacturer != null && stockQuantity >= 0;
    }

    @Override
    public double calculatePrice() {
        return applyDiscount();
    }

    public double applyDiscount() {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Invalid discount value");
        }
        return price - (price * discountPercent / 100);
    }
}
