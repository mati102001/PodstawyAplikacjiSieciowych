package com.example.pas22.repositories;

import com.example.pas22.model.Product;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;
@ApplicationScoped
public class ProductRepository extends Repository<Product> {

    @PostConstruct
    public void init(){
        this.add(new Product("69db1fdf-6b72-4503-be1e-3c2e787b8696", "Pryncypalki", 10, false, false));
        this.add(new Product("69db1fdf-6b72-4503-be1e-3c2e787b8697", "Chleb", 30, false, false));
    }

    @Override
    public boolean update(UUID id, Product product) {
        for (Product productFromList: this.getAll()) {
            if(productFromList.getId().equals(id)) {
                productFromList.setName(product.getName());
                productFromList.setPrice(product.getPrice());
                productFromList.setRented(product.isRented());
                return true;
            }
        }
        return false;
    }
}
