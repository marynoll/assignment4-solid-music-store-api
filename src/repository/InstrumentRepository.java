package repository;

import model.Instrument;
import model.Category;
import utils.DatabaseConnection;
import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstrumentRepository {
    private CategoryRepository categoryRepository = new CategoryRepository();

    public void create(Instrument instrument) throws DatabaseOperationException {
        String sql = "INSERT INTO instruments (name, price, stock_quantity, manufacturer, " +
                "instrument_type, model, description, sound_demo, category_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, instrument.getName());
            stmt.setDouble(2, instrument.getPrice());
            stmt.setInt(3, instrument.getStockQuantity());
            stmt.setString(4, instrument.getManufacturer());
            stmt.setString(5, instrument.getInstrumentType());
            stmt.setString(6, instrument.getModel());
            stmt.setString(7, instrument.getDescription());
            stmt.setString(8, instrument.getSoundDemo());
            stmt.setInt(9, instrument.getCategory() != null ? instrument.getCategory().getId() : 0);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new DatabaseOperationException("Creating instrument failed, no rows affected");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    instrument.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create instrument: " + e.getMessage(), e);
        }
    }

    public List<Instrument> getAll() throws DatabaseOperationException {
        List<Instrument> instruments = new ArrayList<>();
        String sql = "SELECT * FROM instruments ORDER BY name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Instrument instrument = mapResultSetToInstrument(rs);
                instruments.add(instrument);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to retrieve instruments: " + e.getMessage(), e);
        }

        return instruments;
    }

    public Instrument getById(int id) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "SELECT * FROM instruments WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToInstrument(rs);
                } else {
                    throw new ResourceNotFoundException("Instrument with ID " + id + " not found");
                }
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to retrieve instrument: " + e.getMessage(), e);
        }
    }

    public void update(int id, Instrument instrument) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "UPDATE instruments SET name = ?, price = ?, stock_quantity = ?, " +
                "manufacturer = ?, instrument_type = ?, model = ?, description = ?, " +
                "sound_demo = ?, category_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, instrument.getName());
            stmt.setDouble(2, instrument.getPrice());
            stmt.setInt(3, instrument.getStockQuantity());
            stmt.setString(4, instrument.getManufacturer());
            stmt.setString(5, instrument.getInstrumentType());
            stmt.setString(6, instrument.getModel());
            stmt.setString(7, instrument.getDescription());
            stmt.setString(8, instrument.getSoundDemo());
            stmt.setInt(9, instrument.getCategory() != null ? instrument.getCategory().getId() : 0);
            stmt.setInt(10, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Instrument with ID " + id + " not found");
            }

            instrument.setId(id);

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update instrument: " + e.getMessage(), e);
        }
    }

    public void delete(int id) throws DatabaseOperationException, ResourceNotFoundException {
        String sql = "DELETE FROM instruments WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Instrument with ID " + id + " not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete instrument: " + e.getMessage(), e);
        }
    }

    private Instrument mapResultSetToInstrument(ResultSet rs) throws SQLException, DatabaseOperationException {
        int categoryId = rs.getInt("category_id");
        Category category = null;

        try {
            category = categoryRepository.getById(categoryId);
        } catch (ResourceNotFoundException e) {
            throw new DatabaseOperationException("Category not found for instrument", e);
        }

        return new Instrument(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getInt("stock_quantity"),
                rs.getString("manufacturer"),
                rs.getString("instrument_type"),
                rs.getString("model"),
                rs.getString("description"),
                rs.getString("sound_demo"),
                category
        );
    }
}