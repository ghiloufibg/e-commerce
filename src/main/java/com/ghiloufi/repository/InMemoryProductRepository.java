package com.ghiloufi.repository;

import com.ghiloufi.domain.Product;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryProductRepository {

  private final Map<Long, Product> products = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(1);

  public Long save(Product product) {
    if (product == null) {
      throw new IllegalArgumentException("Product cannot be null");
    }
    Long id = idGenerator.getAndIncrement();
    products.put(id, product);
    return id;
  }

  public Optional<Product> findById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    return Optional.ofNullable(products.get(id));
  }

  public List<Product> findAll() {
    return new ArrayList<>(products.values());
  }

  public boolean update(Long id, Product product) {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    if (product == null) {
      throw new IllegalArgumentException("Product cannot be null");
    }
    if (!products.containsKey(id)) {
      return false;
    }
    products.put(id, product);
    return true;
  }

  public boolean deleteById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    return products.remove(id) != null;
  }
}
