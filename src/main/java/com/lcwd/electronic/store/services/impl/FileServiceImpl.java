package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.exceptions.BadApiRequest;
import com.lcwd.electronic.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        String originalFilename = file.getOriginalFilename();
        logger.info("Uploaded file name={}",originalFilename);
        String fileName= UUID.randomUUID().toString();
        String extension=originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension=fileName+extension;
        String fullPathWithFileName=path+ File.separator+fileNameWithExtension;

        if(extension.equalsIgnoreCase(".png")|| extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase(".jpeg"))
        {
            File folder=new File(path);
            if(!folder.exists())
            {
                //create folder
                folder.mkdirs();
            }
            // upload file
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
        }
        else{
            throw new BadApiRequest("image extension not allowed");
        }

        return fileNameWithExtension;
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String fullPath=path+File.separator+name;
        InputStream inputStream=new FileInputStream(fullPath);
        return inputStream;
    }
}
