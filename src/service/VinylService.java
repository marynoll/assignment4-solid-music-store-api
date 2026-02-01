package service;

import model.Vinyl;
import repository.VinylRepository;
import repository.CategoryRepository;
import exception.*;
import service.interfaces.ProductService;

import java.util.List;

public class VinylService implements ProductService<Vinyl> {
    private final VinylRepository vinylRepository;
    private final CategoryRepository categoryRepository;

    public VinylService(VinylRepository vinylRepository, CategoryRepository categoryRepository) {
        this.vinylRepository = vinylRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void create(Vinyl vinyl)
            throws InvalidInputException, DatabaseOperationException, ResourceNotFoundException {

        validateVinyl(vinyl);
        verifyCategoryExists(vinyl);
        enforceBusinessRules(vinyl);

        List<Vinyl> existingVinyls = vinylRepository.getAll();
        for (Vinyl v : existingVinyls) {
            if (v.getName().equalsIgnoreCase(vinyl.getName()) &&
                    v.getArtist().equalsIgnoreCase(vinyl.getArtist()) &&
                    v.getReleaseYear() == vinyl.getReleaseYear()) {
                throw new DuplicateResourceException("Vinyl already exists");
            }
        }


        vinylRepository.create(vinyl);
    }

    @Override
    public List<Vinyl> getAll() throws DatabaseOperationException {
        return vinylRepository.getAll();
    }

    @Override
    public Vinyl getById(int id)
            throws DatabaseOperationException, ResourceNotFoundException {
        return vinylRepository.getById(id);
    }

    @Override
    public void update(int id, Vinyl vinyl)
            throws InvalidInputException, DatabaseOperationException, ResourceNotFoundException {

        validateVinyl(vinyl);
        verifyCategoryExists(vinyl);

        vinylRepository.update(id, vinyl);
    }

    @Override
    public void delete(int id)
            throws DatabaseOperationException, ResourceNotFoundException {
        vinylRepository.delete(id);
    }

    @Override
    public void updateStock(int id, int newQuantity)
            throws InvalidInputException, DatabaseOperationException, ResourceNotFoundException {

        validateStockQuantity(newQuantity);

        Vinyl vinyl = vinylRepository.getById(id);
        vinyl.setStockQuantity(newQuantity);
        vinylRepository.update(id, vinyl);
    }

    private void validateVinyl(Vinyl vinyl) throws InvalidInputException {
        if (!vinyl.isValid()) {
            throw new InvalidInputException("Vinyl validation failed");
        }
    }

    private void verifyCategoryExists(Vinyl vinyl)
            throws InvalidInputException, DatabaseOperationException, ResourceNotFoundException {
        if (vinyl.getCategory() == null || vinyl.getCategory().getId() <= 0) {
            throw new InvalidInputException("Valid category is required");
        }
        categoryRepository.getById(vinyl.getCategory().getId());
    }

    private void enforceBusinessRules(Vinyl vinyl) throws InvalidInputException {
        if (vinyl.getStockQuantity() > 1000) {
            throw new InvalidInputException("Stock quantity cannot exceed 1000 units");
        }

        if (vinyl.getSpeedRPM() != 33 && vinyl.getSpeedRPM() != 45 && vinyl.getSpeedRPM() != 78) {
            throw new InvalidInputException("Speed must be 33, 45, or 78 RPM");
        }
    }

    private void validateStockQuantity(int quantity) throws InvalidInputException {
        if (quantity < 0) {
            throw new InvalidInputException("Stock quantity cannot be negative");
        }
    }
}