package br.com.blz.testjava.controller;

import br.com.blz.testjava.dto.ProductResponse;
import br.com.blz.testjava.model.Product;
import br.com.blz.testjava.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody Product product) {
        Product created = service.create(product);
        ProductResponse response = service.convertToResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{sku}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long sku, @RequestBody Product product) {
        Product updated = service.update(sku, product);
        return ResponseEntity.ok(service.convertToResponse(updated));
    }

    @GetMapping("/{sku}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long sku) {
        ProductResponse response = service.getBySku(sku);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long sku) {
        service.delete(sku);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> responses = service.getAll();
        return ResponseEntity.ok(responses);
    }
}
