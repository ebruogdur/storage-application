package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {
    private  final UserService userService;
    private final NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping("/create/note")
    public String viewNote(@ModelAttribute Note note, Model model) {
        return "home";
    }

    @PostMapping("/create/note")
    public String createNote(@ModelAttribute Note note, Model model, Authentication authentication) {
        note.setUserId(userService.getUser(authentication.getName()).getUserId());
        if(note.getNoteId()!=null){
            noteService.updateNote(note);
        }
        else{
            noteService.createNote(note);
        }

        return "redirect:/home#nav-notes";
    }

    @GetMapping("/delete/note/{id}")
    public String deleteNote(@PathVariable("id") int id, Model model) {
        Note note = noteService.getNote(id);
        if(note!=null){
            noteService.deleteNote(note);
        }
        return "redirect:/home#nav-notes";
    }

}
