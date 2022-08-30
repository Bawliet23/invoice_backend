package com.example.demo.dtos;


import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
public class InvoiceDto {
    private Long id;
    private String name;
    private String email;
    private String shipTo;
    private String billTo;
    private String po;
    private Date date;
    private Date dueDate;
    private String logo;
    private double subtotal;
    private double tax;
    private double total;
    private double amountPaid;
    private double balanceDue;
    private String note;
    private String Term;



    private List<InvoiceProductDto> orderItems ;
}
