package com.example.demo.entities;


import javax.validation.constraints.Positive;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class InvoiceProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Positive
    private int quantity;
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    private Invoice invoice;
}
