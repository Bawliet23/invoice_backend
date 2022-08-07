package com.example.demo.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String logo;
    private String email;
    private String shipTo;
    private String billTo;
    private String po;
    private Date date;
    private Date dueDate;


    @ManyToOne(fetch = FetchType.LAZY,cascade =CascadeType.PERSIST)
    private User user;
    @OneToMany(
            mappedBy = "invoice",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<InvoiceProduct> orderItems ;
}