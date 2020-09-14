package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class FileController {
    private final FileService fileService;
    private  final UserService userService;

    @Value("${attachment.directory}")
    private String uploadDirectory;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/file/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Authentication authentication) throws IOException {
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

}
