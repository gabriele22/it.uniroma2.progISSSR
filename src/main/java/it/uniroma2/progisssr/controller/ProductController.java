package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.ProductDao;
import it.uniroma2.progisssr.dto.TicketDto;
import it.uniroma2.progisssr.entity.Product;
import it.uniroma2.progisssr.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

public class ProductController {

    @Autowired
    ProductDao productDao;

    @Transactional
    public @NotNull Product createProduct(@NotNull Product product) {

        Product newProduct= productDao.save(product);
        return newProduct;
    }


    public Product findProductById(@NotNull Long id) {

        return productDao.getOne(id);
    }

}
