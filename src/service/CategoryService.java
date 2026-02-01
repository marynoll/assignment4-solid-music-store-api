
package service;

import model.Category;
import repository.CategoryRepository;
import exception.*;

import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(Category category)
            throws InvalidInputException, DuplicateResourceException, DatabaseOperationException {

        validateCategory(category);
        checkForDuplicates(category);

        categoryRepository.create(category);
    }

    public List<Category> getAllCategories() throws DatabaseOperationException {
        return categoryRepository.getAll();
    }

    public Category getCategoryById(int id)
            throws DatabaseOperationException, ResourceNotFoundException {
        return categoryRepository.getById(id);
    }

    public void updateCategory(int id, Category category)
            throws InvalidInputException, DatabaseOperationException, ResourceNotFoundException {

        validateCategory(category);

        categoryRepository.update(id, category);
    }

    public void deleteCategory(int id)
            throws DatabaseOperationException, ResourceNotFoundException {
        categoryRepository.delete(id);
    }

    private void validateCategory(Category category) throws InvalidInputException {
        if (!category.isValid()) {
            throw new InvalidInputException("Category validation failed");
        }
    }

    private void checkForDuplicates(Category category)
            throws DuplicateResourceException, DatabaseOperationException {
        if (categoryRepository.existsByName(category.getName())) {
            throw new DuplicateResourceException(
                    "Category with name '" + category.getName() + "' already exists");
        }
    }
}