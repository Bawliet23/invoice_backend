package com.example.demo.services;

import com.example.demo.dtos.InvoiceDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;


@Service
public class InvoiceService implements IInvoiceService {
    @Override
    public InvoiceDto addInvoice(Long id, InvoiceDto invoiceDto, MultipartFile logo) throws DocumentException, FileNotFoundException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Invoice.pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Invoice", font);

        document.add(chunk);
        document.close();
        return null;
    }
}
