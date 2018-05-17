package it.uniroma2.progisssr.rest;

import it.uniroma2.progisssr.controller.ProductController;
import it.uniroma2.progisssr.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "product")
public class ProductRestService {
    @Autowired
    private ProductController productController;
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productController.createProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }


}
