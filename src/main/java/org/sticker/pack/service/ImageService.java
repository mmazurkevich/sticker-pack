package org.sticker.pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.sticker.pack.model.Image;
import org.sticker.pack.repository.ImageRepository;
import org.sticker.pack.storage.StorageException;
import org.sticker.pack.storage.StorageFileNotFoundException;
import org.sticker.pack.storage.StorageProperties;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.stream.Stream;

/**
 * Created by Mikhail on 14.05.2017.
 */
@Service
public class ImageService {

    private final Path rootLocation;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    public ImageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    public Image saveImage(MultipartFile stickerImage) {
        try {
            if (stickerImage.isEmpty()) {
                throw new StorageException("Failed to store empty file " + stickerImage.getOriginalFilename());
            }
            String[] splitedFileName = stickerImage.getOriginalFilename().split("\\.");
            String fileExtension = splitedFileName[splitedFileName.length - 1];
            String fileHash = checksum(stickerImage.getInputStream()).toLowerCase();
            Files.copy(stickerImage.getInputStream(), this.rootLocation.resolve(fileHash + "." + fileExtension));
            Image image = new Image();
            image.setOriginName(stickerImage.getOriginalFilename());
            image.setHashName(fileHash + "." + fileExtension);
            image.setUrl(fileHash + "." + fileExtension);
            return imageRepository.save(image);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + stickerImage.getOriginalFilename(), e);
        }
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        try {
            if (!Files.exists(rootLocation))
                Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    public Path loadImage(String imageName) {
        return rootLocation.resolve(imageName);
    }

    public Resource loadImageAsResource(String filename) {
        try {
            Path file = loadImage(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    private String checksum(InputStream inputStream) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            byte[] block = new byte[4096];
            int length;
            while ((length = inputStream.read(block)) > 0) {
                digest.update(block, 0, length);
            }
            return DatatypeConverter.printHexBinary(digest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
