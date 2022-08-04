package com.example.demo.dtos;

import lombok.Data;

@Data
public class InvoiceProductDto {
    private Long id;
    private int quantity;
    private ProductDtO product;
    private InvoiceDto invoice;
}
