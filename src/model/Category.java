package model;

public class Category implements Validation {
    private int id;
    private String name;
    private String description;

    public Category(int id, String name, String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }

    @Override
    public boolean isValid() {
        return name != null && description != null;
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
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.description = description;
    }
}