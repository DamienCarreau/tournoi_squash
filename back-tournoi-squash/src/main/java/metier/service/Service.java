package metier.service;

import metier.model.*;
import dao.*;
import util.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

public class Service {

    protected JoueurDao joueurDao = new JoueurDao();
    protected UsersDao userDao = new UsersDao();
    protected SportDao sportDao = new SportDao();
    protected LevelDao levelDao = new LevelDao();
    protected MatchDao matchDao = new MatchDao(); 
    protected AdminDao adminDao = new AdminDao();
    protected Util util = new Util();

    /**
     * incrit un joueur dans la base de donnée
     * @param client Joueur à insrire
     * @return l'identifiant du joueur dans la BDD
     */
    public Long inscrireUtilisateur(Joueur client){
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            client.setMotDePasse(util.crypt(client.getMotDePasse(),0));
            joueurDao.creer(client);
            JpaUtil.validerTransaction();
            resultat = client.getId();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return resultat;
    }

    /**
     * Authentifie l'utilisateur correspondant aux informations
     * @param mail mail de l'utilisateur
     * @param motDePasse mot de passe entré
     * @return l'utilisateur authentifié ou null si erreur d'authentification
     */
    public Users authentifierUtilisateur(String mail, String motDePasse) {
        Users resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            // Recherche de l'Utilisateur
            Users user = userDao.chercherParMail(mail);
            if (user != null) {
                // Vérification du mot de passe
                if (motDePasse.equals(util.decrypt(user.getMotDePasse()))) {
                    resultat = user;
                }
            }
        } catch (Exception ex) {
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    
    /**
     * Change le mot de passe de l'utilisateur si les informations en paramètre sont bien celle du joueur
     * @param idUser - id du joueur
     * @param etc
     * @param nouveauMDP - mot de passe voulu
     * @return true si la modification est effectuée false sinon
     */
    public boolean modificationMDP(Date dateNaissance, String telephone, String mail, String nouveauMDP){
        boolean result = false;
        JpaUtil.creerContextePersistance();
        try {
            // Recherche de l'Utilisateur
            Users user = userDao.chercherParMail(mail);
            if (user != null) {
                // Vérification des informations
                if (user.getTelephone().equals(telephone) && user.getDateDeNaissance().equals(dateNaissance)) {
                    JpaUtil.ouvrirTransaction();
                    if(userDao.changeMDP(user.getId(), util.crypt(nouveauMDP,0)))
                        result = true;
                    JpaUtil.validerTransaction();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }

    public Users getUserInformations(Long idUser){
        Users resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            // Recherche de l'Utilisateur
            Users user = userDao.chercherParId(idUser);
            if (user != null) {
                resultat = user;
            }
        } catch (Exception ex) {
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    /**
     * Change l'etat pause du joueur
     * @param idUser - id du joueur
     * @param etc
     * @return true si la modification est effectuée false sinon
     */
    public boolean pauseCompte(Long idUser, Date dateNaissance, String telephone, String mail, String mdp){
        boolean resultat = false;
        JpaUtil.creerContextePersistance();
        try {
            // Recherche de l'Utilisateur
            Joueur user = joueurDao.chercherParId(idUser);
            if (user != null) {
                // Vérification des informations
                if (user.getTelephone().equals(telephone) && user.getEmail().equals(mail) && user.getDateDeNaissance().equals(dateNaissance) && util.crypt(mdp,0).equals(user.getMotDePasse())) {
                    JpaUtil.ouvrirTransaction();
                    miseEnPause(user);
                    resultat = true;
                    JpaUtil.validerTransaction();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    /**
     * Change l'etat pause du joueur
     * @param idUser - id du joueur
     * @return true si la modification est effectuée false sinon
     */
    public boolean pauseJoueur(Long idUser){
        boolean resultat = false;
        JpaUtil.creerContextePersistance();
        try {
            // Recherche de l'Utilisateur
            Joueur user = joueurDao.chercherParId(idUser);
            if (user != null) {
                JpaUtil.ouvrirTransaction();
                miseEnPause(user);
                resultat = true;
                JpaUtil.validerTransaction();
            }
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    public void miseEnPause(Joueur user){
        if(user.getPause() == 0){
            joueurDao.changePause(user.getId(), 1);
            matchDao.supprimeMatchActif(user.getId());
            List<Level> listeSport = levelDao.getAllLevel(user.getId());
            for(Level l : listeSport){
                List<Level> list  = levelDao.obtenirJoueurInferieur(l.getSport().getId(),levelDao.getLevel(user.getId(),l.getSport().getId()).getClassement());
                for(Level n : list)
                    levelDao.setClassement(n.getId(),n.getClassement()-1);
                levelDao.setClassement(l.getId(),-1L);
            }
            
        }else{
            joueurDao.changePause(user.getId(), 0);
            List<Level> list = levelDao.getAllLevel(user.getId());
            for(Level l : list)
                setClassement(l);
        }
    }

    /**
     * Modifie les informations de l'utilisateur si le mdp est bon
     * @param idUser - id de l'utilisateur
     * @param nom - nouveau non
     * @param etc
     * @param motDePasse - mot de passe de l'utilisateur à vérifier
     * @return true si modification, false sinon
     */
    public boolean modifierInformations(Long idUser, String nom, String prenom, Date dateNaissance, String telephone, String mail, String motDePasse) {
        boolean resultat = false;
        JpaUtil.creerContextePersistance();
        try {
            // Recherche de l'Utilisateur
            Users user = userDao.chercherParId(idUser);
            if (user != null) {
                // Vérification du mot de passe
                if (user.getMotDePasse().equals(util.crypt(motDePasse,0))) {
                    JpaUtil.ouvrirTransaction();
                    userDao.modifieInformations(idUser,nom,prenom,dateNaissance,telephone,mail);
                    JpaUtil.validerTransaction();
                    resultat = true;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    /**
     * Renvoi la liste des joueurs
     */
    public List<Joueur> afficheJoueurs(){
        List<Joueur> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = joueurDao.getListe();
        } catch (Exception ex) {
            System.out.println(ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    // Créer un sport
    public Long creerSport(Sport sport){
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            sportDao.creer(sport);
            JpaUtil.validerTransaction();
            resultat = sport.getId();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return resultat;
    }

    /**
     * Renvoi la liste des sports
     */
    public List<Sport> afficheSports(){
        List<Sport> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = sportDao.getListe();
        } catch (Exception ex) {
            System.out.println(ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    /**
     * Renvoi la liste des niveaux d'un sport
     */
    public List<String> getSportLevel(Long idSport){
        List<String> resultat = new ArrayList<String>();
        JpaUtil.creerContextePersistance();
        try {
            Sport sport = sportDao.chercherParId(idSport);
            String level = sport.getNiveaux();
            int index = level.indexOf(",");
            while(index != -1){
                resultat.add(level.substring(0,index));
                level = level.substring(index+1);
                index = level.indexOf(",");
            }
            resultat.add(level);
        } catch (Exception ex) {
            System.out.println(ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    /**
     * incrit un joueur à un sport
     * @return l'etat de l'inscription
     */
    public boolean inscriptionSport(Level level){
        boolean resultat = false;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            levelDao.creer(level);
            Long classement = setClassement(level);
            if(classement == 0)
                resultat = false;
            JpaUtil.validerTransaction();
            if(level.getId() != null)
                resultat = true;
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    /**
     * Créer le classement du joueur en fonction de son niveau et adapte les autres classements
     * Est util lors de la sortie d'une pause ou de l'inscription à un sport
     * @return le classement du joueur
     */
    public Long setClassement(Level level){
        Long resultat = 0L;
        // Récupère le niveau auquel il faut inscrire le joueur
        List<String> niveaux = new ArrayList<String>();
        Sport sport = sportDao.chercherParId(level.getSport().getId());
        String niv = sport.getNiveaux();
        int index = niv.indexOf(",");
        while(index != -1){
            niveaux.add(niv.substring(0,index));
            niv = niv.substring(index+1);
            index = niv.indexOf(",");
        }
        niveaux.add(niv);
        int cpt;
        for(cpt = 0; cpt < niveaux.size(); cpt++)
            if(niveaux.get(cpt).equals(level.getNiveau()))
                break;
        if(cpt != niveaux.size()-1)
            cpt++;
        niv = niveaux.get(cpt);
        // Récupère le classement du dernier joueur du niveau niv
        Long classement = levelDao.introduction(level.getSport().getId(),cpt,niveaux);
        //System.out.println("Joueur : "+level.getJoueur().getNom()+" Le niveau à inscrire est : "+niv+" ce qui correspond au classement "+(classement+1)+" - sport : "+level.getSport().getNom());
        resultat = classement+1L;
        // Déplace tous les joueurs sous ce classement d'un cran
        List<Level> joueurs = levelDao.obtenirJoueurInferieur(level.getSport().getId(),classement);
        for(Level l : joueurs){
            //System.out.println("On déplace le joueur classé "+l.getClassement()+" au classement "+(l.getClassement()+1)+" et de level "+l.getNiveau());
            levelDao.setClassement(l.getId(),l.getClassement()+1L);
        }
        // Incère le joueur dans le classement
        levelDao.setClassement(level.getId(),resultat);
        // Mets à jour son niveau
        levelDao.setNiveau(level.getId(),niv);
        /////
        return resultat;
    }

    /**
     * vérifie si un joueur est inscrit à un sport
     * @param idUser - id du joueur
     * @param idSport - id du sport
     * @return true si pratique le sport, false sinon
     */
    public boolean consulterSport(Long idUser, Long idSport){
        boolean resultat = false;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            Level result = levelDao.getLevel(idUser,idSport);
            if(result != null)
                resultat = true;
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    /**
     * créer un sport
     * @param match - match à créer
     * @return l'id du match
     */
    public Long creerMatch(Matchs match){
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            matchDao.creer(match);
            JpaUtil.validerTransaction();
            if(match.getId() != null)
                resultat = match.getId();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    /**
     * Retourne le match
     * @param idMatch - id du match à consulter
     * @return le match
     */
    public Matchs infoMatch(Long idMatch){
        Matchs resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            resultat = matchDao.getMatch(idMatch);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    /**
     * Retourne la liste des matchs du sport en attente du joueur passé en param
     */
    public List<Matchs> matchDemandes(Long idUser, Long idSport){
        List<Matchs> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            resultat = matchDao.demande(idUser,idSport);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    /**
     * Change l'état du match en param de EN_ATTENTE_Jx à EN_COURS
     */
    public boolean matchAccepte(Long idMatch){
        boolean resultat = false;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            resultat = matchDao.accepte(idMatch);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    /**
     * Retourne la liste des futurs matchs du sport du joueur passé en param
     */
    public List<Matchs> futurMatch(Long idUser, Long idSport){
        List<Matchs> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            resultat = matchDao.futur(idUser,idSport);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    /**
     * Modifie la date d'un match et modifie le statue d'attente du match
     */
    public boolean nouvelleDate(Long idMatch, Date date){
        boolean result = false;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            Matchs m = matchDao.getMatch(idMatch);
            matchDao.changeDate(idMatch,date);
            if(m.getEtat().equals("EN_ATTENTE_J1"))
                matchDao.changeEtat(idMatch, "EN_ATTENTE_J2");
            else if(m.getEtat().equals("EN_ATTENTE_J2"))
                matchDao.changeEtat(idMatch, "EN_ATTENTE_J1");
            else{
                System.out.println("Erreur nouvelleDate, état actuel : "+m.getEtat());
                JpaUtil.annulerTransaction();
                JpaUtil.fermerContextePersistance();
                return false;
            }
            result = true;
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }

    /**
     * Retourne la liste des joueurs d'un sport en fonction de leur classement
     */
    public List<Level> getClassement(Long idSport){
        List<Level> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            resultat = levelDao.getClassement(idSport);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    /**
     * Retourne la liste des joueurs défiable pour le joueur en param dans le sport en param
     */
    public List<Level> getDefiePossible(Long idUser, Long idSport){
        List<Level> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            resultat = levelDao.getDefiePossible(idSport,levelDao.getLevel(idUser,idSport).getClassement());
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    /**
     * Inscrit les scores, met à jour l'état du match et le classement
     * Regarde les niveaux et les changes si besoin
     */
    public boolean annoncerResultats(Long idMatch, Long idUser, int scoreV, int scoreP){
        boolean resultat = false;
        Matchs match = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            //////
            match = matchDao.getMatch(idMatch);
            Long classementVainqueur = levelDao.getLevel(idUser,match.getSport().getId()).getClassement();
            Joueur perdant = null;
            int vainqueur = 0;
            if(match.getJoueur1().getId().equals(idUser)){
                perdant = match.getJoueur2();
                vainqueur = 1;
            }else{
                perdant = match.getJoueur1();
                vainqueur = 2;
            }
            //entrer les scores et mettre à jour l'etat du match
            matchDao.resultats(idMatch,vainqueur,scoreV,scoreP);
            //mettre à jour le classement
            Long classementPerdant = levelDao.getLevel(perdant.getId(),match.getSport().getId()).getClassement();
                //si vainqueur est moins classé
            if(classementVainqueur > classementPerdant){
                //Joueurs entre ++
                List<Level> list = levelDao.obtenirJoueurEntre(match.getSport().getId(),classementPerdant,classementVainqueur);
                for(Level l : list)
                    levelDao.setClassement(l.getId(),l.getClassement()+1);
                //classement vainqueur = classement perdant
                levelDao.setClassement(levelDao.getLevel(idUser,match.getSport().getId()).getId(),classementPerdant);
                //classement perdant ++
                levelDao.setClassement(levelDao.getLevel(perdant.getId(),match.getSport().getId()).getId(),classementPerdant+1);
            }
            resultat = true;
            //////
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        if(match != null)
            majNiveau(match.getSport());
        return resultat;
    }

    /**
    * Effectue des modifications sur les niveaux si besoin
    * Si un joueur est mieux classé que 3 joueurs de niveau inférieur, il monte d'un niveau et on re-check
    * Idem pour descendre de niveau
    */
    public void majNiveau(Sport sport){
        boolean change = true;
        int cpt = 0;
        while(change && cpt < 5){
            JpaUtil.creerContextePersistance();
            try {
                JpaUtil.ouvrirTransaction();
                change = false;
                cpt++;
                List<Level> liste = levelDao.obtenirTous(sport.getId());
                List<String> niveaux = new ArrayList<String>();
                String level = sport.getNiveaux();
                int index = level.indexOf(",");
                while(index != -1){
                    niveaux.add(level.substring(0,index));
                    level = level.substring(index+1);
                    index = level.indexOf(",");
                }
                niveaux.add(level);
                for(Level l : liste){
                    int i = 0;
                    for(i = 0; i < niveaux.size(); i++)
                        if(niveaux.get(i).equals(l.getNiveau()))
                            break;
                    int sumInf = 0;
                    int sumSup = 0;
                    for(int j = i-1; j >= 0; j--)
                        sumSup += levelDao.obtenirNombreJoueurSup(l,niveaux.get(j)); 
                    for(int j = i+1; j < niveaux.size(); j++)    
                        sumInf += levelDao.obtenirNombreJoueurInf(l,niveaux.get(j));
                    if(sumInf > 3 && sumSup <= 3){
                        //On decend le joueur d'un level
                        levelDao.modifieNiveau(l.getJoueur().getId(),l.getSport().getId(),niveaux.get(i+1));
                        change = true;
                    }else if(sumInf <= 3 && sumSup > 3){
                        //On monte le joueur d'un level
                        levelDao.modifieNiveau(l.getJoueur().getId(),l.getSport().getId(),niveaux.get(i-1));
                        change = true; 
                    }
                }
                JpaUtil.validerTransaction();
            } catch (Exception ex) {
                System.out.println(ex);
                change = false;
                JpaUtil.annulerTransaction();
            } finally {
                JpaUtil.fermerContextePersistance();
            }
        }
    }

    /**
     * Retourne la liste des matchs terminés d'un sport passé en param
     */
    public List<Matchs> afficheMatchs(Long idSport){
        List<Matchs> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            resultat = matchDao.afficheMatchs(idSport);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    /**
     * Retourne la liste des matchs terminés d'un joueur d'un sport passé en param
     */
    public List<Matchs> getHistorique(Long idUser, Long idSport){
        List<Matchs> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            resultat = matchDao.getHistorique(idUser,idSport);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    /**
     * Modifie le niveau d'un joueur dans un sport
     */
    public boolean modifieNiveau(Long idLevel, String niveau){
        boolean result = false;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            Level l = levelDao.chercherParId(idLevel);
            if(levelDao.modifieNiveau(l.getJoueur().getId(),l.getSport().getId(),niveau))
                result = true;
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }

    /**
     * Supprime les matchs, les levels ainsi que le joueur lui même
     */
    public boolean supprimerJoueur(Long idUser){
        boolean result = false;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            List<Level> listeSport = levelDao.getAllLevel(idUser);
            for(Level l : listeSport){
                List<Level> list  = levelDao.obtenirJoueurInferieur(l.getSport().getId(),levelDao.getLevel(idUser,l.getSport().getId()).getClassement());
                for(Level n : list)
                    levelDao.setClassement(n.getId(),n.getClassement()-1);
                levelDao.setClassement(l.getId(),-1L);
            }
            matchDao.supprimerMatch(idUser);
            levelDao.supprimerLevel(idUser);
            joueurDao.supprimerJoueur(idUser);
            result = true;
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }

    /**
     * Supprime les matchs, les levels ainsi que le joueur lui même si les informations en param sont correctes
     */
    public boolean supprimerCompte(Long idUser, Date date, String telephone, String mail, String mdp){
        boolean result = false;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            Joueur joueur = joueurDao.chercherParId(idUser);
            if(date.equals(joueur.getDateDeNaissance()) && telephone.equals(joueur.getTelephone()) && mail.equals(joueur.getEmail()) && util.crypt(mdp,0).equals(joueur.getMotDePasse())){
                List<Level> listeSport = levelDao.getAllLevel(idUser);
                for(Level l : listeSport){
                    List<Level> list  = levelDao.obtenirJoueurInferieur(l.getSport().getId(),levelDao.getLevel(idUser,l.getSport().getId()).getClassement());
                    for(Level n : list)
                        levelDao.setClassement(n.getId(),n.getClassement()-1);
                    levelDao.setClassement(l.getId(),-1L);
                }
                matchDao.supprimerMatch(idUser);
                levelDao.supprimerLevel(idUser);
                joueurDao.supprimerJoueur(idUser);
                result = true;
            }
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }

    /**
     * Regarde les matchs et les met à jour si besoin
     * Si un match est en attente depuis plus de 2 semaines, le joueur en attente est déclaré forfait
     */
    public boolean majDate(){
        boolean result = false;
        JpaUtil.creerContextePersistance();
        List<Sport> list = new ArrayList<Sport>();
        try {
            JpaUtil.ouvrirTransaction(); 
            List<Matchs> liste = matchDao.obtenirMatchEnAttente();
            for(Matchs m : liste){
                //Date dateMatch = m.getDateMatch();
                Date date = new Date();
                Date dateCreation = m.getDateCreation();
		Calendar cal = Calendar.getInstance();
                cal.setTime(dateCreation);
                cal.add(Calendar.DATE, 15);
                dateCreation = cal.getTime();
                if(dateCreation.before(date)){ 
                    Joueur vainqueur = null;
                    Joueur perdant = null;
                    if(m.getEtat().equals("EN_ATTENTE_J1")){
                        vainqueur = m.getJoueur2();
                        perdant = m.getJoueur1();
                    }else if(m.getEtat().equals("EN_ATTENTE_J2")){
                        vainqueur = m.getJoueur1();
                        perdant = m.getJoueur2();
                    }else{
                        System.out.println("Erreur majDate, match non en_attente");
                    }
                    Long classementVainqueur = levelDao.getLevel(vainqueur.getId(),m.getSport().getId()).getClassement();
                    Long classementPerdant = levelDao.getLevel(perdant.getId(),m.getSport().getId()).getClassement();
                    if(classementVainqueur > classementPerdant){
                        List<Level> li = levelDao.obtenirJoueurEntre(m.getSport().getId(),classementPerdant,classementVainqueur);
                        for(Level l : li)
                            levelDao.setClassement(l.getId(),l.getClassement()+1);
                        //classement vainqueur = classement perdant
                        levelDao.setClassement(levelDao.getLevel(vainqueur.getId(),m.getSport().getId()).getId(),classementPerdant);
                        //classement perdant ++
                        levelDao.setClassement(levelDao.getLevel(perdant.getId(),m.getSport().getId()).getId(),classementPerdant+1);
                    }
                    matchDao.changeEtat(m.getId(),"FORFAIT");
                    boolean b = false;
                    for(Sport s : list){
                        if(s.equals(m.getSport())){
                            b = true;
                            break;
                        }
                    }
                    if(b)
                        list.add(m.getSport());
                }
            }
            result = true;
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        for(Sport s : list)
            majNiveau(s);
        return result;
    }

    public Sport getSport(Long idSport){
        Sport sport = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            sport = sportDao.chercherParId(idSport);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return sport;
    }

    public Joueur getJoueur(Long idUser){
        Joueur joueur = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            joueur = joueurDao.chercherParId(idUser);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return joueur;
    }

    public Level getLevel(Long idUser, Long idSport){
        Level level = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            level = levelDao.getLevel(idUser,idSport);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return level;
    }

    public List<Level> getLevels(Long idUser){
        List<Level> levels = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            levels = levelDao.getAllLevel(idUser);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return levels;
    }

     /**
     * Permet à l'admin d'accepter le joueur, met à jour la variable "autorisation"
     */
    public boolean accepteJoueur(Long idUser){
        boolean result = false;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            joueurDao.accepteJoueur(idUser);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }

    public List<Admin> getAdmins(){
        List<Admin> admins = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();  
            admins = adminDao.getAllAdmin();
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return admins;
    }

}
