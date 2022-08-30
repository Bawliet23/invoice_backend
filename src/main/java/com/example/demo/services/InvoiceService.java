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
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;


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
    public void getInvoice(HttpServletResponse response, Long id) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
//        FileOutputStream pdf = new FileOutputStream("src/main/webapp/"+invoiceDto.getName()+".pdf");
        PdfWriter.getInstance(document, response.getOutputStream());
//        PdfWriter.getInstance(document, pdf);
//        String l =FileHandler.uploadFile(logo);
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isPresent()){
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLACK);
            Paragraph title = new Paragraph("Invoice", font);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            Image img = Image.getInstance("src/main/webapp/"+invoice.get().getLogo());
            img.scaleAbsoluteHeight(100);
            img.scaleAbsoluteWidth(100);
            document.add(img);

            font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Paragraph name = new Paragraph("Name : ", font);
            name.setAlignment(Paragraph.ALIGN_LEFT);
            name.add(new Phrase(invoice.get().getName(),FontFactory.getFont(FontFactory.COURIER_BOLD, 18, BaseColor.BLACK)));
            document.add(name);

            Paragraph bill = new Paragraph("Bill To : ", font);
            bill.setAlignment(Paragraph.ALIGN_RIGHT);
            bill.add(new Phrase(invoice.get().getBillTo(),FontFactory.getFont(FontFactory.COURIER_BOLD, 18, BaseColor.BLACK)));
            Phrase p = new Phrase(invoice.get().getPo(),FontFactory.getFont(FontFactory.COURIER_BOLD, 18, BaseColor.BLACK));
            document.add(bill);

            Paragraph ship = new Paragraph("Ship To : ", font);
            ship.setAlignment(Paragraph.ALIGN_LEFT);
            ship.add(new Phrase(invoice.get().getShipTo(),FontFactory.getFont(FontFactory.COURIER_BOLD, 18, BaseColor.BLACK)));
            document.add(ship);
            document.add(new Phrase("\n"));




            //table
            PdfPTable table = new PdfPTable(4);
            font = FontFactory.getFont(FontFactory.COURIER, 15, BaseColor.WHITE);
            PdfPCell c1 = new PdfPCell(new Phrase("Product",font));
            c1.setBackgroundColor(BaseColor.BLACK);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Quantity",font));
            c1.setBackgroundColor(BaseColor.BLACK);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Price Unit",font));
            c1.setBackgroundColor(BaseColor.BLACK);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Amount",font));
            c1.setBackgroundColor(BaseColor.BLACK);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.setHeaderRows(1);
            for (InvoiceProduct order : invoice.get().getOrderItems()){
                table.addCell(order.getProduct().getName());
                table.addCell(order.getQuantity()+"");
                table.addCell(order.getProduct().getPrice()+"");
                table.addCell((order.getProduct().getPrice()*order.getQuantity())+"");
            }

            document.add(table);

            document.close();
        }

    }
}
