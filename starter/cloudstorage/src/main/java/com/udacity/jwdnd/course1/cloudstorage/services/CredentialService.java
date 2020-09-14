package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private  final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int createCredential(Credential credential) {

        SecureRandom randomForKey = new SecureRandom();
        byte[] key = new byte[16];
        randomForKey.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);

        return credentialMapper.
                insertCredential(new Credential(null,credential.getUrl(),credential.getUsername(),
                        encodedKey,encryptedPassword,credential.getUserId()));
    }


    public Credential getCredential(int credentialId) {
        return credentialMapper.getCredential(credentialId);
    }

    public List<Credential> getAllCredential() {
        return credentialMapper.getAllCredentials();
    }


    public void updateCredential(Credential credential) {


        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());

        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, credential.getKey());
        credential.setPassword(encryptedPassword);
        credentialMapper.updateCredential(credential);
    }


    public void deleteCredential(Credential credential ) {
        credentialMapper.deleteCredential(credential.getCredentialId());
    }

}
