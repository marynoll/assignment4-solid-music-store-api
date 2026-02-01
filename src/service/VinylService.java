package service;

import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;
import model.Vinyl;
import repository.VinylRepository;

import java.util.List;

public class VinylService {

    private VinylRepository vinylRepo = new VinylRepository();


    public Vinyl getByPrice() throws DatabaseOperationException, ResourceNotFoundException {
        return vinylRepo.getByPrice();
    }

}