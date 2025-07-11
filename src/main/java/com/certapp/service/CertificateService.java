package com.certapp.service;

import com.certapp.model.Student;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.time.LocalDate;

@Service
public class CertificateService {

    public void generateAndSend(Student student) {
        try {
            String fileName = student.getName().replaceAll(" ", "_") + "_certificate.pdf";
            Document document = new Document(PageSize.A4.rotate(), 50, 50, 50, 50); 
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            PdfContentByte canvas = writer.getDirectContent();
            Rectangle rect = new Rectangle(36, 36, PageSize.A4.rotate().getWidth() - 36, PageSize.A4.rotate().getHeight() - 36);
            rect.setBorder(Rectangle.BOX);
            rect.setBorderWidth(3);
            rect.setBorderColor(BaseColor.DARK_GRAY);
            canvas.rectangle(rect);

            Font titleFont = FontFactory.getFont("Times-Roman", 32, Font.BOLD, BaseColor.BLACK);
            Font nameFont = FontFactory.getFont("Times-Roman", 24, Font.BOLD, BaseColor.BLACK);
            Font textFont = FontFactory.getFont("Times-Roman", 16, Font.NORMAL, BaseColor.DARK_GRAY);

            Paragraph title = new Paragraph("Certificate of Completion", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            Paragraph line1 = new Paragraph("This is to certify that", textFont);
            line1.setAlignment(Element.ALIGN_CENTER);
            document.add(line1);

            Paragraph name = new Paragraph(student.getName(), nameFont);
            name.setAlignment(Element.ALIGN_CENTER);
            document.add(name);

            Paragraph line2 = new Paragraph("has successfully completed the course", textFont);
            line2.setAlignment(Element.ALIGN_CENTER);
            document.add(line2);

            Paragraph course = new Paragraph(student.getCourse(), nameFont);
            course.setAlignment(Element.ALIGN_CENTER);
            document.add(course);

            document.add(Chunk.NEWLINE);

            Paragraph date = new Paragraph("Date: " + LocalDate.now(), textFont);
            date.setAlignment(Element.ALIGN_CENTER);
            document.add(date);

            document.close();

            EmailService.sendEmailWithAttachment(student.getEmail(), fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
