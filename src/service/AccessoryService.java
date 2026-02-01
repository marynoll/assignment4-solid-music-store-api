package service;

import model.Accessory;
import repository.AccessoryRepository;
import repository.CategoryRepository;
import exception.*;
import service.interfaces.ProductService;

import java.util.List;

public class AccessoryService implements ProductService<Accessory> {
    private final AccessoryRepository accessoryRepository;
    private final CategoryRepository categoryRepository;

    public AccessoryService(AccessoryRepository accessoryRepository, CategoryRepository categoryRepository) {
        this.accessoryRepository = accessoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void create(Accessory accessory)
            throws InvalidInputException, DatabaseOperationException, ResourceNotFoundException {

        validateAccessory(accessory);
        verifyCategoryExists(accessory);
        enforceBusinessRules(accessory);

        accessoryRepository.create(accessory);
    }

    @Override
    public List<Accessory> getAll() throws DatabaseOperationException {
        return accessoryRepository.getAll();
    }

    @Override
    public Accessory getById(int id)
            throws DatabaseOperationException, ResourceNotFoundException {
        return accessoryRepository.getById(id);
    }

    @Override
    public void update(int id, Accessory accessory)
            throws InvalidInputException, DatabaseOperationException, ResourceNotFoundException {

        validateAccessory(accessory);
        verifyCategoryExists(accessory);

        accessoryRepository.update(id, accessory);
    }

    @Override
    public void delete(int id)
            throws DatabaseOperationException, ResourceNotFoundException {
        accessoryRepository.delete(id);
    }

    @Override
    public void updateStock(int id, int newQuantity)
            throws InvalidInputException, DatabaseOperationException, ResourceNotFoundException {

        validateStockQuantity(newQuantity);

        Accessory accessory = accessoryRepository.getById(id);
        accessory.setStockQuantity(newQuantity);
        accessoryRepository.update(id, accessory);
    }

    private void validateAccessory(Accessory accessory) throws InvalidInputException {
        if (!accessory.isValid()) {
            throw new InvalidInputException("Accessory validation failed");
        }
    }

    private void verifyCategoryExists(Accessory accessory)
            throws InvalidInputException, DatabaseOperationException, ResourceNotFoundException {
        if (accessory.getCategory() == null || accessory.getCategory().getId() <= 0) {
            throw new InvalidInputException("Valid category is required");
        }
        categoryRepository.getById(accessory.getCategory().getId());
    }

    private void enforceBusinessRules(Accessory accessory) throws InvalidInputException {
        if (accessory.getStockQuantity() > 1000) {
            throw new InvalidInputException("Stock quantity cannot exceed 1000 units");
        }
    }

    private void validateStockQuantity(int quantity) throws InvalidInputException {
        if (quantity < 0) {
            throw new InvalidInputException("Stock quantity cannot be negative");
        }
    }
}