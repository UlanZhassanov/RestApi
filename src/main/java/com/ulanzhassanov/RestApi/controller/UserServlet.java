package com.ulanzhassanov.RestApi.controller;

import com.ulanzhassanov.RestApi.model.User;
import com.ulanzhassanov.RestApi.repository.hibernate.UserRepositoryImpl;
import com.ulanzhassanov.RestApi.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = {"/users", "/users/*"})
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserService(new UserRepositoryImpl());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        User user = new User();
        user.setName(name);

        User savedUser = userService.saveUser(user);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(savedUser.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo(); // Получаем динамическую часть URL

        resp.setContentType("application/json");

        if (pathInfo == null || pathInfo.equals("/")) {
            // Если путь /users, возвращаем всех пользователей
            List<User> users = userService.getAllUsers();
            StringBuilder json = new StringBuilder("[");
            for (User user : users) {
                json.append("{\"id\":").append(user.getId())
                        .append(",\"name\":\"").append(user.getName()).append("\"},");
            }
            if (json.length() > 1) json.setLength(json.length() - 1); // Удаляем последнюю запятую
            json.append("]");
            resp.getWriter().write(json.toString());
        } else {
            // Если путь /users/{id}, получаем пользователя по ID
            try {
                Integer id = Integer.parseInt(pathInfo.substring(1)); // Извлекаем ID из пути
                User user = userService.getUserById(id);
                if (user != null) {
                    resp.getWriter().write("{\"id\":" + user.getId() + ",\"name\":\"" + user.getName() + "\"}");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\":\"User not found\"}");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Invalid user ID\"}");
            }
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"User ID is required\"}");
            return;
        }

        try {
            Integer id = Integer.parseInt(pathInfo.substring(1)); // Извлекаем ID из пути
            String name = req.getParameter("name");

            User user = userService.getUserById(id);
            user.setName(name);
            User updatedUser = userService.updateUser(user);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(updatedUser.toString());

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid user ID\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"User ID is required\"}");
            return;
        }

        try {
            Integer id = Integer.parseInt(pathInfo.substring(1)); // Извлекаем ID из пути
            userService.deleteUserById(id);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"status\":\"ok\"}");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid user ID\"}");
        }
    }

}
