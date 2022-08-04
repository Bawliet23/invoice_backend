package com.example.demo.services;

import com.example.demo.dtos.ProductDtO;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.repositories.IProductRepository;
import com.example.demo.repositories.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public boolean addProduct(Long userId, ProductDtO productDtO) {
        Optional<User> exist = userRepository.findById(userId);
        if (exist.isEmpty())
            return false;
        User user = exist.get();
        user.getProducts().add(modelMapper.map(productDtO, Product.class));
        userRepository.save(user);
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
    public List<ProductDtO> getproductsByUser(Long id) {
        Optional<User> exist = userRepository.findById(id);
        if(exist.isPresent())
            new Exception("user not found");
        return exist.get().getProducts().stream().map((product -> modelMapper.map(product, ProductDtO.class))).collect(Collectors.toList());
    }
}
