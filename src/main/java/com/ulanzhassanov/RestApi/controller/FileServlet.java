package com.ulanzhassanov.RestApi.controller;

import com.ulanzhassanov.RestApi.model.File;
import com.ulanzhassanov.RestApi.repository.hibernate.FileRepositoryImpl;
import com.ulanzhassanov.RestApi.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = {"/files", "/files/*"})
public class FileServlet extends HttpServlet {
    private FileService fileService = new FileService(new FileRepositoryImpl());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String filePath = req.getParameter("filePath");

        File file = new File();
        file.setName(name);
        file.setFilePath(filePath);
        File savedFile = fileService.saveFile(file);

        resp.setContentType("application/json");
        resp.getWriter().write(savedFile.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");

        if (pathInfo == null || pathInfo.equals("/")) {
            List<File> files = fileService.getAllFiles();
            resp.getWriter().write(files.toString());
        } else {
            try {
                Integer id = Integer.parseInt(pathInfo.substring(1));
                File file = fileService.getFileById(id);
                if (file != null){
                    resp.getWriter().write(file.toString());
                } else {
                    resp.getWriter().write("file not found");
                }
            } catch (Exception e) {
                resp.getWriter().write("invalid file id");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setContentType("application/json");
            resp.getWriter().write("File id is required");
        }

        try {
            Integer id = Integer.parseInt(pathInfo.substring(1));
            String name = req.getParameter("name");
            String filePath = req.getParameter("filePath");

            File file = fileService.getFileById(id);
            file.setName(name);
            file.setFilePath(filePath);
            File updatedFile = fileService.updateFile(file);

            resp.setContentType("application/json");
            resp.getWriter().write(updatedFile.toString());
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid file ID\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setContentType("application/json");
            resp.getWriter().write("File id is required");
        }

        try {
            Integer id = Integer.parseInt(pathInfo.substring(1));
            fileService.deleteFileById(id);

            resp.setContentType("application/json");
            resp.getWriter().write("File deleted");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid file ID\"}");
        }
    }

}
