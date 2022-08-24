package com.example.demo.controllers;

import com.example.demo.dtos.InvoiceDto;
import com.example.demo.services.IInvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/invoice")
public class InvoiceController {

    private final IInvoiceService  invoiceService;

    public InvoiceController(IInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }
   
    @PostMapping("")
    private ResponseEntity<?> addInvoice(@RequestParam("logo")MultipartFile logo,@RequestParam("invoice") String invoiceDto) throws IOException, DocumentException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        InvoiceDto invoiceDto1 = objectMapper.readValue(invoiceDto,InvoiceDto.class);
        Document pdf = invoiceService.addInvoice(invoiceDto1,logo);
        return ResponseEntity.status(HttpStatus.OK).body(pdf);
    }
}
