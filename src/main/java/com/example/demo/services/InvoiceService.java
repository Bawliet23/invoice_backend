package com.example.demo.services;

import com.example.demo.dtos.InvoiceDto;
import com.example.demo.entities.Invoice;
import com.example.demo.entities.InvoiceProduct;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.repositories.IInvoiceRepository;
import com.example.demo.repositories.IProductRepository;
import com.example.demo.repositories.IUserRepository;
import com.example.demo.utils.FileHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;


@Service
public class InvoiceService implements IInvoiceService {
    private final IInvoiceRepository invoiceRepository;
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final IProductRepository productRepository;

    public InvoiceService(IInvoiceRepository invoiceRepository, IUserRepository userRepository, ModelMapper modelMapper, IProductRepository productRepository) {
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }


    @Override
    public InvoiceDto addInvoice(Long id, InvoiceDto invoiceDto, MultipartFile logo) throws  IOException {
        String l = FileHandler.uploadFile(logo);
        invoiceDto.setLogo(l);
        User user = userRepository.findById(id).get();
        Invoice invoice = modelMapper.map(invoiceDto,Invoice.class);
        for (InvoiceProduct inpr :invoice.getOrderItems()) {
            inpr.setInvoice(invoice);
            Product pr = productRepository.findById(inpr.getProduct().getId()).get();
            inpr.setProduct(pr);


        }
        invoice.setUser(user);
        Invoice in = invoiceRepository.save(invoice);

        return modelMapper.map(in,InvoiceDto.class);
    }

    @Override
    public FileOutputStream getInvoice(Long id) {
//        Document document = new Document();
//        FileOutputStream pdf = new FileOutputStream("src/main/webapp/"+invoiceDto.getName()+".pdf");
//        PdfWriter.getInstance(document, pdf);
//        String l =FileHandler.uploadFile(logo);
//        document.open();
//        Font font = FontFactory.getFont(FontFactory.COURIER, 18, BaseColor.BLACK);
//        Paragraph title = new Paragraph("Invoice", font);
//        title.setAlignment(Paragraph.ALIGN_CENTER);
//
//        document.add(title);
//        Image img = Image.getInstance("src/main/webapp/"+l);
//        img.scaleAbsoluteHeight(20);
//        img.scaleAbsoluteWidth(20);
//        document.add(img);
//
//
//        document.close();
        return null;
    }
}
