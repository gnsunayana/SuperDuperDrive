package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.UserCredentials;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserCredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Controller
public class UserCredentialsController {

    private final UserCredentialsService userCredentialsService;
    private final HomeController homeController;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public UserCredentialsController(UserCredentialsService userCredentialsService, HomeController homeController,UserService userService,EncryptionService encryptionService) {
        this.userCredentialsService = userCredentialsService;
        this.homeController = homeController;
        this.userService = userService;
        this.encryptionService= encryptionService;
    }

    @PostConstruct
    public void postConstruct() {
    }

    @PostMapping("/credential-save")
    public String userCredentialsSave(Authentication authentication, @ModelAttribute("noteModal") Note note, @ModelAttribute("credentialModal") CredentialForm credentialForm, Model model) {
        String error = null;
        Boolean updateOrAdd = null;
        String userName= authentication.getName();
        int userId = userService.getUser(userName).getUserId();
        String key = encryptionService.generateNewKey();
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), key);
        UserCredentials userCredentials = new UserCredentials(credentialForm.getCredentialId(), credentialForm.getUrl(), credentialForm.getUserName(), key, encryptedPassword, userId);
        if (error == null && !userCredentialsService.isCredentialValid(credentialForm, authentication.getName())) {

            error = "Another credential with the same url and username already exists";
        }

        if (error == null) {
            try {
                if(userCredentials.getCredentialId() != null){
                    updateOrAdd = true;
                }
                else{
                    updateOrAdd = false;
                }
                userCredentialsService.saveUserCredentials(credentialForm, authentication.getName());
            } catch (IOException e) {
                error = "Error while saving the UserCredentials" + e.getMessage();
            }
        }

        if (error == null) {
            if(updateOrAdd){
                model.addAttribute("credentialUpdatedSuccess",true);
            }
            else{
                model.addAttribute("credentialSavedSuccess", true);
            }

        } else {
            model.addAttribute("credentialError", error);
        }

        homeController.addCommonModelAttributes(model, authentication.getName(), "credentials");
        return "home";
    }

    @GetMapping("/credential-delete")
    public String deleteUserCredential(Authentication authentication, @RequestParam("credentialId") Integer credentialId, @ModelAttribute("noteModal") Note note, @ModelAttribute("credentialModal") CredentialForm credentialForm, Model model) {

        System.out.println("In UserCredentialsController Delete method");

        userCredentialsService.deleteUserCredentials(credentialId, authentication.getName());
        model.addAttribute("credentialDeletedSuccess", true);
        homeController.addCommonModelAttributes(model, authentication.getName(), "credentials");

        return "home";
    }
}
