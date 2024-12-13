package br.com.blz.testjava.service;

import br.com.blz.testjava.dto.InventoryResponse;
import br.com.blz.testjava.dto.ProductResponse;
import br.com.blz.testjava.service.exceptions.ProductAlreadyExistsException;
import br.com.blz.testjava.service.exceptions.ProductNotFoundException;
import br.com.blz.testjava.model.Product;
import br.com.blz.testjava.model.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product create(Product product) {
        if (repository.existsBySku(product.getSku())) {
            throw new ProductAlreadyExistsException("Product with this SKU " + product.getSku() + " already exists.");
        }
        return repository.save(product);
    }

    public Product update(Long sku, Product product) {
        if (!sku.equals(product.getSku())) {
            throw new IllegalArgumentException("SKU in path and in payload do not match.");
        }

        if (!repository.existsBySku(sku)) {
            throw new ProductNotFoundException("Product not found for SKU " + sku);
        }

        return repository.save(product);
    }

    public ProductResponse getBySku(Long sku) {
        Product product = repository.findBySku(sku)
            .orElseThrow(() -> new ProductNotFoundException("Product not found for SKU " + sku));

        return convertToResponse(product);
    }

    public void delete(Long sku) {
        if (!repository.existsBySku(sku)) {
            throw new ProductNotFoundException("Product not found for SKU " + sku);
        }
        repository.deleteBySku(sku);
    }

    public ProductResponse convertToResponse(Product product) {
        int totalQuantity = product.getInventory().getWarehouses().stream()
            .mapToInt(Warehouse::getQuantity)
            .sum();
        boolean isMarketable = totalQuantity > 0;

        InventoryResponse invResp = new InventoryResponse();
        invResp.setQuantity(totalQuantity);
        invResp.setWarehouses(product.getInventory().getWarehouses());

        ProductResponse response = new ProductResponse();
        response.setSku(product.getSku());
        response.setName(product.getName());
        response.setMarketable(isMarketable);
        response.setInventory(invResp);

        return response;
    }

    public List<ProductResponse> getAll() {
        List<Product> products = repository.findAll();
        return products.stream().map(this::convertToResponse).collect(Collectors.toList());
    }
}
