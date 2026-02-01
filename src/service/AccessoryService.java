package service;

import model.Accessory;
import repository.AccessoryRepository;
import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;

import java.util.List;

public class AccessoryService {

    private AccessoryRepository accessoryRepo = new AccessoryRepository();

    public void createAccessory(Accessory accessory) throws DatabaseOperationException {
        if (accessory.getName() == null || accessory.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        accessoryRepo.create(accessory);
    }

    public List<Accessory> getAllAccessories() throws DatabaseOperationException {
        return accessoryRepo.getAll();
    }

    public Accessory getAccessoryById(int id) throws DatabaseOperationException, ResourceNotFoundException {
        return accessoryRepo.getById(id);
    }

    public void updateAccessoryName(int id, String newName) throws DatabaseOperationException, ResourceNotFoundException {
        Accessory existing = accessoryRepo.getById(id);
        existing.setName(newName);
        accessoryRepo.update(id, existing);
    }


    public void deleteAccessory(int id) throws DatabaseOperationException, ResourceNotFoundException {
        accessoryRepo.delete(id);
    }

    public List<Accessory> getVinyls() throws DatabaseOperationException {
        return accessoryRepo.getAll();
    }
}