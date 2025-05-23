package com.example.demo.ib;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.Product;

@RestController
public class IBApplication {

    private static Map<String, Product> productRepo = new HashMap<>();

    static {
        Product honey = new Product();
        honey.setId("1");
        honey.setName("Honey");
        productRepo.put(honey.getId(), honey);
        Product almond = new Product();
        almond.setId("2");
        almond.setName("Almond");
        productRepo.put(almond.getId(), almond);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable String id) {
        if (!productRepo.containsKey(id)) {
            throw new ProductNotFoundException();
        }
        productRepo.remove(id);
        return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable String id, @RequestBody Product product) {
        if (!productRepo.containsKey(id)) {
            throw new ProductNotFoundException();
        }
        productRepo.remove(id);
        product.setId(id);
        productRepo.put(id, product);
        return new ResponseEntity<>("Product updated", HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        productRepo.put(product.getId(), product);
        return new ResponseEntity<>("Product created", HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<Object> getProducts() {
        return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductsbyID(@PathVariable String id) {
        Product result = productRepo.get(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
