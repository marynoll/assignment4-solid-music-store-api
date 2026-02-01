package model;

public class Vinyl extends ProductItem {
    private String artist;
    private String genre;
    private int releaseYear;
    private int speedRPM;
    private Category category;

    public Vinyl(int id, String name, double price, String manufacturer,
                 int stockQuantity, String artist, String genre,
                 int releaseYear, int speedRPM, Category category) {
        super(id, name, price, stockQuantity, manufacturer);
        setArtist(artist);
        setGenre(genre);
        setReleaseYear(releaseYear);
        setSpeedRPM(speedRPM);
        setCategory(category);
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        if (artist == null || artist.trim().isEmpty()) {
            throw new IllegalArgumentException("Artist cannot be empty");
        }
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        if (genre == null) {
            throw new IllegalArgumentException("Genre cannot be empty");
        }
        this.genre = genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        if (releaseYear < 1900 || releaseYear > 2026) {
            throw new IllegalArgumentException("Release year must be between 1900 and 2026");
        }
        this.releaseYear = releaseYear;
    }

    public int getSpeedRPM() {
        return speedRPM;
    }

    public void setSpeedRPM(int speedRPM) {
        if (speedRPM != 33 && speedRPM != 45 && speedRPM != 78) {
            throw new IllegalArgumentException("Speed must be 33, 45, or 78 RPM");
        }
        this.speedRPM = speedRPM;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String getProductType() {
        return "Vinyl Record";
    }

    @Override
    public String getProductInfo() {
        return String.format(
                "Product ID: %d%n" + "Name: %s%n" + "Product Type: %s%n" + "Artist: %s%n" +
                        "Genre: %s%n" + "Release Year: %d%n" + "Speed (RPM): %d%n" +
                        "Category: %s%n" + "Price: $%.2f%n" + "Stock Quantity: %d%n" + "Manufacturer: %s",
                getId(),
                getName(),
                getProductType(),
                artist,
                genre,
                releaseYear,
                speedRPM,
                category != null ? category.getName() : "None",
                getPrice(),
                getStockQuantity(),
                getManufacturer()
        );
    }

    @Override
    public boolean isValid() {
        return super.isValid() && artist != null
                && genre != null
                && (speedRPM == 33 || speedRPM == 45 || speedRPM == 78)
                && category != null;
    }

    @Override
    public String toString() {
        return "Vinyl{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", artist='" + getArtist() + '\'' +
                ", genre='" + getGenre() + '\'' +
                ", year=" + getReleaseYear() +
                ", rpm=" + getSpeedRPM() +
                ", category=" + getCategory().getName() +
                '}';
    }
}