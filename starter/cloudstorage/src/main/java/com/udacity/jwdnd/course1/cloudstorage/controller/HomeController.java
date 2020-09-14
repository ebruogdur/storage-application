package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class HomeController {

    private final CredentialService credentialService;
    private final FileService fileService;
    private final NoteService noteService;

    public HomeController(CredentialService credentialService, FileService fileService,  NoteService noteService) {
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.noteService = noteService;
    }


    @GetMapping("/home")
    public String viewHome( Model model) {

        List<Credential> allCredential = credentialService.getAllCredential();
        model.addAttribute("credentials",allCredential);

        List<Note> allNotes = noteService.getAllNotes();
        model.addAttribute("notes", allNotes);

        List<File> allFiles = fileService.getAllFiles();
        model.addAttribute("allFiles",allFiles);
        return "home";
    }
}
