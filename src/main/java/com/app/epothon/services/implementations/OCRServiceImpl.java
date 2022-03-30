package com.app.epothon.services.implementations;

import com.app.epothon.dto.ApiResponse;
import com.app.epothon.dto.BasicTableInfo;
import com.app.epothon.model.ScannerModel;
import com.app.epothon.repository.ScannerRepository;
import com.app.epothon.services.OCRService;
import com.app.epothon.util.ImageUtilService;
import com.app.epothon.util.UtilService;
import lombok.AllArgsConstructor;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OCRServiceImpl implements OCRService {

    private ImageUtilService imageUtilService;
    private ScannerRepository scannerRepository;
    private UtilService utilService;

    @Override
    public ResponseEntity<ApiResponse<ScannerModel>> generateText(String destinationLanguage, MultipartFile image) {

        BasicTableInfo basicTableInfo = utilService.generateBasicTableInfoWithoutToken(destinationLanguage);

        ScannerModel scannerModel = new ScannerModel();
        scannerModel.setId(basicTableInfo.getId());
        scannerModel.setLanguage(destinationLanguage);

        ITesseract instance = new Tesseract();

        try {
            BufferedImage in = ImageIO.read(convert(image));

            BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = newImage.createGraphics();
            g.drawImage(in, 0, 0, null);
            g.dispose();

            instance.setLanguage(destinationLanguage);
            instance.setDatapath("src/main/resources/tessdata");

            String result = instance.doOCR(newImage);

//            upload to cloudinary
            MultipartFile[] multipartFiles = new MultipartFile[1];
            multipartFiles[0] = image;
            List<String> imageLinks = new ArrayList<>();

            try {
                imageLinks = imageUtilService.uploadImage(multipartFiles);
                System.out.println("Done");
            } catch (Exception e) {
                System.out.println("Not Done");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.toString());
            }

//            String filePath = request.getServletContext().getRealPath("/");
//            multipartFile.transferTo(new File(filePath));

            scannerModel.setImageFile(imageLinks.get(0));
            scannerModel.setTextFile(result);

            scannerRepository.save(scannerModel);

            return new ResponseEntity<>(new ApiResponse<>(200, "OCR Successful!",scannerModel),HttpStatus.OK);

        } catch (TesseractException | IOException e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new ApiResponse<>(400, "OCR Failed! Error while reading image.",scannerModel),HttpStatus.BAD_REQUEST);
        }

    }

    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File("src/main/resources/uploads/"+file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
