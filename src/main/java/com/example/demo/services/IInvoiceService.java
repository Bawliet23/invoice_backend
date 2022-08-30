package com.example.demo.services;

import com.example.demo.dtos.InvoiceDto;
import com.itextpdf.text.DocumentException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public interface IInvoiceService {
    InvoiceDto addInvoice(Long id, InvoiceDto invoiceDto, MultipartFile logo) throws  IOException;
    void getInvoice(HttpServletResponse response, Long id) throws DocumentException, IOException;
}
