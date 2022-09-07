package com.example.demo.services;

import com.example.demo.dtos.ProductDtO;
import com.example.demo.dtos.ProductDtO1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IProductService {

    public boolean addProduct(ProductDtO productDtO);
    public void updateProduct(ProductDtO productDtO);
    public boolean deleteProduct(Long productId);
    public Page<ProductDtO1> getproductsByUser(Long id, Pageable pageable);


}
