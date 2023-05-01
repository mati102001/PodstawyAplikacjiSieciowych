package com.example.pas22.managers;

import com.example.pas22.model.Product;
import com.example.pas22.repositories.ProductRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Stateless
public class ProductManager {

    @Inject
    private ProductRepository pr;



    public Product add(String name, float price) {
        Product product = new Product(UUID.randomUUID().toString() ,name, price, false, false);
        pr.add(product);
        return product;
    }

    public synchronized List<Product> getAll() {
        return pr.getAll();
    }

    public synchronized List<Product> getAllVisible(){
        List<Product> all = new ArrayList<Product>(pr.getAll());
        all.removeIf(Product::isArchive);
        return all;
    }

    public Product getProduct(UUID id) {
        return pr.get(id);
    }

    public void updateName(UUID id, String name) {
        Product p  = pr.get(id);
        p.setName(name);
        this.update(id, p);
    }

    public void updatePrice(UUID id, float price) {
        Product p  = pr.get(id);
        p.setPrice(price);
        this.update(id, p);
    }
    public void update(UUID id, Product p) {
        pr.update(id, p);
    }

    public boolean delete(UUID id) {
        return pr.delete(id);
    }


}
