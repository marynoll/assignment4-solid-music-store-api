package service.interfaces;

import exception.*;
import java.util.List;

public interface ProductService<T> {

    void create(T product) throws InvalidInputException, DatabaseOperationException, ResourceNotFoundException;

    List<T> getAll() throws DatabaseOperationException;

    T getById(int id) throws DatabaseOperationException, ResourceNotFoundException;

    void update(int id, T product) throws InvalidInputException, DatabaseOperationException, ResourceNotFoundException;

    void delete(int id) throws DatabaseOperationException, ResourceNotFoundException;

    void updateStock(int id, int newQuantity) throws InvalidInputException, DatabaseOperationException, ResourceNotFoundException;
}