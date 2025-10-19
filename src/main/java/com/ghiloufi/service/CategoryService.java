package com.ghiloufi.service;

import com.ghiloufi.domain.Category;
import com.ghiloufi.domain.Product;
import com.ghiloufi.repository.InMemoryCategoryRepository;
import java.util.List;
import java.util.Optional;

public class CategoryService {

  private final InMemoryCategoryRepository repository;

  public CategoryService(InMemoryCategoryRepository repository) {
    this.repository = repository;
  }

  public Long createCategory(String name, String description) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Category name cannot be null or blank");
    }
    Category category = new Category(name, description);
    return repository.save(category);
  }

  public Optional<Category> getCategoryById(Long id) {
    return repository.findById(id);
  }

  public List<Category> getAllCategories() {
    return repository.findAll();
  }

  public boolean updateCategory(Long id, Category category) {
    if (category == null) {
      throw new IllegalArgumentException("Category cannot be null");
    }
    return repository.update(id, category);
  }

  public boolean addSubCategory(Long parentId, Long subCategoryId) {
    Optional<Category> parentOpt = repository.findById(parentId);
    Optional<Category> subCategoryOpt = repository.findById(subCategoryId);

    if (parentOpt.isEmpty() || subCategoryOpt.isEmpty()) {
      return false;
    }

    Category parent = parentOpt.get();
    Category subCategory = subCategoryOpt.get();

    Category updatedParent = parent.withSubCategory(subCategory);
    return repository.update(parentId, updatedParent);
  }

  public boolean removeSubCategory(Long parentId, Long subCategoryId) {
    Optional<Category> parentOpt = repository.findById(parentId);
    Optional<Category> subCategoryOpt = repository.findById(subCategoryId);

    if (parentOpt.isEmpty() || subCategoryOpt.isEmpty()) {
      return false;
    }

    Category parent = parentOpt.get();
    Category subCategory = subCategoryOpt.get();

    Category updatedParent = parent.withoutSubCategory(subCategory);
    return repository.update(parentId, updatedParent);
  }

  public boolean addProductToCategory(Long categoryId, Product product) {
    if (product == null) {
      throw new IllegalArgumentException("Product cannot be null");
    }
    Optional<Category> categoryOpt = repository.findById(categoryId);
    if (categoryOpt.isEmpty()) {
      return false;
    }
    Category updatedCategory = categoryOpt.get().withProduct(product);
    return repository.update(categoryId, updatedCategory);
  }

  public boolean removeProductFromCategory(Long categoryId, Product product) {
    if (product == null) {
      throw new IllegalArgumentException("Product cannot be null");
    }
    Optional<Category> categoryOpt = repository.findById(categoryId);
    if (categoryOpt.isEmpty()) {
      return false;
    }
    Category updatedCategory = categoryOpt.get().withoutProduct(product);
    return repository.update(categoryId, updatedCategory);
  }

  public boolean deleteCategory(Long id) {
    return repository.deleteById(id);
  }
}
