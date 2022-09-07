package com.example.demo.controllers;


import com.example.demo.dtos.InvoiceDto;
import com.example.demo.dtos.ProductDtO1;
import com.example.demo.dtos.RegisterDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.services.IInvoiceService;
import com.example.demo.services.IProductService;
import com.example.demo.services.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class UserController {

    private final IUserService userService;
    private final IInvoiceService invoiceService;
    private final IProductService productService;

    public UserController(IUserService userService, IInvoiceService invoiceService, IProductService productService) {
        this.userService = userService;
        this.invoiceService = invoiceService;
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id){
        UserDto user = userService.getUser(id);
        if (user==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    @GetMapping("/{id}/invoices")
    public ResponseEntity<?> getInvoicesByUser(@PathVariable("id") Long id){
        List<InvoiceDto> invoiceDtos = invoiceService.getInvoicesByUser(id);
        if (invoiceDtos==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("invoices Not Found");
        return ResponseEntity.status(HttpStatus.OK).body(invoiceDtos);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<?> getProductsByUser(@PathVariable("id") Long id, @PageableDefault(size = 5) Pageable pageable){
        Page<ProductDtO1> products = productService.getproductsByUser(id,pageable);
        if (products.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("page Not Found");
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        boolean deleted = userService.deleteUser(id);
        if (!deleted)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User doesn't exist");
        return ResponseEntity.status(HttpStatus.OK).body("User deleted");
    }

    @PostMapping("")
    public ResponseEntity<?> addUser(@RequestBody() RegisterDto userDto) throws IOException {

        UserDto user = userService.addUser(userDto);
        if (user==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
