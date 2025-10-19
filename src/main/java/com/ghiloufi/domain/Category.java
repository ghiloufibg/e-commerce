package com.ghiloufi.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record Category(
    String name, String description, List<Category> subCategories, List<Product> products) {

  public Category {
    Objects.requireNonNull(name, "Category name cannot be null");
    subCategories = subCategories == null ? List.of() : List.copyOf(subCategories);
    products = products == null ? List.of() : List.copyOf(products);
  }

  public Category(String name, String description) {
    this(name, description, List.of(), List.of());
  }

  public Category withName(String name) {
    return new Category(name, this.description, this.subCategories, this.products);
  }

  public Category withDescription(String description) {
    return new Category(this.name, description, this.subCategories, this.products);
  }

  public Category withSubCategory(Category category) {
    if (category == null) {
      throw new IllegalArgumentException("Category cannot be null");
    }
    if (category == this) {
      throw new IllegalArgumentException("A category cannot contain itself");
    }
    List<Category> newSubCategories = new ArrayList<>(subCategories);
    newSubCategories.add(category);
    return new Category(name, description, newSubCategories, products);
  }

  public Category withoutSubCategory(Category category) {
    List<Category> newSubCategories = new ArrayList<>(subCategories);
    newSubCategories.remove(category);
    return new Category(name, description, newSubCategories, products);
  }

  public Category withProduct(Product product) {
    if (product == null) {
      throw new IllegalArgumentException("Product cannot be null");
    }
    List<Product> newProducts = new ArrayList<>(products);
    newProducts.add(product);
    return new Category(name, description, subCategories, newProducts);
  }

  public Category withoutProduct(Product product) {
    List<Product> newProducts = new ArrayList<>(products);
    newProducts.remove(product);
    return new Category(name, description, subCategories, newProducts);
  }

  public List<Product> getAllProducts() {
    List<Product> allProducts = new ArrayList<>(products);
    for (Category subCategory : subCategories) {
      allProducts.addAll(subCategory.getAllProducts());
    }
    return allProducts;
  }

  public int getTotalProductCount() {
    int count = products.size();
    for (Category subCategory : subCategories) {
      count += subCategory.getTotalProductCount();
    }
    return count;
  }

  public boolean containsProduct(Product product) {
    if (products.contains(product)) {
      return true;
    }
    for (Category subCategory : subCategories) {
      if (subCategory.containsProduct(product)) {
        return true;
      }
    }
    return false;
  }
}
