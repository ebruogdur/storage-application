package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public class AjaxController {
    private  final CredentialService credentialService;
    private  final EncryptionService encryptionService;

    public AjaxController(CredentialService credentialService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/getPassword")
    public ResponseEntity<?> getCredentialPassword(
            @Valid @RequestBody int credentialId) {

        String result = null;
        Credential credential = credentialService.getCredential(credentialId);

        if(credential!=null){
            result=encryptionService.decryptValue(credential.getPassword(),credential.getKey());
        }

        return ResponseEntity.ok(result);

    }
}
