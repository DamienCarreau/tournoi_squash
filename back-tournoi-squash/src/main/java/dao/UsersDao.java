package dao;

import metier.model.Users;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Date;

public class UsersDao {
    
    public Users chercherParMail(String mail) {
        Users result = null;
        if(mail != null && mail.length() > 0){
            EntityManager em = JpaUtil.obtenirContextePersistance();
            TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.email = :mail", Users.class);
            query.setParameter("mail", mail); // correspond au paramètre ":mail" dans la requête
            List<Users> Utilisateurs = query.getResultList();
            if (!Utilisateurs.isEmpty()) {
                result = Utilisateurs.get(0); // premier de la liste
            }
        }
        return result;
    }

    public Users chercherParId(Long id) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Users.class, id); // renvoie null si l'identifiant n'existe pas
    }

    public boolean changeMDP(Long idUser, String mdp){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Users> query = em.createQuery("UPDATE Users SET motDePasse = :mdp WHERE id = :id", Users.class);
        query.setParameter("mdp", mdp);
        query.setParameter("id", idUser);
        int result = query.executeUpdate();
        boolean res = false;
        if(result != 0) {
            res = true;
        }
        return res;
    }

    public boolean modifieInformations(Long idUser, String nom, String prenom, Date dateNaissance, String telephone, String mail){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Users> query = em.createQuery("UPDATE Users SET nom=:nom, prenom=:prenom, dateDeNaissance=:date, telephone=:tel, email=:mail WHERE id = :id", Users.class);
        query.setParameter("nom", nom);
        query.setParameter("prenom", prenom);
        query.setParameter("date", dateNaissance);
        query.setParameter("tel", telephone);
        query.setParameter("mail", mail);
        query.setParameter("id", idUser);
        int result = query.executeUpdate();
        boolean res = false;
        if(result != 0) {
            res = true;
        }
        return res;
    }
    
}
