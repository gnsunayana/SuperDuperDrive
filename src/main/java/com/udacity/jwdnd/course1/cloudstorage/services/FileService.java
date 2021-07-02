package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    private final UserService userService;


    public FileService(UserService userService, FileMapper fileMapper) {
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    @PostConstruct
    public void postConstruct() {
    }

    public boolean isFileNameValid(String filePath, String username) {
        String fileName = filePath;
        if (fileName == null || fileName.isBlank()) {
            return false;
        }
        int userId = userService.getUser(username).getUserId();
        return !fileMapper.checkFileExitsByName(fileName, userId);
    }

    public int uploadFile(final MultipartFile fileUpload, final String userName) throws IOException {

        System.out.println("In FileService upload file");
        int userId = userService.getUser(userName).getUserId();
        InputStream fis = null;
        try {
            fis = fileUpload.getInputStream();
            File file = new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(),
                    Long.toString(fileUpload.getSize()), userId, fis);
            int fileId = fileMapper.addFile(file);
            if (fileId < 1) {
                throw new IOException("Failed to insert file data to database");
            }
            return fileId;
        } finally {
            fis.close();
        }
    }

    public File downloadFile(final Integer fileId, String userName) {

        System.out.println("In FileService download file");
        Integer userId = userService.getUser(userName).getUserId();
        File file = fileMapper.getByUser(fileId, userId);
        System.out.println("Download complete");
        return file;
    }

    public void deleteFile(final Integer fileId, final String userName) {

        System.out.println("In FileService delete file");

        int userId = userService.getUser(userName).getUserId();

        fileMapper.deleteByUser(fileId, userId);

    }

    public List<File> listFileNames(String userName) {
        System.out.println("In FileService list file names method");
        int userId = userService.getUser(userName).getUserId();
        List<File> fileList = fileMapper.listFileNames(userId);
        if (fileList.size() > 0) {
            for (File file : fileList) {
                System.out.printf("fileList id: %d name:%s\n", file.getFileId(), file.getFileName());

            }
        }

        return fileList;


    }

}
