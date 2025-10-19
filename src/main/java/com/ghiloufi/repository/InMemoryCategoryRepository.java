package com.ghiloufi.repository;

import com.ghiloufi.domain.Category;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryCategoryRepository {

  private final Map<Long, Category> categories = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(1);

  public Long save(Category category) {
    if (category == null) {
      throw new IllegalArgumentException("Category cannot be null");
    }
    Long id = idGenerator.getAndIncrement();
    categories.put(id, category);
    return id;
  }

  public Optional<Category> findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    return Optional.ofNullable(categories.get(id));
  }

  public List<Category> findAll() {
    return new ArrayList<>(categories.values());
  }

  public boolean update(Long id, Category category) {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    if (category == null) {
      throw new IllegalArgumentException("Category cannot be null");
    }
    if (!categories.containsKey(id)) {
      return false;
    }
    categories.put(id, category);
    return true;
  }

  public boolean deleteById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    return categories.remove(id) != null;
  }
}
