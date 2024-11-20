package com.ulanzhassanov.RestApi.repository.hibernate;

import com.ulanzhassanov.RestApi.model.File;
import com.ulanzhassanov.RestApi.repository.FileRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class FileRepositoryImpl implements FileRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("RestApiPU");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public File getById(Integer id) {
        return entityManager.find(File.class, id);
    }

    @Override
    public List<File> getAll() {
        return entityManager.createQuery("FROM File", File.class).getResultList();
    }

    @Override
    public File save(File file) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(file);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }

        return getById(file.getId());
    }

    @Override
    public File update(File file) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(file);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }

        return getById(file.getId());
    }

    @Override
    public void deleteById(Integer id) {
        try {
            entityManager.getTransaction().begin();
            File file = getById(id);
            if (file != null){
                entityManager.remove(file);
            } else {
                System.out.println("File with ID "+ id + " not found.");
            }
            entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
