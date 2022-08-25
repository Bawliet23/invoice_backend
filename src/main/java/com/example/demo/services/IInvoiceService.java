package com.example.demo.services;

import com.example.demo.dtos.InvoiceDto;
import com.itextpdf.text.DocumentException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public interface IInvoiceService {
    public InvoiceDto addInvoice(Long id, InvoiceDto invoiceDto, MultipartFile logo) throws  IOException;
    public FileOutputStream getInvoice(Long id);
}
