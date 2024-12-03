package com.ulanzhassanov.RestApi.repository.hibernate;

import com.ulanzhassanov.RestApi.model.Event;
import com.ulanzhassanov.RestApi.repository.EventRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class EventRepositoryImpl implements EventRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("RestApiPU");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public Event getById(Integer id) {
        return entityManager.find(Event.class, id);
    }

    @Override
    public List<Event> getAll() {
        return entityManager.createQuery("FROM Event", Event.class).getResultList();
    }

    public Event getByFileId(Integer id) {
        return entityManager.createQuery("FROM Event e WHERE e.file.id = :fileId", Event.class).setParameter("fileId", id).getSingleResult();
    }

    @Override
    public Event save(Event event) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(event);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }

        return getById(event.getId());
    }

    @Override
    public Event update(Event event) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(event);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }

        return getById(event.getId());
    }

    @Override
    public void deleteById(Integer id) {
        try {
            entityManager.getTransaction().begin();
            Event event = getById(id);
            if (event != null){
                entityManager.remove(event);
            } else {
                System.out.println("Event with ID "+ id + " not found.");
            }
            entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
