package it.uniroma2.progisssr.dao;

import it.uniroma2.progisssr.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product,Long> {
}
