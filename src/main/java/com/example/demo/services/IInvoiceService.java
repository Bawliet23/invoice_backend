package com.example.demo.services;

import com.example.demo.dtos.InvoiceDto;
import com.itextpdf.text.DocumentException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface IInvoiceService {
    public InvoiceDto addInvoice(Long id, InvoiceDto invoiceDto, MultipartFile logo) throws DocumentException, FileNotFoundException;
}
