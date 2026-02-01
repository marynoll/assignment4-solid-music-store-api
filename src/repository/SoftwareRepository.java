package repository;

import model.Software;
import model.Category;
import utils.DatabaseConnection;
import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SoftwareRepository {
    private CategoryRepository categoryRepository = new CategoryRepository();

    public void create(Software software) throws DatabaseOperationException {
        String sql = "INSERT INTO software (name, price, stock_quantity, manufacturer, " +
                "developer, version, description, category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, software.getName());
            stmt.setDouble(2, software.getPrice());
            stmt.setInt(3, software.getStockQuantity());
            stmt.setString(4, software.getManufacturer());
            stmt.setString(5, software.getDeveloper());
            stmt.setString(6, software.getVersion());
            stmt.setString(7, software.getDescription());
            stmt.setInt(8, software.getCategory() != null ? software.getCategory().getId() : 0);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new DatabaseOperationException("Creating software failed, no rows affected");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    software.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create software: " + e.getMessage(), e);
        }
    }

    public List<Software> getAll() throws DatabaseOperationException {
        List<Software> softwareList = new ArrayList<>();
        String sql = "SELECT * FROM software ORDER BY name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Software software = mapResultSetToSoftware(rs);
                softwareList.add(software);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to retrieve software: " + e.getMessage(), e);
        }

        return softwareList;
    }

    public Software getById(int id) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "SELECT * FROM software WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSoftware(rs);
                } else {
                    throw new ResourceNotFoundException("Software with ID " + id + " not found");
                }
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to retrieve software: " + e.getMessage(), e);
        }
    }

    public void update(int id, Software software) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "UPDATE software SET name = ?, price = ?, stock_quantity = ?, " +
                "manufacturer = ?, developer = ?, version = ?, description = ?, category_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, software.getName());
            stmt.setDouble(2, software.getPrice());
            stmt.setInt(3, software.getStockQuantity());
            stmt.setString(4, software.getManufacturer());
            stmt.setString(5, software.getDeveloper());
            stmt.setString(6, software.getVersion());
            stmt.setString(7, software.getDescription());
            stmt.setInt(8, software.getCategory() != null ? software.getCategory().getId() : 0);
            stmt.setInt(9, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Software with ID " + id + " not found");
            }

            software.setId(id);

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update software: " + e.getMessage(), e);
        }
    }

    public void delete(int id) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "DELETE FROM software WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Software with ID " + id + " not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete software: " + e.getMessage(), e);
        }
    }

    private Software mapResultSetToSoftware(ResultSet rs) throws SQLException, DatabaseOperationException {
        int categoryId = rs.getInt("category_id");
        Category category = null;

        try {
            category = categoryRepository.getById(categoryId);
        } catch (ResourceNotFoundException e) {
            throw new DatabaseOperationException("Category not found for software", e);
        }

        return new Software(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getInt("stock_quantity"),
                rs.getString("manufacturer"),
                rs.getString("developer"),
                rs.getString("version"),
                rs.getString("description"),
                category
        );
    }
}