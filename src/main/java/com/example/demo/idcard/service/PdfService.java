package com.example.demo.idcard.service;

import com.example.demo.idcard.model.Profile;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import com.example.demo.idcard.util.QRCodeUtil;
import com.example.demo.idcard.util.BarcodeUtil;

@Service
public class PdfService {

    public ByteArrayOutputStream generatePdf(Profile profile) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Title
            document.add(new Paragraph("ID CARD",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));

            document.add(new Paragraph(" ")); // space

            document.add(new Paragraph("Name: " + profile.getFullName()));
            document.add(new Paragraph("Type: " + profile.getType()));
            document.add(new Paragraph("Department: " + profile.getDepartment()));
            document.add(new Paragraph("Email: " + profile.getEmail()));
            document.add(new Paragraph("Reg No: " + profile.getRegistrationNumber()));

            document.add(new Paragraph(" ")); // space

            // Generate QR
            String qrFile = "qr_" + profile.getId() + ".png";
            QRCodeUtil.generateQR(profile.getRegistrationNumber(), qrFile);

            Image qr = Image.getInstance("uploads/" + qrFile);
            qr.scaleToFit(100, 100);
            document.add(qr);

            // Generate Barcode
            String barcodeFile = "bar_" + profile.getId() + ".png";
            BarcodeUtil.generateBarcode(profile.getRegistrationNumber(), barcodeFile);

            Image bar = Image.getInstance("uploads/" + barcodeFile);
            bar.scaleToFit(200, 80);
            document.add(bar);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out;
    }
}
