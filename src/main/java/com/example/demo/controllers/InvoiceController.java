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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/invoice")
@CrossOrigin("*")
public class InvoiceController {

    private final IInvoiceService  invoiceService;

    public InvoiceController(IInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("")
    private ResponseEntity<?> addInvoice(@RequestParam("logo")MultipartFile logo,@RequestParam("invoice") String invoiceDto,@RequestParam("userid") Long id) throws IOException, DocumentException, URISyntaxException, MessagingException, ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        InvoiceDto invoiceDto1 = objectMapper.readValue(invoiceDto,InvoiceDto.class);
        InvoiceDto pdf = invoiceService.addInvoice(id,invoiceDto1,logo);
          return new ResponseEntity<>(pdf, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public void download(HttpServletResponse response,@PathVariable("id") Long id) throws IOException, DocumentException, ParseException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        invoiceService.getInvoice(response,id);
    }

    @GetMapping("/send/{id}")
    public void download(@PathVariable("id") Long id) throws MessagingException, DocumentException, IOException, ParseException {
     invoiceService.sendMail(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable Long id){
        invoiceService.delete(id);
         return new ResponseEntity<>("Invoice Deleted", HttpStatus.OK);
    }
}
