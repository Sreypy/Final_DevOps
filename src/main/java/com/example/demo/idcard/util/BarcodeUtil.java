package com.example.demo.idcard.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.nio.file.Path;

public class BarcodeUtil {

    public static String generateBarcode(String text, String fileName) throws Exception {

        String path = "uploads/" + fileName;

        BitMatrix matrix = new MultiFormatWriter()
                .encode(text, BarcodeFormat.CODE_128, 300, 100);

        MatrixToImageWriter.writeToPath(matrix, "PNG", Path.of(path));

        return fileName;
    }
}