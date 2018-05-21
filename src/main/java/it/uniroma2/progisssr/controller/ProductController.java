package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.ProductDao;
import it.uniroma2.progisssr.dto.ProductDto;
import it.uniroma2.progisssr.entity.Target;
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

        Target target = this.marshalling(productDto);
        Target newTarget = productDao.save(target);
        return unmarshalling(newTarget);
    }

    private Target marshalling (ProductDto productDto){
        Target target = new Target(productDto.getName(),
                productDto.getVersion(),productDto.getDescription() );
        return target;

    }

    private ProductDto unmarshalling (Target target){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(target, ProductDto.class);
    }

    @Transactional
    public @NotNull ProductDto updateProduct( @NotNull Long id, @NotNull ProductDto productDto) throws EntitaNonTrovataException{

        Target targetToUpdate = productDao.getOne(id);
        if (targetToUpdate == null)
            throw new EntitaNonTrovataException();
        productDto.setID(id);
        targetToUpdate.update(marshalling(productDto));

        Target targetUpdated = productDao.save(targetToUpdate);
        return unmarshalling(targetUpdated);

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
        List<Target> targets = productDao.findAll();
        List<ProductDto> productsDto = new ArrayList<>();
        for (Target target : targets) {
            productsDto.add(unmarshalling(target));
        }
        return productsDto;
    }



}
