package com.example.demo.services;

import com.example.demo.dtos.ProductDtO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IProductService {

    public boolean addProduct(Long userId, ProductDtO productDtO);
    public void updateProduct(ProductDtO productDtO);
    public boolean deleteProduct(Long productId);
    public List<ProductDtO> getproductsByUser(Long id);


}
