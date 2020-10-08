package dao;

import metier.model.Admin;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AdminDao {

    public List<Admin> getAllAdmin(){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Admin> query = em.createQuery("SELECT a FROM Admin a", Admin.class);
        return query.getResultList();
    }

}
