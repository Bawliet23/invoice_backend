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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
    public void getInvoice(HttpServletResponse response, Long id) throws DocumentException, IOException, ParseException {
        Document document = new Document(PageSize.A4);
//        FileOutputStream pdf = new FileOutputStream("src/main/webapp/"+invoiceDto.getName()+".pdf");
        PdfWriter.getInstance(document, response.getOutputStream());
        DecimalFormat df = new DecimalFormat("0.00");
//        PdfWriter.getInstance(document, pdf);
//        String l =FileHandler.uploadFile(logo);
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isPresent()){
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 20, BaseColor.BLACK);
            Paragraph title = new Paragraph("Invoice", font);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            title.add(new Phrase(" #"+invoice.get().getId(),FontFactory.getFont(FontFactory.COURIER_BOLD, 17, BaseColor.GRAY)));
            document.add(title);
            Image img = Image.getInstance("src/main/webapp/"+invoice.get().getLogo());
//            img.scaleAbsoluteWidth(100);
            img.scaleToFit(100f, 100f);;
            document.add(img);
            document.add(new Phrase("\n\n"));

            PdfPTable header = new PdfPTable(2);
            header.setWidthPercentage(100);
            PdfPCell seller =  new PdfPCell();
            seller.setBorder(PdfPCell.NO_BORDER);





            font = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.GRAY);
            Paragraph name = new Paragraph("Name : ", font);
            name.setAlignment(Paragraph.ALIGN_LEFT);
            name.add(new Phrase(invoice.get().getName(),FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)));
            seller.addElement(name);

            Paragraph bill = new Paragraph("Bill To : ", font);
            bill.setAlignment(Paragraph.ALIGN_LEFT);
            bill.add(new Phrase(invoice.get().getBillTo(),FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)));
            Phrase p = new Phrase(invoice.get().getPo(),FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK));
            seller.addElement(bill);

            Paragraph ship = new Paragraph("Ship To : ", font);
            ship.setAlignment(Paragraph.ALIGN_LEFT);
            ship.add(new Phrase(invoice.get().getShipTo(),FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)));
            seller.addElement(ship);


            header.addCell(seller);



            PdfPCell buyer =  new PdfPCell();
            buyer.setBorder(PdfPCell.NO_BORDER);


            Paragraph date = new Paragraph("Date : ", font);
            date.setAlignment(Paragraph.ALIGN_RIGHT);
            date.add(new Phrase(convertDate(invoice.get().getDate(),"MMM dd, yyyy"),FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)));
            buyer.addElement(date);

            Paragraph dateDue = new Paragraph("Due Date : ", font);
            dateDue.setAlignment(Paragraph.ALIGN_RIGHT);
            dateDue.add(new Phrase(convertDate(invoice.get().getDueDate(),"MMM dd, yyyy"),FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)));
            buyer.addElement(dateDue);

            Paragraph pt = new Paragraph("Payment Terms : ", font);
            pt.setAlignment(Paragraph.ALIGN_RIGHT);
            pt.add(new Phrase(invoice.get().getPaymentTerm(),FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)));
            buyer.addElement(pt);

            Paragraph po = new Paragraph("Po Number : ", font);
            po.setAlignment(Paragraph.ALIGN_RIGHT);
            po.add(new Phrase(invoice.get().getPo(),FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)));
            buyer.addElement(po);

            Paragraph balance = new Paragraph("Balance Due : ", font);
            balance.setAlignment(Paragraph.ALIGN_RIGHT);
            balance.add(new Phrase(df.format(invoice.get().getBalanceDue())+invoice.get().getCurrency(),FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)));
            buyer.addElement(balance);


            header.addCell(buyer);

            document.add(header);
            document.add(new Phrase("\n\n\n"));


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
                table.addCell(order.getProduct().getPrice()+invoice.get().getCurrency());
                table.addCell((order.getProduct().getPrice()*order.getQuantity())+invoice.get().getCurrency());
            }

            document.add(table);

            document.add(new Phrase("\n\n\n"));
            font = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.GRAY);
            PdfPTable mid = new PdfPTable(2);
            mid.setWidthPercentage(100);
            PdfPCell t =  new PdfPCell();
            t.setBorder(PdfPCell.NO_BORDER);
            mid.addCell(t);
            PdfPCell ta =  new PdfPCell();
            ta.setBorder(PdfPCell.NO_BORDER);

            Paragraph Subtotal = new Paragraph("Subtotal : ", font);
            Subtotal.setAlignment(Paragraph.ALIGN_RIGHT);
            Subtotal.add(new Phrase(invoice.get().getSubtotal()+invoice.get().getCurrency(),FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)));
            ta.addElement(Subtotal);

            Paragraph tax = new Paragraph("Tax("+invoice.get().getTax()+"%) :", font);
            tax.setAlignment(Paragraph.ALIGN_RIGHT);
            double d =((invoice.get().getSubtotal() * invoice.get().getTax())/100);
            tax.add(new Phrase(df.format(d)+invoice.get().getCurrency(),FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)));
            ta.addElement(tax);

            Paragraph total = new Paragraph("Total : ", font);
            total.setAlignment(Paragraph.ALIGN_RIGHT);
            total.add(new Phrase(df.format(invoice.get().getTotal())+invoice.get().getCurrency(),FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)));
            ta.addElement(total);

            Paragraph amountPaid = new Paragraph("Amount Paid : ", font);
            amountPaid.setAlignment(Paragraph.ALIGN_RIGHT);
            amountPaid.add(new Phrase(invoice.get().getAmountPaid()+invoice.get().getCurrency(),FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)));
            ta.addElement(amountPaid);
            mid.addCell(ta);
            document.add(mid);

            document.add(new Phrase("\n\n"));
            Paragraph note= new Paragraph("Notes :\n", font);
            note.add(new Phrase(invoice.get().getNote(),FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)));
            document.add(note);
            document.add(new Phrase("\n"));
            Paragraph term= new Paragraph("Terms :\n", font);
            term.add(new Phrase(invoice.get().getTerm(),FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)));
            document.add(term);
            document.close();
        }

    }

    @Override
    public void delete(Long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        invoice.ifPresent(invoiceRepository::delete);
    }

    @Override
    public List<InvoiceDto> getInvoicesByUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        List<InvoiceDto> invoices = new ArrayList<>();
        if (user.isPresent()){
           invoices = user.get().getInvoices().stream().map(invoice -> modelMapper.map(invoice,InvoiceDto.class)).collect(Collectors.toList());
        }

        return invoices;
    }

    public String convertDate(Date d, String newFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(newFormat);
        return sdf.format(d);
    }
}
