package com.mycompany.semaforostopologia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class SemaforoRepository implements SemaforoDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<SemaforoEntity> listAll() {
        return entityManager.createQuery("FROM SemaforoEntity ORDER BY id", SemaforoEntity.class)
                .getResultList();
    }

    @Override
    public SemaforoEntity findById(String id) {
        return entityManager.find(SemaforoEntity.class, id);
    }

    @Override
    public void persist(SemaforoEntity entity) {
        entityManager.merge(entity);
    }
}
