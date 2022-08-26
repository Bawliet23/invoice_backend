package com.example.demo.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;


  private List<ProductDtO1> products = new ArrayList<>();
    private List<InvoiceDto> invoices = new ArrayList<>() ;
}
