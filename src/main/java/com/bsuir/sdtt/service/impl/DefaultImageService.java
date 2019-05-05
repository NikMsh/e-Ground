package com.bsuir.sdtt.service.impl;

import com.bsuir.sdtt.service.ImageService;
import com.google.api.client.http.FileContent;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Slf4j
@Service
public class DefaultImageService implements ImageService {
    private Drive googleDrive;

    public DefaultImageService(Drive googleDrive) {
        this.googleDrive = googleDrive;
    }

    @Override
    public java.io.File convertStringToFile(String image) throws IOException {
        byte[] imageByte = Base64.decodeBase64(image);
        java.io.File convertedFile = new java.io.File(Objects.requireNonNull("sample.jpg"));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(imageByte);
        fos.close();
        return convertedFile;
    }

    @Override
    public String saveImageToGoogleDrive(java.io.File image) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(image.getName());
        FileContent mediaContent = new FileContent("image/jpeg", image);
        File file = googleDrive.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        log.info("Save image to drive. File ID: {}", file.getId());

        return file.getId();
    }

    @Override
    public String compressionImage(java.io.File image) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(image);

        java.io.File compressedImageFile = new java.io.File("compress_" + image.getName());
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);
        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.1f);
        writer.write(null, new IIOImage(bufferedImage, null, null), param);

        os.close();
        ios.close();
        writer.dispose();
        return compressedImageFile.getAbsolutePath();
    }

    @Override
    public void deleteImageFromGoogleDrive(String imageId, String compressImageId) throws GeneralSecurityException, IOException {

        if (imageId != null && compressImageId != null && !imageId.isEmpty() && !compressImageId.isEmpty()) {
            googleDrive.files().delete(imageId).execute();
            googleDrive.files().delete(compressImageId).execute();
        }
    }
}