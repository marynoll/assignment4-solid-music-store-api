package repository;

import model.Category;
import utils.DatabaseConnection;
import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    public void create(Category category) throws DatabaseOperationException {
        String sql = "INSERT INTO category (name, description) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new DatabaseOperationException("Creating category failed, no rows affected");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    category.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create category: " + e.getMessage(), e);
        }
    }

    public List<Category> getAll() throws DatabaseOperationException {
        List<Category> categories = new ArrayList<>();  // list of categories
        String sql = "SELECT * FROM category ORDER BY name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category category = mapResultSetToCategory(rs);  // single category from row
                categories.add(category);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to retrieve categories: " + e.getMessage(), e);
        }

        return categories;
    }

    public Category getById(int id) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "SELECT * FROM category WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategory(rs);
                } else {
                    throw new ResourceNotFoundException("Category with ID " + id + " not found");
                }
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to retrieve category: " + e.getMessage(), e);
        }
    }

    public void update(int id, Category category) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "UPDATE category SET name = ?, description = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setInt(3, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Category with ID " + id + " not found");
            }

            category.setId(id);

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update category: " + e.getMessage(), e);
        }
    }

    public void delete(int id) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "DELETE FROM category WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Category with ID " + id + " not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete category: " + e.getMessage(), e);
        }
    }

    public boolean existsByName(String name) throws DatabaseOperationException {
        String sql = "SELECT COUNT(*) FROM category WHERE name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to check category existence: " + e.getMessage(), e);
        }

        return false;
    }

    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        Category category = new Category(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description")
        );
        category.setId(rs.getInt("id"));
        return category;
    }
}
