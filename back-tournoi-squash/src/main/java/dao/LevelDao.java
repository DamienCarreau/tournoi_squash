package dao;

import metier.model.Level;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class LevelDao {

    public void creer(Level level){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(level);
    }

    public Level getLevel(Long idUser, Long idSport) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Level> query = em.createQuery("SELECT l FROM Level l WHERE l.joueur.id = :idUser AND l.sport.id = :idSport", Level.class);
        query.setParameter("idUser", idUser);
        query.setParameter("idSport", idSport);
        List<Level> level = query.getResultList();
        Level result = null;
        if(!level.isEmpty())
            result = level.get(0);
        return result;
    }

    public Level chercherParId(Long id) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Level.class, id); // renvoie null si l'identifiant n'existe pas
    }

    public List<Level> getAllLevel(Long idUser) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Level> query = em.createQuery("SELECT l FROM Level l WHERE l.joueur.id = :idUser", Level.class);
        query.setParameter("idUser", idUser);
        return query.getResultList();
    }

    public Level getLevelByClassement(Long idSport, Long classement){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Level> query = em.createQuery("SELECT l FROM Level l WHERE l.classement = :classement AND l.sport.id = :idSport", Level.class);
        query.setParameter("classement", classement);
        query.setParameter("idSport", idSport);
        List<Level> level = query.getResultList();
        Level result = null;
        if(!level.isEmpty())
            result = level.get(0);
        return result;
    }

    public Long introduction(Long idSport, int cpt, List<String> niveaux){
        Long result = 0L;
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Level> query = em.createQuery("SELECT l FROM Level l WHERE l.sport.id = :idSport AND l.classement IS NOT NULL AND l.classement <> -1 ORDER BY l.classement DESC", Level.class);
        query.setParameter("idSport", idSport);
        List<Level> level = query.getResultList();
        if(!level.isEmpty()){
            for(Level l : level){
                int index = niveaux.indexOf(l.getNiveau());
                if(index != -1 && index <= cpt){
                    result = l.getClassement();
                    break;
                }
            }
        }        
        return result;
    }

    public List<Level> obtenirJoueurInferieur(Long idSport, Long classement){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Level> query = em.createQuery("SELECT l FROM Level l WHERE l.sport.id = :idSport AND l.classement IS NOT NULL AND l.classement > :classement", Level.class);
        query.setParameter("classement", classement);
        query.setParameter("idSport", idSport);
        return query.getResultList();
    }

    public boolean setClassement(Long idLevel, Long classement) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Level> query = em.createQuery("UPDATE Level SET classement = :classement WHERE id = :id", Level.class);
        query.setParameter("id", idLevel);
        if(classement.equals(-1L))
            query.setParameter("classement", null);
        else
            query.setParameter("classement", classement);
        
        int result = query.executeUpdate();
        boolean res = false;
        if(result != 0) {
            res = true;
        }
        return res;
    }

    public boolean setNiveau(Long idLevel, String niveau) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Level> query = em.createQuery("UPDATE Level SET niveau = :niveau WHERE id = :id", Level.class);
        query.setParameter("id", idLevel);
        query.setParameter("niveau", niveau);
        int result = query.executeUpdate();
        boolean res = false;
        if(result != 0) {
            res = true;
        }
        return res;
    }

    public List<Level> getClassement(Long idSport) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Level> query = em.createQuery("SELECT l FROM Level l WHERE l.sport.id = :idSport AND l.classement IS NOT NULL ORDER BY l.classement", Level.class);
        query.setParameter("idSport", idSport);
        return query.getResultList();
    }

    public List<Level> getDefiePossible(Long idSport, Long classement) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Level> query = em.createQuery("SELECT l FROM Level l WHERE l.sport.id = :idSport AND l.classement >= :classement AND l.classement IS NOT NULL ORDER BY l.classement", Level.class);
        query.setParameter("idSport", idSport);
        query.setParameter("classement", classement-3);
        return query.getResultList();
    }

    public List<Level> obtenirJoueurEntre(Long idSport, Long classement1, Long classement2) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Level> query = em.createQuery("SELECT l FROM Level l WHERE l.sport.id = :idSport AND l.classement > :classement1 AND l.classement < :classement2 AND l.classement IS NOT NULL ORDER BY l.classement", Level.class);
        query.setParameter("idSport", idSport);
        query.setParameter("classement1", classement1);
        query.setParameter("classement2", classement2);
        return query.getResultList();
    }

    public boolean modifieNiveau(Long idUser, Long idSport, String niveau) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Level> query = em.createQuery("UPDATE Level SET niveau = :niveau WHERE joueur.id = :idUser AND sport.id = :idSport", Level.class);
        query.setParameter("idUser", idUser);
        query.setParameter("idSport", idSport);
        query.setParameter("niveau", niveau);
        int result = query.executeUpdate();
        boolean res = false;
        if(result != 0) {
            res = true;
        }
        return res;
    }

    public List<Level> obtenirTous(Long idSport) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Level> query = em.createQuery("SELECT l FROM Level l WHERE l.sport.id = :idSport", Level.class);
        query.setParameter("idSport", idSport);
        return query.getResultList();
    }

    public int obtenirNombreJoueurInf(Level level, String niv) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Level> query = em.createQuery("SELECT l FROM Level l WHERE l.niveau = :niveau AND l.classement < :classement AND l.sport.id = :id", Level.class);
        query.setParameter("niveau", niv);
        query.setParameter("id", level.getSport().getId());
        query.setParameter("classement", level.getClassement());
        return query.getResultList().size();
    }

    public int obtenirNombreJoueurSup(Level level, String niv) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Level> query = em.createQuery("SELECT l FROM Level l WHERE l.niveau = :niveau AND l.classement > :classement AND l.sport.id = :id", Level.class);
        query.setParameter("niveau", niv);
        query.setParameter("id", level.getSport().getId());
        query.setParameter("classement", level.getClassement());
        return query.getResultList().size();
    }

    public boolean supprimerLevel(Long idUser) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Level> query = em.createQuery("DELETE FROM Level WHERE joueur.id = :id", Level.class);
        query.setParameter("id", idUser);
        int result = query.executeUpdate();
        boolean res = false;
        if(result != 0) {
            res = true;
        }
        return res;
    }

}
