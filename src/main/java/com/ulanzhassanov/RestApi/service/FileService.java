package com.ulanzhassanov.RestApi.service;

import com.ulanzhassanov.RestApi.model.File;
import com.ulanzhassanov.RestApi.repository.FileRepository;

import java.util.List;

public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File getFileById(Integer id) {
        return fileRepository.getById(id);
    }

    public List<File> getAllFiles() {
        return fileRepository.getAll();
    }

    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    public File updateFile(File file) {
        return fileRepository.update(file);
    }

    public void deleteFileById(Integer id){
        fileRepository.deleteById(id);
    }
}
