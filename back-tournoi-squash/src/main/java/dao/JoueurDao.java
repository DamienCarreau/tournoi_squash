package dao;

import metier.model.Joueur;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class JoueurDao {

    public void creer(Joueur client){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(client);
    }
    
    public Joueur chercherParId(Long id) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Joueur.class, id); // renvoie null si l'identifiant n'existe pas
    }

    public boolean changePause(Long idUser, int etat){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Joueur> query = em.createQuery("UPDATE Joueur SET pause = :etat WHERE id = :id", Joueur.class);
        query.setParameter("etat", etat);
        query.setParameter("id", idUser);
        int result = query.executeUpdate();
        boolean res = false;
        if(result != 0) {
            res = true;
        }
        return res;
    }

    public List<Joueur> getListe(){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Joueur> query = em.createQuery("SELECT j FROM Joueur j ORDER BY j.prenom", Joueur.class);
        return query.getResultList();
    }

    public boolean supprimerJoueur(Long idUser) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Joueur> query = em.createQuery("DELETE FROM Joueur WHERE id = :id", Joueur.class);
        query.setParameter("id", idUser);
        int result = query.executeUpdate();
        boolean res = false;
        if(result != 0) {
            res = true;
        }
        return res;
    }

    public boolean accepteJoueur(Long idUser) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Joueur> query = em.createQuery("UPDATE Joueur SET autorisation = :etat WHERE id = :id", Joueur.class);
        query.setParameter("id", idUser);
        query.setParameter("etat", 1);
        int result = query.executeUpdate();
        boolean res = false;
        if(result != 0) {
            res = true;
        }
        return res;
    }

}
