package com.example.demo.controllers;

import com.example.demo.dtos.ProductDtO;
import com.example.demo.dtos.UserDto;
import com.example.demo.services.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin("*")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ResponseEntity<?> addProduct(@RequestBody() ProductDtO productDtO) {
        boolean added = productService.addProduct(productDtO);
        if (!added)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        return ResponseEntity.status(HttpStatus.OK).body("Product was added");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product was deleted");
    }

}
