package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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
public class NotesController {

    private final NoteService noteService;
    private final HomeController homeController;

    public NotesController(NoteService noteService, HomeController homeController) {
        this.noteService = noteService;
        this.homeController = homeController;
    }

    @PostConstruct
    public void postConstruct() {
    }

    @PostMapping("/note-save")
    public String saveNote(Authentication authentication, @ModelAttribute("noteModal") Note note, @ModelAttribute("credentialModal") CredentialForm credentialForm, Model model) {

        String error = null;
        Boolean updateOrAdd = null;

        if (error == null && !noteService.isNoteValid(note, authentication.getName())) {
            error = "Another note with the same title exists already";
        }

        if (error == null) {
            try {
                if(note.getNoteId() != null){
                    updateOrAdd = true;
                }
                else{
                    updateOrAdd = false;
                }
                noteService.saveNote(note, authentication.getName());
            } catch (IOException e) {
                error = "There was an error while saving the note";
            }
        }

        if (error == null) {
            if(updateOrAdd){
                model.addAttribute("noteUpdatedSuccess", true);
            }
            else{
                model.addAttribute("noteSavedSuccess", true);
            }
           
        } else {
            model.addAttribute("noteError", error);
        }

        homeController.addCommonModelAttributes(model, authentication.getName(), "notes");

        return "home";


    }


    @GetMapping("/note-delete")
    public String deleteNote(Authentication authentication, @RequestParam("noteId") Integer noteId, @ModelAttribute("noteModal") Note note, @ModelAttribute("credentialModal") CredentialForm credentialForm, Model model) {
        noteService.deleteNote(noteId, authentication.getName());
        model.addAttribute("noteDeletedSuccess", true);
        homeController.addCommonModelAttributes(model, authentication.getName(), "notes");
        return "home";
    }

}
