package com.example.demo.services;

import com.example.demo.dtos.InvoiceDto;
import com.example.demo.dtos.ProductDtO;
import com.example.demo.dtos.ProductDtO1;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.repositories.IProductRepository;
import com.example.demo.repositories.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;


    public ProductService(IProductRepository productRepository, IUserRepository userRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean addProduct( ProductDtO productDtO) {
        Optional<User> exist = userRepository.findById(productDtO.getUser());
        if (exist.isEmpty())
            return false;
        User user = exist.get();
       Product product = modelMapper.map(productDtO, Product.class);
       product.setUser(user);
        productRepository.save(product);
        return true;

    }

    @Override
    public void updateProduct(ProductDtO productDtO) {
//todo
    }

    @Override
    public boolean deleteProduct(Long productId) {
        Optional<Product> ex = productRepository.findById(productId);
        if (ex.isEmpty())
            return false;
        productRepository.delete(ex.get());
        return true;

    }

    @Override
    public Page<ProductDtO1> getproductsByUser(Long id, Pageable pageable) {
        Optional<User> exist = userRepository.findById(id);
        if(exist.isEmpty())
            new Exception("user not found");
        Page<Product> products = productRepository.findAllByUser(pageable,exist.get());
        return products.map(product -> modelMapper.map(product,ProductDtO1.class));
    }

    @Override
    public List<ProductDtO1> getproductsByUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        List<ProductDtO1> products = new ArrayList<>();
        if (user.isPresent()){
            products = user.get().getProducts().stream().map(product -> modelMapper.map(product,ProductDtO1.class)).collect(Collectors.toList());
        }

        return products;
    }
}
