package com.example.pas22Front.model;

import com.example.pas22Front.dao.ModelDao;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Rent implements ModelDao, Serializable {
    final private UUID id;
    final private User user;
    private Product product;
    final private Date startDate;
    private Date endDate;

    public Rent(UUID id, User user, Product product, Date startDate, Date endDate) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.startDate = startDate;
        this.endDate = endDate;
    }
//
//    public float getRentPrice() {
//        if (!this.rentEnded()) {
//            return 0;
//        }
//
//        long diffInMillies = Math.abs(this.startDate.getTime() - this.endDate.getTime());
//        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//        return diff * product.getPrice();
//    }
//
//
//    public boolean rentEnded() {
//        return this.endDate != null;
//    }id

//    public void endRent() {
//        if (this.rentEnded()) {
//            this.endDate = new Date();
//        }
//    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public User getCustomer() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setProduct(Product p ){
        this.product = p;
    }
}
