package com.example.demo.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String logo;


    private List<ProductDtO> products;
    private List<InvoiceDto> invoices;
}
