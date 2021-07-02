package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Controller
public class FileController {

    private final FileService fileService;
    private final HomeController homeController;

    public FileController(FileService fileService, HomeController homeController) {
        this.fileService = fileService;
        this.homeController = homeController;
    }

    @PostConstruct
    public void postConstruct() {
    }

    @PostMapping("/file-upload")
    public String postFileUpload(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload, @ModelAttribute("noteModal") Note note, @ModelAttribute("credentialModal") CredentialForm credentialForm, Model model) {
        String error = null;

        if (fileUpload.getOriginalFilename() == null || fileUpload.getOriginalFilename().isBlank()) {
            error = "Please select a file";
        }

        if (error == null && !fileService.isFileNameValid(fileUpload.getOriginalFilename(), authentication.getName())) {
            error = "File with this name already exists";
        }

        if (error == null) {
            try {
                fileService.uploadFile(fileUpload, authentication.getName());
            } catch (IOException e) {
                error = "File upload failed" + e.getMessage();
            }

        }
        if (error == null) {
            model.addAttribute("uploadSuccess", true);
        } else {
            model.addAttribute("uploadError", error);
        }

        homeController.addCommonModelAttributes(model, authentication.getName(), "files");
        return "home";
    }

    @GetMapping("/file-delete")
    public String fileDelete(Authentication authentication, @RequestParam("fileId") Integer fileId, @ModelAttribute("noteModal") Note note, @ModelAttribute("credentialModal") CredentialForm credentialForm, Model model) {
        fileService.deleteFile(fileId, authentication.getName());
        model.addAttribute("fileDeleteSuccess", true);
        homeController.addCommonModelAttributes(model, authentication.getName(), "files");

        return "home";
    }

    @GetMapping("/file-download")
    public ResponseEntity<byte[]> getFileDownload(Authentication authentication, @RequestParam("fileId") Integer fileId) throws IOException {

        File file = fileService.downloadFile(fileId, authentication.getName());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getFileData().readAllBytes());
    }


}
