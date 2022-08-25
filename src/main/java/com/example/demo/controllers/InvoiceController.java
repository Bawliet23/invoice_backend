package com.example.demo.controllers;

import com.example.demo.dtos.InvoiceDto;
import com.example.demo.services.IInvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
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
    private ResponseEntity<?> addInvoice(@RequestParam("logo")MultipartFile logo,@RequestParam("invoice") String invoiceDto,@RequestParam("userid") Long id) throws IOException, DocumentException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        InvoiceDto invoiceDto1 = objectMapper.readValue(invoiceDto,InvoiceDto.class);
        InvoiceDto pdf = invoiceService.addInvoice(id,invoiceDto1,logo);
//        HttpHeaders headers = new HttpHeaders();
//        // Here you have to set the actual filename of your pdf
//        String filename = "invoice.pdf";
//        headers.setContentDispositionFormData(filename, filename);
//        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdf, HttpStatus.OK);
    }
}
