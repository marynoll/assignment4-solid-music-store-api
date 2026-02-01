package repository;

import model.Vinyl;
import model.Category;
import utils.DatabaseConnection;
import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VinylRepository {
    private CategoryRepository categoryRepository = new CategoryRepository();

    public void create(Vinyl vinyl) throws DatabaseOperationException {
        String sql = "INSERT INTO vinyl (name, price, manufacturer, stock_quantity, " +
                "artist, genre, release_year, speed_rpm, category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, vinyl.getName());
            stmt.setDouble(2, vinyl.getPrice());
            stmt.setString(3, vinyl.getManufacturer());
            stmt.setInt(4, vinyl.getStockQuantity());
            stmt.setString(5, vinyl.getArtist());
            stmt.setString(6, vinyl.getGenre());
            stmt.setInt(7, vinyl.getReleaseYear());
            stmt.setInt(8, vinyl.getSpeedRPM());
            stmt.setInt(9, vinyl.getCategory().getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new DatabaseOperationException("Creating vinyl failed, no rows affected");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    vinyl.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create vinyl: " + e.getMessage(), e);
        }
    }

    public List<Vinyl> getAll() throws DatabaseOperationException {
        List<Vinyl> vinyls = new ArrayList<>();
        String sql = "SELECT * FROM vinyl ORDER BY artist, name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vinyl vinyl = mapResultSetToVinyl(rs);
                vinyls.add(vinyl);
            }

        } catch (SQLException | ResourceNotFoundException e) {
            throw new DatabaseOperationException("Failed to retrieve vinyls: " + e.getMessage(), e);
        }

        return vinyls;
    }

    public Vinyl getById(int id) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "SELECT * FROM vinyls WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToVinyl(rs);
                } else {
                    throw new ResourceNotFoundException("Vinyl with ID " + id + " not found");
                }
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to retrieve vinyl: " + e.getMessage(), e);
        }
    }

    public void update(int id, Vinyl vinyl) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "UPDATE vinyls SET name = ?, price = ?, manufacturer = ?, " +
                "stock_quantity = ?, artist = ?, genre = ?, release_year = ?, " +
                "speed_rpm = ?, category_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vinyl.getName());
            stmt.setDouble(2, vinyl.getPrice());
            stmt.setString(3, vinyl.getManufacturer());
            stmt.setInt(4, vinyl.getStockQuantity());
            stmt.setString(5, vinyl.getArtist());
            stmt.setString(6, vinyl.getGenre());
            stmt.setInt(7, vinyl.getReleaseYear());
            stmt.setInt(8, vinyl.getSpeedRPM());
            stmt.setInt(9, vinyl.getCategory().getId());
            stmt.setInt(10, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Vinyl with ID " + id + " not found");
            }

            vinyl.setId(id);

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update vinyl: " + e.getMessage(), e);
        }
    }

    public void delete(int id) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "DELETE FROM vinyls WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Vinyl with ID " + id + " not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete vinyl: " + e.getMessage(), e);
        }
    }

    private Vinyl mapResultSetToVinyl(ResultSet rs) throws SQLException, DatabaseOperationException, ResourceNotFoundException {
        Vinyl vinyl = new Vinyl(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getString("manufacturer"),
                rs.getInt("stock_quantity"),
                rs.getString("artist"),
                rs.getString("genre"),
                rs.getInt("release_year"),
                rs.getInt("speed_rpm"),
                categoryRepository.getById(rs.getInt("category_id"))
        );

        // Fetch category
        int categoryId = rs.getInt("category_id");
        try {
            Category category = categoryRepository.getById(categoryId);
            vinyl.setCategory(category);
        } catch (ResourceNotFoundException e) {
            throw new DatabaseOperationException("Category not found for vinyl", e);
        }

        return vinyl;
    }
}