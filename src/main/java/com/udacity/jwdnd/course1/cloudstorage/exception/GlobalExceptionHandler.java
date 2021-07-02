package com.udacity.jwdnd.course1.cloudstorage.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleError(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes, Model model) {
        //redirectAttributes.addFlashAttribute("message",e.getCause().getMessage());

        String error = "File Size limit exceeded";
        redirectAttributes.addAttribute("uploadError", error);
        //model.addAttribute("uploadError",error);
        return "redirect:/home";
    }
}


