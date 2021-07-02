package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserCredentialsService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.PostConstruct;

@Controller
public class HomeController {

    private FileService fileService;
    private NoteService noteService;
    private UserCredentialsService userCredentialsService;

    public HomeController(FileService fileService, NoteService noteService, UserCredentialsService userCredentialsService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.userCredentialsService = userCredentialsService;
    }

    @PostConstruct
    public void postConstruct() {
    }

    @GetMapping("/home")
    public String getHomepage(Authentication authentication, @ModelAttribute("noteModal") Note note, @ModelAttribute("credentialModal") CredentialForm credentialForm, Model model) {
        addCommonModelAttributes(model, authentication.getName(), "files");
        return "home";
    }


    public void addCommonModelAttributes(Model model, String userName, String activeTab) {
        model.addAttribute("fileList", fileService.listFileNames(userName));
        model.addAttribute("notesList", noteService.listNotes(userName));
        model.addAttribute("credentialList", userCredentialsService.listCredentials(userName));
        model.addAttribute("activeTab", activeTab);
    }

}
