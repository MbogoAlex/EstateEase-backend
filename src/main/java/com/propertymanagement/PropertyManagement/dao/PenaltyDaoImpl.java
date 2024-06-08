package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.Penalty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;

@Repository
public class PenaltyDaoImpl implements PenaltyDao{

    private final EntityManager entityManager;
    @Autowired
    public PenaltyDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Penalty addPenalty(Penalty penalty) {
        entityManager.persist(penalty);
        return penalty;
    }

    @Override
    public Penalty getPenalty(int id) {
        TypedQuery<Penalty> query = entityManager.createQuery("from Penalty where id = :id", Penalty.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Penalty updatePenalty(Penalty penalty) {
        entityManager.merge(penalty);
        return penalty;
    }

    @Override
    public String removePenalty(int id) {
        Query query = entityManager.createQuery("delete from Penalty where id = :id");
        query.setParameter("id", id);
        return "Deleted "+query.executeUpdate()+" rows";
    }

    @Override
    public Penalty activatePenalty(int id) {
        TypedQuery<Penalty> query = entityManager.createQuery("from Penalty where id = :id", Penalty.class);
        query.setParameter("id", id);
        Penalty penalty = query.getSingleResult();
        penalty.setStatus(true);
        entityManager.merge(penalty);
        return penalty;
    }

    @Override
    public Penalty deactivatePenalty(int id) {
        TypedQuery<Penalty> query = entityManager.createQuery("from Penalty where id = :id", Penalty.class);
        query.setParameter("id", id);
        Penalty penalty = query.getSingleResult();
        penalty.setStatus(false);
        entityManager.merge(penalty);
        return penalty;
    }
}
