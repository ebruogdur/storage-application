package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CredentialController {
    private final CredentialService credentialService;
    private  final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
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


}
