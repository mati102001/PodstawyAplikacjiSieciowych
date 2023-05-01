package com.example.pas22Front.beans.product;
import com.example.pas22Front.beans.login.Jwt;
import com.example.pas22Front.model.Product;
import com.example.pas22Front.restClients.ProductRestClient;
import com.example.pas22Front.restClients.exceptions.GeneralBadRequestException;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Named("ProductBean")
@SessionScoped
public class ProductBean implements Serializable {
    private ProductRestClient productRestClient;
    private List<Product> allProducts = new ArrayList<Product>();
    private Product updatedProduct;
    private Product newProduct;
    private String message = "";
    @Inject
    private Jwt jwt;

    @PostConstruct
    public void init(){
        productRestClient = new ProductRestClient(jwt.getJwt());
        this.fetchProducts();
    }


        public void fetchProducts(){
            try {
                allProducts = productRestClient.getAllProducts();
            }catch (GeneralBadRequestException e){
                this.message=e.getStatus()+": "+e.getMessage();
            }catch (Exception e){
                this.message=e.getMessage();
            }

        }

        public void deleteProduct(String id){
            productRestClient.deleteProduct(id);
            this.fetchProducts();
        }

        public String goToProductEdit(String id){
            updatedProduct = productRestClient.getProduct(id);
            return "toProductEditPage";
        }
        public String updateProduct(){
            productRestClient.updateProduct(updatedProduct);
            this.fetchProducts();
            return "toProducts";
        }
        public String goToProductCreator(){
            newProduct = new Product(UUID.randomUUID().toString(),"",  0, false, false);
            return "toProductCreatorPage";
        }

        public String addProduct(){
            productRestClient.addProduct(newProduct);
            newProduct = new Product(UUID.randomUUID().toString(),"",  0, false, false);
            return "toProducts";
        }

}