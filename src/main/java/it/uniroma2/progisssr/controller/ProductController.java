package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.ProductDao;
import it.uniroma2.progisssr.entity.Product;
import it.uniroma2.progisssr.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;

public class ProductController {

    @Autowired
    ProductDao productDao;


    public Product findProductById(@NotNull Long id) {

        return productDao.getOne(id);
    }

}
