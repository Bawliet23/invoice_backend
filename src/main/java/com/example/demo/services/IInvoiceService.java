package com.example.demo.services;

import com.example.demo.dtos.InvoiceDto;
import com.itextpdf.text.DocumentException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

public interface IInvoiceService {
    InvoiceDto addInvoice(Long id, InvoiceDto invoiceDto, MultipartFile logo) throws  IOException;
    void getInvoice(HttpServletResponse response, Long id) throws DocumentException, IOException, ParseException;
    void delete(Long id);
    List<InvoiceDto> getInvoicesByUser(Long id);
}
