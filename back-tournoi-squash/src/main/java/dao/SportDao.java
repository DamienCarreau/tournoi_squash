package dao;

import metier.model.Sport;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class SportDao {

    public void creer(Sport sport){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(sport);
    }
    
    public Sport chercherParId(Long id) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Sport.class, id); // renvoie null si l'identifiant n'existe pas
    }

    public List<Sport> getListe(){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Sport> query = em.createQuery("SELECT s FROM Sport s", Sport.class);
        return query.getResultList();
    }

}
