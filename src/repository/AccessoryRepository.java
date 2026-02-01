package repository;

import model.Accessory;
import model.Category;
import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccessoryRepository {


    public void create(Accessory accessory) throws DatabaseOperationException {
        String sql = "INSERT INTO accessory(name, price, stock_quantity, manufacturer, " +
                "accessory_type, description, category_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, accessory.getName());
            ps.setDouble(2, accessory.getPrice());
            ps.setInt(3, accessory.getStockQuantity());
            ps.setString(4, accessory.getManufacturer());
            ps.setString(5, accessory.getAccessoryType());
            ps.setString(6, accessory.getDescription());
            ps.setInt(7, accessory.getCategory() != null ? accessory.getCategory().getId() : 0);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new DatabaseOperationException("Creating accessory failed, no rows affected");
            }

            // Get generated ID and set it on the accessory object
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    accessory.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create accessory: " + e.getMessage(), e);
        }
    }

    public List<Accessory> getAll() throws DatabaseOperationException {
        List<Accessory> accessories = new ArrayList<>();
        String sql = "SELECT * FROM accessory ORDER BY name";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Accessory accessory = mapResultSetToAccessory(rs);
                accessories.add(accessory);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get accessories: " + e.getMessage(), e);
        }

        return accessories;
    }

    public Accessory getById(int id) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "SELECT * FROM accessory WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAccessory(rs);
                } else {
                    throw new ResourceNotFoundException("Accessory with ID " + id + " not found");
                }
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get accessory by ID: " + e.getMessage(), e);
        }
    }

    public void update(int id, Accessory accessory) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "UPDATE accessory SET name = ?, price = ?, stock_quantity = ?, " +
                "manufacturer = ?, accessory_type = ?, description = ?, category_id = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, accessory.getName());
            ps.setDouble(2, accessory.getPrice());
            ps.setInt(3, accessory.getStockQuantity());
            ps.setString(4, accessory.getManufacturer());
            ps.setString(5, accessory.getAccessoryType());
            ps.setString(6, accessory.getDescription());
            ps.setInt(7, accessory.getCategory() != null ? accessory.getCategory().getId() : 0);
            ps.setInt(8, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Accessory with ID " + id + " not found");
            }

            accessory.setId(id);

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update accessory: " + e.getMessage(), e);
        }
    }

    public void delete(int id) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "DELETE FROM accessory WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Accessory with ID " + id + " not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete accessory: " + e.getMessage(), e);
        }
    }
    private Accessory mapResultSetToAccessory(ResultSet rs) throws SQLException {
        Category category = new Category(rs.getInt("category_id"),
                "Category ID: " + rs.getInt("category_id"),
                "Placeholder description"
        );

        return new Accessory(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getInt("stock_quantity"),
                rs.getString("manufacturer"),
                rs.getString("accessory_type"),
                rs.getString("description"),
                category
        );
    }

    private Accessory mapResultSetToAccessoryWithFullCategory(ResultSet rs, CategoryRepository categoryRepo)
            throws SQLException, DatabaseOperationException {
        try {
            Category category = categoryRepo.getById(rs.getInt("category_id"));

            return new Accessory(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock_quantity"),
                    rs.getString("manufacturer"),
                    rs.getString("accessory_type"),
                    rs.getString("description"),
                    category
            );
        } catch (ResourceNotFoundException e) {
            throw new DatabaseOperationException("Category not found for accessory", e);
        }
    }
}