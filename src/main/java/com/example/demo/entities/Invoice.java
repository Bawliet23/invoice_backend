package com.example.demo.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
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
    private String note;
    private String Term;
    private String shipTo;
    private String billTo;
    private String po;
    private Date date;
    private Date dueDate;
    private String currency;
    @Transient
    private double subtotal;
    private double tax;
    @Transient
    private double total;
    private double amountPaid;
    @Transient
    private double balanceDue;

    @ManyToOne(fetch = FetchType.LAZY,cascade =CascadeType.PERSIST)
    private User user;
    @OneToMany(
            mappedBy = "invoice",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<InvoiceProduct> orderItems = new ArrayList<>();

    public double getSubtotal() {
        subtotal=0;
         for (InvoiceProduct orderItem : orderItems){
            subtotal+=(orderItem.getProduct().getPrice() * orderItem.getQuantity());
        }
        return subtotal;
    }

    public double getTotal() {
        double t = (this.getSubtotal() * this.getTax())/100;
        return this.getSubtotal()+t;
    }
    public double getBalanceDue() {
        return this.getTotal()-this.amountPaid;
    }
}
