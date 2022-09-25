package com.example.demo.services;

import com.example.demo.dtos.InvoiceDto;
import com.itextpdf.text.DocumentException;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

public interface IInvoiceService {
    InvoiceDto addInvoice(Long id, InvoiceDto invoiceDto, MultipartFile logo) throws IOException, MessagingException, DocumentException, ParseException;
    void getInvoice(HttpServletResponse response, Long id) throws DocumentException, IOException, ParseException;
    void delete(Long id);
    List<InvoiceDto> getInvoicesByUser(Long id);
    Page<InvoiceDto> getInvoicesByUser(Long id, Pageable pageable);

    void sendMail(Long id) throws IOException, DocumentException, ParseException, MessagingException;
}
