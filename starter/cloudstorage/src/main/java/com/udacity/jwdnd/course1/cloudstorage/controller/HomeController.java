package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class HomeController {

    private final CredentialService credentialService;
    private final FileService fileService;
    private final AuthenticationService authenticationService;
    private  final EncryptionService encryptionService;
    private  final UserService userService;

    @Value("${attachment.directory}")
    private String uploadDirectory;

    private final NoteService noteService;
    public HomeController(CredentialService credentialService, FileService fileService, AuthenticationService authenticationService, EncryptionService encryptionService, UserService userService, NoteService noteService) {
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.authenticationService = authenticationService;
        this.encryptionService = encryptionService;
        this.userService = userService;
        this.noteService = noteService;
    }


    @GetMapping("/home")
    public String viewHome( Model model) {

        List<Credential> allCredential = credentialService.getAllCredential();
        model.addAttribute("credentials",allCredential);

        List<Note> allNotes = noteService.getAllNotes();
        model.addAttribute("notes", allNotes);

        List<com.udacity.jwdnd.course1.cloudstorage.model.File> allFiles = fileService.getAllFiles();
        model.addAttribute("allFiles",allFiles);
        return "home";
    }


    @GetMapping("/create/credential")
    public String viewCrediental(@ModelAttribute Credential credential, Model model) {

        return "home";
    }
    @PostMapping("/create/credential")
    public String createCrediental(@ModelAttribute Credential credential, Model model, Authentication authentication) {

        credential.setUserId(userService.getUser(authentication.getName()).getUserId());

        if(credential.getCredentialId()!=null){
            credentialService.updateCredential(credential);
        }
        else{
           credentialService.createCredential(credential);

        }

        return "redirect:/home#nav-credentials";
    }

    @GetMapping("/delete/credential/{id}")
    public String deleteCredential(@PathVariable("id") int id, Model model) {

        Credential credential = credentialService.getCredential(id);
        if(credential!=null){
            credentialService.deleteCredential(credential);
        }
        return "redirect:/home#nav-credentials";
    }

    @GetMapping("/create/note")
    public String viewNote(@ModelAttribute Note note, Model model) {
        return "home";
    }

    @PostMapping("/create/note")
    public String createNote(@ModelAttribute Note note, Model model,Authentication authentication) {

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


    @PostMapping("/file/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file,Authentication authentication) throws IOException {


        if (file.isEmpty()) {

            return "redirect:/home";
        }

        new File(uploadDirectory).mkdir();

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            Path path = Paths.get(uploadDirectory, fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

        com.udacity.jwdnd.course1.cloudstorage.model.File newFile =new com.udacity.jwdnd.course1.cloudstorage.model.File();

        newFile.setUserId(userService.getUser(authentication.getName()).getUserId());
        newFile.setContentType(file.getContentType());
        newFile.setFileData(file.getBytes());
        newFile.setFileName(fileName);
        newFile.setFileSize(Integer.toString((int) file.getSize()));
        fileService.createFile(newFile);
        return "redirect:/home#nav-files";
    }

    @GetMapping("/delete/file/{id}")
    public String deleteFile(@PathVariable("id") int id, Model model) throws IOException {

        com.udacity.jwdnd.course1.cloudstorage.model.File file = fileService.getFile(id);
        if(file!=null){
            fileService.deleteFile(file);
        }

        File deletedFile = new File(uploadDirectory + "/"+ file.getFileName());

        Files.deleteIfExists(deletedFile.toPath());

        return "redirect:/home#nav-files";
    }

    @GetMapping("/download/file/{id}")
    public ResponseEntity<FileSystemResource> downloadFile(@PathVariable("id") int id, Model model) throws IOException {

        HttpHeaders respHeaders = new HttpHeaders();
        com.udacity.jwdnd.course1.cloudstorage.model.File file = fileService.getFile(id);
        File downloadedFile = new File(uploadDirectory + "/"+ file.getFileName());

        if(file!=null){
            long fileLength = downloadedFile.length();

            respHeaders.setContentLength(fileLength);
            respHeaders.setContentDispositionFormData(file.getFileName(), file.getFileName());
            return new ResponseEntity<FileSystemResource>(new FileSystemResource(downloadedFile), respHeaders,
                    HttpStatus.OK);
        }
        else{
            return new ResponseEntity<FileSystemResource>(new FileSystemResource(downloadedFile), respHeaders,
                    HttpStatus.NOT_FOUND);
        }


    }


    @PostMapping("/getPassword")
    public ResponseEntity<?> getSearchResultViaAjax(
            @Valid @RequestBody int credentialId) {

        String result = null;


        Credential credential = credentialService.getCredential(credentialId);

        if(credential!=null){
            result=encryptionService.decryptValue(credential.getPassword(),credential.getKey());
        }

        return ResponseEntity.ok(result);

    }


}
