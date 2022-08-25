package com.example.demo.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileHandler {
    private static String LOGODIRECTORY = "src/main/webapp/";

    public  static String  uploadFile(MultipartFile file) throws IOException {
        Path fileStorage=null;
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String ext = FilenameUtils.getExtension(filename);
        filename = getSaltString()+"."+ext;

            fileStorage = get(LOGODIRECTORY, filename).toAbsolutePath().normalize();

        copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
        return filename;
    }
    public static Resource downloadFile(String filename)  throws IOException {
        Path filePath = get(LOGODIRECTORY).toAbsolutePath().normalize().resolve(filename);
        if(!Files.exists(filePath)) {
            throw new FileNotFoundException(filename + " was not found on the server");
        }
        return new UrlResource(filePath.toUri());
    }
    public static void deleteFile(String filename){

            File fileToDelete = FileUtils.getFile(LOGODIRECTORY+filename);
            FileUtils.deleteQuietly(fileToDelete);


    }

    public static String getSaltString() {
        String SALTCHARS = "abcdefghijklmnopqrstuvyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 9) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }
}
