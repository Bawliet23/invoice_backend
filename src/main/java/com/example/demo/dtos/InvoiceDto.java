package com.example.demo.dtos;


import lombok.Data;

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


    private List<InvoiceProductDto> orderItems ;
}
