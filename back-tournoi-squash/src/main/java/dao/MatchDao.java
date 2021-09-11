package dao;

import metier.model.Matchs;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Date;

public class MatchDao {

    public void creer(Matchs match){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(match);
    }

    public Matchs getMatch(Long idMatch) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Matchs> query = em.createQuery("SELECT m FROM Matchs m WHERE m.id = :idMatch", Matchs.class);
        query.setParameter("idMatch", idMatch);
        List<Matchs> matchs = query.getResultList();
        Matchs result = null;
        if(!matchs.isEmpty())
            result = matchs.get(0);
        return result;
    }

    public List<Matchs> demande(Long idUser, Long idSport) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Matchs> query = em.createQuery("SELECT m FROM Matchs m WHERE m.sport.id = :idSport AND ((m.etat = :etat1 AND m.joueur1.id = :idUser) OR (m.etat = :etat2 AND m.joueur2.id = :idUser))", Matchs.class);
        query.setParameter("idSport", idSport);
        query.setParameter("idUser", idUser);
        query.setParameter("etat1", "EN_ATTENTE_J1");
        query.setParameter("etat2", "EN_ATTENTE_J2");
        List<Matchs> result = query.getResultList();
        return result;
    }

    public boolean accepte(Long idMatch) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Matchs> query = em.createQuery("UPDATE Matchs SET etat = :etat WHERE id = :id", Matchs.class);
        query.setParameter("id", idMatch);
        query.setParameter("etat", "EN_COURS");
        int result = query.executeUpdate();
        boolean res = false;
        if(result != 0) {
            res = true;
        }
        return res;
    }

    public List<Matchs> futur(Long idUser, Long idSport) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Matchs> query = em.createQuery("SELECT m FROM Matchs m WHERE m.sport.id = :idSport AND m.etat = :etat AND (m.joueur1.id = :idUser OR  m.joueur2.id = :idUser)", Matchs.class);
        query.setParameter("idSport", idSport);
        query.setParameter("idUser", idUser);
        query.setParameter("etat", "EN_COURS");
        List<Matchs> result = query.getResultList();
        return result;
    }

    public void changeDate(Long idMatch, Date date) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Matchs> query = em.createQuery("UPDATE Matchs SET dateMatch = :date, dateCreation = :creation WHERE id = :id", Matchs.class);
        query.setParameter("id", idMatch);
        query.setParameter("date", date);
        query.setParameter("creation", new Date());
        query.executeUpdate();
    }

    public void changeEtat(Long idMatch, String etat) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Matchs> query = em.createQuery("UPDATE Matchs SET etat = :etat WHERE id = :id", Matchs.class);
        query.setParameter("id", idMatch);
        query.setParameter("etat", etat);
        query.executeUpdate();
    }

    public void supprimeMatchActif(Long idUser) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Matchs> query = em.createQuery("DELETE FROM Matchs WHERE (etat = :e1 OR etat = :e2 OR etat = :e3) AND (joueur1.id = :id OR joueur2.id = :id)", Matchs.class);
        query.setParameter("id", idUser);
        query.setParameter("e1", "EN_ATTENTE_J1");
        query.setParameter("e2", "EN_ATTENTE_J2");
        query.setParameter("e3", "EN_COURS"); 
        query.executeUpdate();
    }

    public void resultats(Long idMatch, int vainqueur, int scoreV, int scoreP) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Matchs> query = em.createQuery("UPDATE Matchs SET etat = :etat, scoreJ1 = :s1, scoreJ2 = :s2 WHERE id = :id", Matchs.class);
        query.setParameter("id", idMatch);
        if(vainqueur == 1){
            query.setParameter("s1", scoreV);
            query.setParameter("s2", scoreP);
        }else{
            query.setParameter("s1", scoreP);
            query.setParameter("s2", scoreV);
        }
        
        query.setParameter("etat", "TERMINEE"); 
        query.executeUpdate();
    }

    public List<Matchs> afficheMatchs(Long idSport) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Matchs> query = em.createQuery("SELECT m FROM Matchs m WHERE m.sport.id = :id AND (m.etat = :etat1 OR m.etat = :etat2) ORDER BY m.dateMatch DESC", Matchs.class);
        query.setParameter("id", idSport);     
        query.setParameter("etat1", "TERMINEE"); 
        query.setParameter("etat2", "FORFAIT"); 
        return query.getResultList();
    }

    public List<Matchs> getHistorique(Long idUser, Long idSport) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Matchs> query = em.createQuery("SELECT m FROM Matchs m WHERE m.sport.id = :id AND (m.etat = :etat1 OR m.etat = :etat2) AND (m.joueur1.id = :idUser OR m.joueur2.id = :idUser) ORDER BY m.dateMatch DESC", Matchs.class);
        query.setParameter("id", idSport);     
        query.setParameter("etat1", "TERMINEE"); 
        query.setParameter("etat2", "FORFAIT"); 
        query.setParameter("idUser",idUser);
        return query.getResultList();
    }

    public boolean supprimerMatch(Long idUser) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Matchs> query = em.createQuery("DELETE FROM Matchs WHERE joueur1.id = :id OR joueur2.id = :id", Matchs.class);
        query.setParameter("id", idUser);
        int result = query.executeUpdate();
        boolean res = false;
        if(result != 0) {
            res = true;
        }
        return res;
    }

    public List<Matchs> obtenirMatchEnAttente() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Matchs> query = em.createQuery("SELECT m FROM Matchs m WHERE m.etat = :e1 OR m.etat = :e2", Matchs.class);
        query.setParameter("e1", "EN_ATTENTE_J1");
        query.setParameter("e2", "EN_ATTENTE_J2");
        return query.getResultList();
    }

}
