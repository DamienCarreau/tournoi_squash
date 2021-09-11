package ihm;

import metier.model.*;
import dao.JpaUtil;
import util.Util;
import metier.service.Service;

import java.util.Date;
import java.util.List;
import javafx.util.Pair;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    
    private static Joueur[] joueurs;
    private static Admin admin;
    private static Sport[] sports;
    private static Level[] levels;
    private static Matchs[] matchs;

    public static void main(String[] args){
        System.out.println("..Main..");

        JpaUtil.init();

        //INIT
        /*inscriptionJoueur();
        inscriptionAdmin();
        creationSport();
        inscriptionSport();
        creationMatch();
        accepteMatch();
        testerAnnoncerResultats();*/

        //TESTS :
        //testerAuthentification();
        //testerModificationMDP();
        //testerGetUserInformations();
        //testerPauseCompte();
        //testerPauseJoueur();
        //testerModifierInformations();
        //testerAfficheJoueurs();
        //testerAfficheSports();
        //testerGetSportLevel();
        //testerConsulterSport();
        //testerInfoMatch();
        //testerMatchDemandes();
        //testerFuturMatch();
        //testerNouvelleDate();
        //testerGetClassement();
        //testerGetDefiePossible();
        //testerAfficheMatchs();
        //testerGetHistorique();
        //testerModifieNiveau();
        //testerSupprimerJoueur();
        //testerSupprimerCompte();
        //testerMajDate();
        //testerAccepteJoueur();

        //essaiRapide();

        JpaUtil.destroy();
    }

    public static void essaiRapide(){
        Service service = new Service();

        service.majDate();

       
    }

    public static void inscriptionJoueur() {

        Service service = new Service();

        joueurs = new Joueur[7];
        
        joueurs[0] = new Joueur("claude.chappe@insa-lyon.fr", "Chappe", "Claude", "0606060606", new Date("12/23/2002"), "mdp", 'H');
        Long id1 = service.inscrireUtilisateur(joueurs[0]);

        joueurs[1] = new Joueur("damien.carreau@insa-lyon.fr", "Carreau", "Damien", "0606060606", new Date("09/30/1999"), "mdp", 'H');
        Long id2 = service.inscrireUtilisateur(joueurs[1]);

        joueurs[2] = new Joueur("michel.dupont@gmail.com", "Dupont", "Michel", "0606060606", new Date("12/23/2000"), "mdp", 'H');
        Long id3 = service.inscrireUtilisateur(joueurs[2]);

        joueurs[3] = new Joueur("camile.martin@gmail.com", "Martin", "Camille", "0606060606", new Date("12/23/2000"), "mdp", 'F');
        Long id4 = service.inscrireUtilisateur(joueurs[3]);

        joueurs[4] = new Joueur("pierre.dupont@gmail.com", "Dupont", "Pierre", "0606060606", new Date("12/23/2000"), "mdp", 'H');
        Long id5 = service.inscrireUtilisateur(joueurs[4]);

        joueurs[5] = new Joueur("sandra.chevalier@gmail.com", "Chevalier", "Sandra", "0606060606", new Date("12/23/2000"), "mdp", 'F');
        Long id6 = service.inscrireUtilisateur(joueurs[5]);

        joueurs[6] = new Joueur("orayesgemez5313@free.fr", "SING", "Camille", "Ainhoa", new Date("12/23/2000"), "mdp", 'F');
        Long id7 = service.inscrireUtilisateur(joueurs[6]);

    }

    public static void inscriptionAdmin(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
        EntityManager em = emf.createEntityManager();
        
        Util util = new Util();
        //admin = new Admin("admin", "ADMIN", "Admin", "000000000", new Date("01/01/2000"), util.crypt("admin",0));
        admin = new Admin("contact@squashrhone.com", "Karpenschif", "Anne-Laure", "0629683538", new Date("19/11/1971"), util.crypt("Metropole2017",0));

        try {
            em.getTransaction().begin();
            em.persist(admin);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public static void creationSport() {

        Service service = new Service();

        sports = new Sport[2];
        
        sports[0] = new Sport("Squash","L1,L2,L3,M1,M2,M3");
        Long id1 = service.creerSport(sports[0]);
        if (id1 != null) {
            System.out.println("> Succès création "+sports[0].getNom());
        } else {
            System.out.println("> Échec création "+sports[0].getNom());
        }

        sports[1] = new Sport("Badminton","L1,L2,L3,M1,M2,M3,");
        Long id2 = service.creerSport(sports[1]);
        if (id2 != null) {
            System.out.println("> Succès création "+sports[1].getNom());
        } else {
            System.out.println("> Échec création "+sports[1].getNom());
        }
    }

    public static void inscriptionSport(){

        Service service = new Service();
        
        levels = new Level[3];

        boolean result;
        
        levels[0] = new Level(joueurs[0],sports[0],"L1");
        result = service.inscriptionSport(levels[0]);
        /*if (result) {
            System.out.println("Le joueur "+joueurs[1].getNom()+" "+joueurs[1].getPrenom()+" est fait maintenant du "+sports[0].getNom());
        } else {
            System.out.println("L'inscription de "+joueurs[1].getNom()+" "+joueurs[1].getPrenom()+" au sport "+sports[0].getNom()+" a échoué !");
        }*/

        levels[1] = new Level(joueurs[0],sports[1],"L2");
        result = service.inscriptionSport(levels[1]);
        /*if (result) {
            System.out.println("Le joueur "+joueurs[1].getNom()+" "+joueurs[1].getPrenom()+" est fait maintenant du "+sports[1].getNom());
        } else {
            System.out.println("L'inscription de "+joueurs[1].getNom()+" "+joueurs[1].getPrenom()+" au sport "+sports[1].getNom()+" a échoué !");
        }*/

        levels[2] = new Level(joueurs[2],sports[0],"L2");
        result = service.inscriptionSport(levels[2]);
        /*if (result) {
            System.out.println("Le joueur "+joueurs[2].getNom()+" "+joueurs[2].getPrenom()+" est fait maintenant du "+sports[0].getNom());
        } else {
            System.out.println("L'inscription de "+joueurs[2].getNom()+" "+joueurs[2].getPrenom()+" au sport "+sports[0].getNom()+" a échoué !");
        }*/

        service.inscriptionSport(new Level(joueurs[3],sports[0],"L3"));
        service.inscriptionSport(new Level(joueurs[4],sports[0],"L2"));
        service.inscriptionSport(new Level(joueurs[5],sports[0],"M3"));
        service.inscriptionSport(new Level(joueurs[6],sports[0],"M2"));
    }

    public static void creationMatch(){

        Service service = new Service();

        matchs = new Matchs[7];
        
        matchs[0] = new Matchs(sports[0], joueurs[0], joueurs[2], new Date("06/17/2020"));
        String id1 = service.creerMatch(matchs[0]);
        /*if (id1 != null) {
            System.out.println(matchs[0].toString());
        } else {
            System.out.println("> Échec création match");
        }*/

        matchs[1] = new Matchs(sports[0], joueurs[2], joueurs[1], new Date("06/14/2020"));
        String id2 = service.creerMatch(matchs[1]);
        /*if (id2 != null) {
            System.out.println(matchs[1].toString());
        } else {
            System.out.println("> Échec création match");
        }*/

        matchs[2] = new Matchs(sports[1], joueurs[0], joueurs[1], new Date("06/12/2020"));
        String id3 = service.creerMatch(matchs[2]);
        /*if (id3 != null) {
            System.out.println(matchs[2].toString());
        } else {
            System.out.println("> Échec création match");
        }*/

        matchs[3] = new Matchs(sports[0],joueurs[3],joueurs[4], new Date("06/20/2020"));
        matchs[4] = new Matchs(sports[0],joueurs[5],joueurs[0], new Date("06/20/2020"));
        matchs[5] = new Matchs(sports[0],joueurs[6],joueurs[0], new Date("06/20/2020"));
        matchs[6] = new Matchs(sports[0],joueurs[0],joueurs[4], new Date("06/20/2020"));

        for(int i = 3; i < 7; i++)
            service.creerMatch(matchs[i]);
    }

    public static void testerAuthentification() {
        //======================================
        // Tester l'authentification des utilisateurs :

        System.out.println();
        System.out.println("**** testerAuthentificationUtilisateur() ****");
        System.out.println();

        Service service = new Service();

        Users user;
        String mail;
        String motDePasse;

        mail = "camile.martin@gmail.com";
        motDePasse = "mdp";
        user = service.authentifierUtilisateur(mail, motDePasse);
        if (user != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }

        mail = "claude.chappe@insa-lyon.fr";
        motDePasse = "mdp";
        user = service.authentifierUtilisateur(mail, motDePasse);
        if (user != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }

        mail = "claude.chappe@insa-lyon.fr";
        motDePasse = "mmm";
        user = service.authentifierUtilisateur(mail, motDePasse);
        if (user != null) {
            System.out.println("Authentification réussie avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        } else {
            System.out.println("Authentification échouée avec le mail '" + mail + "' et le mot de passe '" + motDePasse + "'");
        }

        System.out.println();
        System.out.println("**** Fin testerAuthentificationUtilisateur() ****");
        System.out.println();

        //======================================
    }

    public static void testerModificationMDP(){
        System.out.println();
        System.out.println("**** testerModificationMDP() ****");
        System.out.println();

        Service service = new Service();

        boolean result;

        result = service.modificationMDP(joueurs[0].getDateDeNaissance(), joueurs[0].getTelephone(), joueurs[0].getEmail(), "nouveauMdp");
        if(result)
            System.out.println("Modification du mot de passe réussie pour l'utilisateur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());
        else
            System.out.println("Modification du mot de passe échouée pour l'utilisateur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());


        result = service.modificationMDP(joueurs[0].getDateDeNaissance(), "000", joueurs[0].getEmail(), "nouveauMdp");
        if(result)
            System.out.println("Modification du mot de passe réussie pour l'utilisateur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());
        else
            System.out.println("Modification du mot de passe échouée pour l'utilisateur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());


        result = service.modificationMDP(joueurs[0].getDateDeNaissance(), joueurs[0].getTelephone(), "email", "nouveauMdp");
        if(result)
            System.out.println("Modification du mot de passe réussie pour l'utilisateur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());
        else
            System.out.println("Modification du mot de passe échouée pour l'utilisateur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());


        result = service.modificationMDP(new Date("01/01/2000"), joueurs[0].getTelephone(), joueurs[0].getEmail(), "nouveauMdp");
        if(result)
            System.out.println("Modification du mot de passe réussie pour l'utilisateur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());
        else
            System.out.println("Modification du mot de passe échouée pour l'utilisateur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());


        System.out.println();
        System.out.println("**** Fin testerModificationMDP() ****");
        System.out.println();
    }

    public static void testerGetUserInformations(){
        System.out.println();
        System.out.println("**** testerGetUserInformations() ****");
        System.out.println();

        Service service = new Service();

        Users result;

        result = service.getUserInformations(joueurs[0].getId());
        if(result != null)
            System.out.println(result.toString());
        else
            System.out.println("Erreur getUserInformations sur le joueur : "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());
   

        result = service.getUserInformations(joueurs[2].getId());
        if(result != null)
            System.out.println(result.toString());
        else
            System.out.println("Erreur getUserInformations sur le joueur : "+joueurs[2].getNom()+" "+joueurs[2].getPrenom());


        System.out.println();
        System.out.println("**** Fin testerGetUserInformations() ****");
        System.out.println();
    }

    public static void testerPauseCompte(){
        System.out.println();
        System.out.println("**** testerPauseCompte() ****");
        System.out.println();

        Service service = new Service();

        boolean result;

        result = service.pauseCompte(joueurs[0].getId(), joueurs[0].getDateDeNaissance(), joueurs[0].getTelephone(), joueurs[0].getEmail(), joueurs[0].getMotDePasse());
        if(result)
            System.out.println("Le joueur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom()+" est maintenant en pause");
        else
            System.out.println("Erreur pauseCompte sur le joueur : "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());
   
        
        result = service.pauseCompte(joueurs[0].getId(), joueurs[0].getDateDeNaissance(), joueurs[0].getTelephone(), joueurs[0].getEmail(), joueurs[0].getMotDePasse());
        if(result)
            System.out.println("Le joueur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom()+" n'est maintenant plus en pause");
        else
            System.out.println("Erreur pauseCompte sur le joueur : "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());


        result = service.pauseCompte(joueurs[0].getId(), joueurs[0].getDateDeNaissance(), "0", joueurs[0].getEmail(), joueurs[0].getMotDePasse());
        if(result)
            System.out.println("Le joueur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom()+" est maintenant en pause");
        else
            System.out.println("Erreur pauseCompte sur le joueur : "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());

        

        System.out.println();
        System.out.println("**** Fin testerPauseCompte() ****");
        System.out.println();
    }

    public static void testerPauseJoueur(){
        System.out.println();
        System.out.println("**** testerPauseJoueur() ****");
        System.out.println();

        Service service = new Service();

        boolean result;

        result = service.pauseJoueur(joueurs[0].getId());
        if(result)
            System.out.println("Le joueur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom()+" est maintenant en pause");
        else
            System.out.println("Erreur pauseCompte sur le joueur : "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());
   
        
        result = service.pauseJoueur(joueurs[0].getId());
        if(result)
            System.out.println("Le joueur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom()+" n'est maintenant plus en pause");
        else
            System.out.println("Erreur pauseCompte sur le joueur : "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());


        result = service.pauseJoueur(joueurs[2].getId());
        if(result)
            System.out.println("Le joueur "+joueurs[2].getNom()+" "+joueurs[2].getPrenom()+" est maintenant en pause");
        else
            System.out.println("Erreur pauseCompte sur le joueur : "+joueurs[2].getNom()+" "+joueurs[2].getPrenom());
        

        System.out.println();
        System.out.println("**** Fin testerPauseJoueur() ****");
        System.out.println();
    }

    public static void testerModifierInformations(){
        System.out.println();
        System.out.println("**** testerModificatierInformations() ****");
        System.out.println();

        Service service = new Service();

        boolean result;
       
        result = service.modifierInformations(joueurs[0].getId(), "Petoux", "Patrick", joueurs[0].getDateDeNaissance(), joueurs[0].getTelephone(),"patrick.petoux@gmail.com","fff");
        if(result)
            System.out.println("Modification réussie pour l'utilisateur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());
        else
            System.out.println("Modification échouée pour l'utilisateur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());


        result = service.modifierInformations(joueurs[0].getId(), "Petoux", "Patrick", joueurs[0].getDateDeNaissance(), joueurs[0].getTelephone(),"patrick.petoux@gmail.com","mdp");
        if(result)
            System.out.println("Modification réussie pour l'utilisateur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());
        else
            System.out.println("Modification échouée pour l'utilisateur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom());


        System.out.println();
        System.out.println("**** Fin testerModificatierInformations() ****");
        System.out.println();
    }

    public static void testerAfficheJoueurs(){
        System.out.println();
        System.out.println("**** testerAfficheJoueurs() ****");
        System.out.println();

        Service service = new Service();

        List<Joueur> result = service.afficheJoueurs();
        if(result != null && !result.isEmpty())
            for(Joueur j : result)
                System.out.println(j.toString());
        else
            System.out.println("Erreur afficheJoueur !");


        System.out.println();
        System.out.println("**** Fin testerAfficheJoueurs() ****");
        System.out.println();
    }

    public static void testerAfficheSports(){
        System.out.println();
        System.out.println("**** testerAfficheSports() ****");
        System.out.println();

        Service service = new Service();

        List<Sport> result = service.afficheSports();
        if(result != null && !result.isEmpty())
            for(Sport s : result)
                System.out.println(s.getNom());
        else
            System.out.println("Erreur afficheSports !");


        System.out.println();
        System.out.println("**** Fin testerAfficheSports() ****");
        System.out.println();
    }

    public static void testerGetSportLevel(){
        System.out.println();
        System.out.println("**** testerGetSportLevel() ****");
        System.out.println();

        Service service = new Service();

        List<String> result = service.getSportLevel(sports[0].getId());
        if(result != null && !result.isEmpty())
            for(String s : result)
                System.out.println(s);
        else
            System.out.println("Erreur getSportLevel !");

        System.out.println();
        System.out.println("**** Fin testerGetSportLevel() ****");
        System.out.println();
    }

    public static void testerConsulterSport(){
        System.out.println();
        System.out.println("**** testerConsulterSport() ****");
        System.out.println();

        Service service = new Service();

        boolean result;

        result = service.consulterSport(joueurs[0].getId(),sports[0].getId());
        if(result)
            System.out.println("Le joueur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom()+" est inscrit au "+sports[0].getNom());
        else 
            System.out.println("Le joueur "+joueurs[0].getNom()+" "+joueurs[0].getPrenom()+" n'est pas inscrit au "+sports[0].getNom());
        
            
        result = service.consulterSport(joueurs[1].getId(),sports[0].getId());
        if(result)
            System.out.println("Le joueur "+joueurs[1].getNom()+" "+joueurs[1].getPrenom()+" est inscrit au "+sports[0].getNom());
        else 
            System.out.println("Le joueur "+joueurs[1].getNom()+" "+joueurs[1].getPrenom()+" n'est pas inscrit au "+sports[0].getNom());


        System.out.println();
        System.out.println("**** Fin testerConsulterSport() ****");
        System.out.println();
    }

    public static void testerInfoMatch(){
        System.out.println();
        System.out.println("**** testerInfoMatch() ****");
        System.out.println();

        Service service = new Service();

        Matchs result;

        result = service.infoMatch(matchs[0].getId());
        if(result != null)
            System.out.println(result.toString());
        else 
            System.out.println("Echec informations sur le match id : "+matchs[0].getId());
        
            
        result = service.infoMatch(matchs[1].getId());
        if(result != null)
            System.out.println(result.toString());
        else 
            System.out.println("Echec informations sur le match id : "+matchs[1].getId());

        System.out.println();
        System.out.println("**** Fin testerInfoMatch() ****");
        System.out.println();
    }

    public static void testerMatchDemandes(){
        System.out.println();
        System.out.println("**** testerMatchDemandes() ****");
        System.out.println();

        Service service = new Service();

        List<Matchs> result;

        result = service.matchDemandes(joueurs[5].getId(),sports[0].getId());
        if(result != null)
            if(!result.isEmpty())
                for(Matchs m : result)
                    System.out.println(m.toString());
            else 
                System.out.println("Pas de matchs en attente");
        else
            System.out.println("Echec matchDemandes");
        
        
        System.out.println();

        result = service.matchDemandes(joueurs[0].getId(),sports[0].getId());
        if(result != null)
            if(!result.isEmpty())
                for(Matchs m : result)
                    System.out.println(m.toString());
            else 
                System.out.println("Pas de matchs en attente");
        else
            System.out.println("Echec matchDemandes");

        System.out.println();
        System.out.println("**** Fin testerMatchDemandes() ****");
        System.out.println();
    }

    public static void accepteMatch(){
        /*System.out.println();
        System.out.println("**** accepteMatch() ****");
        System.out.println();*/

        Service service = new Service();

        boolean result = false;

        result = service.matchAccepte(matchs[0].getId());
        /*if(result)
            System.out.println("Match accepter");
        else
            System.out.println("Echec matchAccepte");*/
        
        
        result = false;
        result = service.matchAccepte(matchs[1].getId());
        /*if(result)
            System.out.println("Match accepter");
        else
            System.out.println("Echec matchAccepte");*/

        service.matchAccepte(matchs[5].getId());
        service.matchAccepte(matchs[6].getId());
        

        /*System.out.println();
        System.out.println("**** Fin accepteMatch() ****");
        System.out.println();*/
    }

    public static void testerFuturMatch(){
        System.out.println();
        System.out.println("**** testerFuturMatch() ****");
        System.out.println();

        Service service = new Service();

        List<Matchs> result;

        result = service.futurMatch(joueurs[2].getId(),sports[0].getId());
        if(result != null)
            if(!result.isEmpty())
                for(Matchs m : result)
                    System.out.println(m.toString());
            else 
                System.out.println("Pas de futurs matchs");
        else
            System.out.println("Echec matchDemandes");
       

        System.out.println();
        System.out.println("**** Fin testerFuturMatch() ****");
        System.out.println();
    }

    public static void testerNouvelleDate(){
        System.out.println();
        System.out.println("**** testerNouvelleDate() ****");
        System.out.println();

        Service service = new Service();

        boolean result = service.nouvelleDate(matchs[4].getId(), new Date("06/28/2020"));
        if(result)
            System.out.println("Match repoussé");
        else
            System.out.println("Echec nouvelleDate");

        result = service.nouvelleDate(matchs[4].getId(), new Date("07/02/2020"));
        if(result)
            System.out.println("Match repoussé");
        else
            System.out.println("Echec nouvelleDate");

        System.out.println();
        System.out.println("**** Fin testerNouvelleDate() ****");
        System.out.println();
    }

    public static void testerGetClassement(){
        System.out.println();
        System.out.println("**** testerGetClassement() ****");
        System.out.println();

        Service service = new Service();

        List<Level> result = null;

        result = service.getClassement(sports[0].getId());
        if(!result.isEmpty()){
            System.out.println("Classement des joueurs du sport "+sports[0].getNom());
            for(Level level : result)
                System.out.println("    "+level.getClassement()+": "+level.getJoueur().getPrenom()+" "+level.getJoueur().getNom());
        }else
            System.out.println("Il n'y a pas de joueurs actif dans le sport "+sports[0].getNom());

        System.out.println();

        result = service.getClassement(sports[1].getId());
        if(!result.isEmpty()){
            System.out.println("Classement des joueurs du sport "+sports[0].getNom());
            for(Level level : result)
                System.out.println("    "+level.getClassement()+": "+level.getJoueur().getPrenom()+" "+level.getJoueur().getNom());
        }else
            System.out.println("Il n'y a pas de joueurs actif dans le sport "+sports[1].getNom());
            
        System.out.println();
        System.out.println("Mise en pause du joueur " + joueurs[0].getPrenom()+" "+joueurs[0].getNom());
        service.pauseJoueur(joueurs[0].getId());
        
        result = service.getClassement(sports[1].getId());
        if(!result.isEmpty()){
            System.out.println("Classement des joueurs du sport "+sports[0].getNom());
            for(Level level : result)
                System.out.println("    "+level.getClassement()+": "+level.getJoueur().getPrenom()+" "+level.getJoueur().getNom());
        }else
            System.out.println("Il n'y a pas de joueurs actif dans le sport "+sports[1].getNom());


        System.out.println();
        System.out.println("**** Fin testerGetClassement() ****");
        System.out.println();
    }

    public static void testerGetDefiePossible(){
        System.out.println();
        System.out.println("**** testerGetDefiePossible() ****");
        System.out.println();

        Service service = new Service();

        List<Level> result = null;

        int idJoueur = 3;
        result = service.getDefiePossible(joueurs[idJoueur].getId(),sports[0].getId());
        if(!result.isEmpty()){
            System.out.println("Défie possible pour le joueur "+joueurs[idJoueur].getPrenom()+" "+joueurs[idJoueur].getNom()+" dans le sport "+sports[0].getNom());
            for(Level level : result)
                System.out.println("    "+level.getClassement()+": "+level.getJoueur().getPrenom()+" "+level.getJoueur().getNom());
        }else
            System.out.println("Aucun défie possible pour le joueur "+joueurs[idJoueur].getPrenom()+" "+joueurs[idJoueur].getNom()+" dans le sport "+sports[0].getNom());

        idJoueur = 0;
        result = service.getDefiePossible(joueurs[idJoueur].getId(),sports[0].getId());
        if(result != null && !result.isEmpty()){
            System.out.println("Défie possible pour le joueur "+joueurs[idJoueur].getPrenom()+" "+joueurs[idJoueur].getNom()+" dans le sport "+sports[0].getNom());
            for(Level level : result)
                System.out.println("    "+level.getClassement()+": "+level.getJoueur().getPrenom()+" "+level.getJoueur().getNom());
        }else
            System.out.println("Aucun défie possible pour le joueur "+joueurs[idJoueur].getPrenom()+" "+joueurs[idJoueur].getNom()+" dans le sport "+sports[0].getNom());

        System.out.println();
        System.out.println("**** Fin testerGetDefiePossible() ****");
        System.out.println();
    }

    public static void testerAnnoncerResultats(){
        /*System.out.println();
        System.out.println("**** testerAnnoncerResultats() ****");
        System.out.println();*/

        Service service = new Service();

        boolean result = false;

        result = service.annoncerResultats(matchs[5].getId(),joueurs[6].getId(),5,2);
        /*if(result)
            System.out.println("Resultats annoncés");
        else
            System.out.println("Echec annoncerResultats");*/

        result = service.annoncerResultats(matchs[0].getId(),joueurs[0].getId(),5,2);
        /*if(result)
            System.out.println("Resultats annoncés");
        else
            System.out.println("Echec annoncerResultats");*/

        result = service.annoncerResultats(matchs[6].getId(),joueurs[4].getId(),4,3);
        /*if(result)
            System.out.println("Resultats annoncés");
        else
            System.out.println("Echec annoncerResultats");*/
        
        /*System.out.println();
        System.out.println("**** Fin testerAnnoncerResultats() ****");
        System.out.println();*/
    }

    public static void testerAfficheMatchs(){
        System.out.println();
        System.out.println("**** testerAfficheMatchs() ****");
        System.out.println();

        Service service = new Service();

        List<Matchs> result = null;

        result = service.afficheMatchs(sports[0].getId());
        if(!result.isEmpty()){
            System.out.println("Matchs terminés en "+sports[0].getNom());
            for(Matchs m : result)
                System.out.println("    "+m.getJoueur1().getNom()+" : "+m.getScoreJ1()+" - "+m.getScoreJ2()+" : "+m.getJoueur2().getNom()+"     "+m.getDateMatch());
        }else
            System.out.println("Aucun matchs términé en "+sports[0].getNom()+" pour le moment");

        result = service.afficheMatchs(sports[1].getId());
        if(!result.isEmpty()){
            System.out.println("Matchs terminés en "+sports[1].getNom());
            for(Matchs m : result)
                System.out.println("    "+m.getJoueur1().getNom()+" : "+m.getScoreJ1()+" - "+m.getScoreJ2()+" : "+m.getJoueur2().getNom()+"     "+m.getDateMatch());
        }else
            System.out.println("Aucun matchs términé en "+sports[1].getNom()+" pour le moment");

        System.out.println();
        System.out.println("**** Fin testerAfficheMatchs() ****");
        System.out.println();
    }

    public static void testerGetHistorique(){
        System.out.println();
        System.out.println("**** testerGetHistorique() ****");
        System.out.println();

        Service service = new Service();

        List<Matchs> result = null;

        result = service.getHistorique(joueurs[0].getId(),sports[0].getId());
        if(!result.isEmpty()){
            System.out.println("Matchs terminés en "+sports[0].getNom()+" pour le joueur "+joueurs[0].getNom());
            for(Matchs m : result)
                System.out.println("    "+m.getJoueur1().getNom()+" : "+m.getScoreJ1()+" - "+m.getScoreJ2()+" : "+m.getJoueur2().getNom()+"     "+m.getDateMatch());
        }else
            System.out.println("Aucun matchs términé en "+sports[0].getNom()+" pour ce joueur pour le moment");

        result = service.getHistorique(joueurs[2].getId(),sports[0].getId());
        if(!result.isEmpty()){
            System.out.println("Matchs terminés en "+sports[0].getNom()+" pour le joueur "+joueurs[2].getNom());
            for(Matchs m : result)
                System.out.println("    "+m.getJoueur1().getNom()+" : "+m.getScoreJ1()+" - "+m.getScoreJ2()+" : "+m.getJoueur2().getNom()+"     "+m.getDateMatch());
        }else
            System.out.println("Aucun matchs términé en "+sports[2].getNom()+" pour ce joueur pour le moment");


        System.out.println();
        System.out.println("**** Fin testerGetHistorique() ****");
        System.out.println();
    }

    public static void testerModifieNiveau(){
        System.out.println();
        System.out.println("**** testerModifieNiveau() ****");
        System.out.println();

        Service service = new Service();

        boolean result = service.modifieNiveau(levels[0].getId(),"M1");
        if(result)
            System.out.println("Niveau modifié");
        else
            System.out.println("Echec modifieNiveau");

        result = service.modifieNiveau(levels[2].getId(),"M3");
        if(result)
            System.out.println("Niveau modifié");
        else
            System.out.println("Echec modifieNiveau");
        

        System.out.println();
        System.out.println("**** Fin testerModifieNiveau() ****");
        System.out.println();
    }

    public static void testerSupprimerJoueur(){
        System.out.println();
        System.out.println("**** testerSupprimerJoueur() ****");
        System.out.println();

        Service service = new Service();

        boolean result = service.supprimerJoueur(joueurs[0].getId());
        if(result)
            System.out.println("Joueur supprimé");
        else
            System.out.println("Echec supprimerJoueur");

        result = service.supprimerJoueur(joueurs[3].getId());
        if(result)
            System.out.println("Joueur supprimé");
        else
            System.out.println("Echec supprimerJoueur");
        

        System.out.println();
        System.out.println("**** Fin testerSupprimerJoueur() ****");
        System.out.println();
    }

    public static void testerSupprimerCompte(){
        System.out.println();
        System.out.println("**** testerSupprimerCompte() ****");
        System.out.println();

        Service service = new Service();

        boolean result = service.supprimerCompte(joueurs[0].getId(), new Date("06/01/2020"), joueurs[0].getTelephone(), joueurs[0].getEmail(), joueurs[0].getMotDePasse());
        if(result)
            System.out.println("Joueur supprimé");
        else
            System.out.println("Echec supprimerJoueur");

        result = service.supprimerCompte(joueurs[0].getId(), joueurs[0].getDateDeNaissance(), joueurs[0].getTelephone(), joueurs[0].getEmail(), joueurs[0].getMotDePasse());
        if(result)
            System.out.println("Joueur supprimé");
        else
            System.out.println("Echec supprimerJoueur");
        

        System.out.println();
        System.out.println("**** Fin testerSupprimerCompte() ****");
        System.out.println();
    }

    public static void testerMajDate(){
        System.out.println();
        System.out.println("**** testerMajDate() ****");
        System.out.println();

        Service service = new Service();

        boolean result = service.majDate();
        if(result)
            System.out.println("Succès majDate");
        else
            System.out.println("Echec majDate"); 

        System.out.println();
        System.out.println("**** Fin testerMajDate() ****");
        System.out.println();
    }

    public static void testerAccepteJoueur(){
        System.out.println();
        System.out.println("**** testerAccepteJoueur() ****");
        System.out.println();

        Service service = new Service();

        Joueur joueur = new Joueur("claude", "Chappe", "Claude", "0606060606", new Date("12/23/2002"), "mdp", 'H');
        Long id1 = service.inscrireUtilisateur(joueur);

        System.out.println("Inscription du joueur "+joueur.toString());

        service.accepteJoueur(joueur.getId());

        System.out.println();
        System.out.println("**** Fin testerAccepteJoueur() ****");
        System.out.println();
    }

}
