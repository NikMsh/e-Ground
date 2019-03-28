package com.bsuir.sdtt.service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface ImageService {
    File convertStringToFile(String image) throws IOException;

    String saveImageToGoogleDrive(java.io.File image) throws GeneralSecurityException, IOException;

    String compressionImage(java.io.File image) throws IOException, GeneralSecurityException;

    void deleteImageFromGoogleDrive(String imageId, String compressImageId) throws GeneralSecurityException, IOException;
}
