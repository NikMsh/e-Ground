package com.bsuir.sdtt.service.impl;

import com.bsuir.sdtt.service.ImageService;
import com.bsuir.sdtt.util.GoogleProperty;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.bsuir.sdtt.util.GoogleProperty.REDIRECT_URI;

/**
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Slf4j
@Service
public class DefaultImageService implements ImageService {
    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

    private NetHttpTransport HTTP_TRANSPORT;

    private GoogleAuthorizationCodeFlow flow;

    private String code;

    public DefaultImageService() throws GeneralSecurityException, IOException {
        HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        InputStream in = DefaultImageService.class.getResourceAsStream(GoogleProperty.CREDENTIALS_FILE_PATH);

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .build();
    }

    private Drive getDriveService() throws IOException {
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName(GoogleProperty.APPLICATION_NAME)
                .build();
    }

    private Credential getCredentials() throws IOException {
        GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();

        return flow.createAndStoreCredential(response, null);
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
    public String saveImageToGoogleDrive(java.io.File image) throws GeneralSecurityException, IOException {
        File fileMetadata = new File();
        fileMetadata.setName(image.getName());
        FileContent mediaContent = new FileContent("image/jpeg", image);
        File file = getDriveService().files().create(fileMetadata, mediaContent)
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
            getDriveService().files().delete(imageId).execute();
            getDriveService().files().delete(compressImageId).execute();
        }
    }

    @Override
    public void authorizationGoogle(String code) {
        this.code = code;
    }

    @Override
    public String getAuthorizationGoogleCode() {
        GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
        url.setRedirectUri(REDIRECT_URI);
        url.setApprovalPrompt("force");
        url.setAccessType("offline");
        return url.build();
    }
}