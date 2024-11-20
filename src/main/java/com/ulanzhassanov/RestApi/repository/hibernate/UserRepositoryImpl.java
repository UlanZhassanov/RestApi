package com.ulanzhassanov.RestApi.repository.hibernate;

import com.ulanzhassanov.RestApi.model.User;
import com.ulanzhassanov.RestApi.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("RestApiPU");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public User getById(Integer id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public User save(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }

        return getById(user.getId());
    }

    @Override
    public User update(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }

        return getById(user.getId());
    }

    @Override
    public void deleteById(Integer id) {
        try {
            entityManager.getTransaction().begin();
            User user = getById(id);
            if (user != null){
                entityManager.remove(user);
            } else {
                System.out.println("User with ID "+ id + " not found.");
            }
            entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
