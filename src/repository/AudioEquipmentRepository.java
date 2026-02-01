package repository;

import model.AudioEquipment;
import model.Category;
import utils.DatabaseConnection;
import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AudioEquipmentRepository {
    private CategoryRepository categoryRepository = new CategoryRepository();

    public void create(AudioEquipment audioEquipment) throws DatabaseOperationException {
        String sql = "INSERT INTO audio_equipment (name, price, stock_quantity, manufacturer, " +
                "brand, model, description, category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, audioEquipment.getName());
            stmt.setDouble(2, audioEquipment.getPrice());
            stmt.setInt(3, audioEquipment.getStockQuantity());
            stmt.setString(4, audioEquipment.getManufacturer());
            stmt.setString(5, audioEquipment.getBrand());
            stmt.setString(6, audioEquipment.getModel());
            stmt.setString(7, audioEquipment.getDescription());
            stmt.setInt(8, audioEquipment.getCategory() != null ? audioEquipment.getCategory().getId() : 0);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new DatabaseOperationException("Creating audio equipment failed, no rows affected");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    audioEquipment.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create audio equipment: " + e.getMessage(), e);
        }
    }

    public List<AudioEquipment> getAll() throws DatabaseOperationException {
        List<AudioEquipment> equipmentList = new ArrayList<>();
        String sql = "SELECT * FROM audio_equipment ORDER BY brand, model";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AudioEquipment equipment = mapResultSetToAudioEquipment(rs);
                equipmentList.add(equipment);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to retrieve audio equipment: " + e.getMessage(), e);
        }

        return equipmentList;
    }

    public AudioEquipment getById(int id) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "SELECT * FROM audio_equipment WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAudioEquipment(rs);
                } else {
                    throw new ResourceNotFoundException("Audio equipment with ID " + id + " not found");
                }
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to retrieve audio equipment: " + e.getMessage(), e);
        }
    }

    public void update(int id, AudioEquipment audioEquipment) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "UPDATE audio_equipment SET name = ?, price = ?, stock_quantity = ?, " +
                "manufacturer = ?, brand = ?, model = ?, description = ?, category_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, audioEquipment.getName());
            stmt.setDouble(2, audioEquipment.getPrice());
            stmt.setInt(3, audioEquipment.getStockQuantity());
            stmt.setString(4, audioEquipment.getManufacturer());
            stmt.setString(5, audioEquipment.getBrand());
            stmt.setString(6, audioEquipment.getModel());
            stmt.setString(7, audioEquipment.getDescription());
            stmt.setInt(8, audioEquipment.getCategory() != null ? audioEquipment.getCategory().getId() : 0);
            stmt.setInt(9, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Audio equipment with ID " + id + " not found");
            }

            audioEquipment.setId(id);

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update audio equipment: " + e.getMessage(), e);
        }
    }

    public void delete(int id) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "DELETE FROM audio_equipment WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Audio equipment with ID " + id + " not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete audio equipment: " + e.getMessage(), e);
        }
    }

    private AudioEquipment mapResultSetToAudioEquipment(ResultSet rs) throws SQLException, DatabaseOperationException {
        int categoryId = rs.getInt("category_id");
        Category category = null;

        try {
            category = categoryRepository.getById(categoryId);
        } catch (ResourceNotFoundException e) {
            throw new DatabaseOperationException("Category not found for audio equipment", e);
        }

        return new AudioEquipment(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getInt("stock_quantity"),
                rs.getString("manufacturer"),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getString("description"),
                category
        );
    }
}