package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.ProductDao;
import it.uniroma2.progisssr.dto.ProductDto;
import it.uniroma2.progisssr.entity.Product;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductController {

    @Autowired
    ProductDao productDao;

    @Transactional
    public @NotNull ProductDto createProduct(@NotNull ProductDto productDto) {

        Product product = this.marshalling(productDto);
        Product newProduct= productDao.save(product);
        return unmarshalling(newProduct);
    }

    private Product marshalling (ProductDto productDto){
        Product product = new Product(productDto.getName(),
                productDto.getVersion(),productDto.getDescription() );
        return product;

    }

    private ProductDto unmarshalling (Product product){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(product, ProductDto.class);
    }

    @Transactional
    public @NotNull ProductDto updateProduct( @NotNull Long id, @NotNull ProductDto productDto) throws EntitaNonTrovataException{

        Product productToUpdate = productDao.getOne(id);
        if (productToUpdate == null)
            throw new EntitaNonTrovataException();
        productDto.setID(id);
        productToUpdate.update(marshalling(productDto));

        Product productUpdated = productDao.save(productToUpdate);
        return unmarshalling(productUpdated);

    }

    public ProductDto findProductById(@NotNull Long id) {
        ProductDto productDto = unmarshalling(productDao.getOne(id));
        return productDto;
    }

    public boolean deleteProduct(@NotNull Long id){
        if(!productDao.existsById(id)){
            return false;
        }
        productDao.deleteById(id);
        return true;
    }

    public List<ProductDto> findAllTickets(){
        List<Product> products = productDao.findAll();
        List<ProductDto> productsDto = new ArrayList<>();
        for (Product product : products) {
            productsDto.add(unmarshalling(product));
        }
        return productsDto;
    }



}
