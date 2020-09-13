package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

import java.util.List;

@Service
public class FileService {


    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }


    public List<File> getAllFiles() {
        return fileMapper.getAllFiles();
    }

    public int createFile(File file) {

        return fileMapper.insertFile(new File(null,file.getFileName(),file.getContentType(),file.getFileSize(),file.getUserId(),file.getFileData()));

    }


    public File getFile(int fileId) {
        return fileMapper.getFile(fileId);
    }

    public void deleteFile(File file ) {


        fileMapper.deleteFile(file.getFileId());
    }

}
