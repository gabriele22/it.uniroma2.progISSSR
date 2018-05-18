package it.uniroma2.progisssr.rest;

import it.uniroma2.progisssr.controller.ProductController;
import it.uniroma2.progisssr.dto.ProductDto;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "product")
@CrossOrigin
public class ProductRestService {
    @Autowired
    private ProductController productController;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto newProductDto = productController.createProduct(productDto);
        return new ResponseEntity<>(newProductDto, HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        ProductDto productUpdatedDto = null;
        try {
            productUpdatedDto = productController.updateProduct(id, productDto);
        } catch (EntitaNonTrovataException e) {
            return new ResponseEntity<>(productUpdatedDto, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productUpdatedDto, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductDto>  findProduct(@PathVariable Long id) {
        ProductDto productDto = productController.findProductById(id);
        return new ResponseEntity<>(productDto, productDto == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        boolean deleted = productController.deleteProduct(id);
        return new ResponseEntity<>(deleted, deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<ProductDto>> findAllProduct() {
        List<ProductDto> productsDto = productController.findAllTickets();
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }


}
