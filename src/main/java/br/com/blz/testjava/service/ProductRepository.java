package br.com.blz.testjava.service;

import br.com.blz.testjava.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProductRepository {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();

    public Product save(Product product) {
        products.put(product.getSku(), product);
        return product;
    }

    public Optional<Product> findBySku(Long sku) {
        return Optional.ofNullable(products.get(sku));
    }

    public void deleteBySku(Long sku) {
        products.remove(sku);
    }

    public boolean existsBySku(Long sku) {
        return products.containsKey(sku);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
}
