package com.ulanzhassanov.RestApi.controller;

import com.ulanzhassanov.RestApi.model.Event;
import com.ulanzhassanov.RestApi.model.File;
import com.ulanzhassanov.RestApi.model.User;
import com.ulanzhassanov.RestApi.repository.hibernate.EventRepositoryImpl;
import com.ulanzhassanov.RestApi.repository.hibernate.FileRepositoryImpl;
import com.ulanzhassanov.RestApi.repository.hibernate.UserRepositoryImpl;
import com.ulanzhassanov.RestApi.service.EventService;
import com.ulanzhassanov.RestApi.service.FileService;
import com.ulanzhassanov.RestApi.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/events", "/events/*"})
public class EventServlet extends HttpServlet {
    private final EventService eventService = new EventService(new EventRepositoryImpl());
    private final FileService fileService = new FileService(new FileRepositoryImpl());
    private final UserService userService = new UserService(new UserRepositoryImpl());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = Integer.parseInt(req.getParameter("userId"));
        Integer fileId = Integer.parseInt(req.getParameter("fileId"));

        Event event = new Event();
        User user = userService.getUserById(userId);
        File file = fileService.getFileById(fileId);
        event.setUser(user);
        event.setFile(file);

        Event savedEvent = eventService.saveEvent(event);

        resp.setContentType("application/json");
        resp.getWriter().print(savedEvent.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Event> events = eventService.getAllEvents();
            resp.getWriter().write(events.toString());
        } else {
            try {
                Integer id = Integer.parseInt(pathInfo.substring(1));
                Event event = eventService.getEventById(id);
                if (event != null) {
                    resp.getWriter().write(event.toString());
                } else {
                    resp.getWriter().write("event not found");
                }
            } catch (Exception e) {
                resp.getWriter().write("invalid event id");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setContentType("application/json");
            resp.getWriter().write("event id is required");
        }

        try {
            Integer id = Integer.parseInt(pathInfo.substring(1));
            Integer userId = Integer.parseInt(req.getParameter("userId"));
            Integer fileId = Integer.parseInt(req.getParameter("fileId"));

            Event event = eventService.getEventById(id);
            event.setUser(userService.getUserById(userId));
            event.setFile(fileService.getFileById(fileId));
            Event updatedEvent = eventService.updateEvent(event);

            resp.setContentType("application/json");
            resp.getWriter().write(updatedEvent.toString());
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid event ID\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setContentType("application/json");
            resp.getWriter().write("Event id is required");
        }

        try {
            Integer id = Integer.parseInt(pathInfo.substring(1));
            eventService.deleteEventById(id);

            resp.setContentType("application/json");
            resp.getWriter().write("Event deleted");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid Event ID\"}");
        }
    }
}
