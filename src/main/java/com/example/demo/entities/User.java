package com.example.demo.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String logo;


    @OneToMany
    private List<Product> products;
    @ManyToMany
    private List<Invoice> invoices;
}
