package com.example.demo.services;

import com.example.demo.dtos.InvoiceDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public interface IInvoiceService {
    public Document addInvoice( InvoiceDto invoiceDto, MultipartFile logo) throws DocumentException, IOException, URISyntaxException;
}
