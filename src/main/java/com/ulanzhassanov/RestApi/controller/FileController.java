package com.ulanzhassanov.RestApi.controller;

import com.ulanzhassanov.RestApi.model.File;
import com.ulanzhassanov.RestApi.repository.hibernate.FileRepositoryImpl;
import com.ulanzhassanov.RestApi.service.FileService;

import java.util.List;

public class FileController {
    private FileService fileService = new FileService(new FileRepositoryImpl());

    public File saveFile(File file) {return fileService.saveFile(file);}
    public File getFileById(int id) {return fileService.getFileById(id);}
    public List<File> getAllFiles() {return fileService.getAllFiles();}
    public File updateFile(File file) {return fileService.updateFile(file);}
    public void deleteFileById(int id) {fileService.deleteFileById(id);}

}
