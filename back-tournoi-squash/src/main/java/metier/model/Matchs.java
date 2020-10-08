package metier.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Matchs implements Serializable {
    
    public enum etat {
        EN_ATTENTE_J1, EN_ATTENTE_J2, EN_COURS, TERMINEE, FORFAIT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Temporal(TemporalType.DATE)
    protected Date dateMatch;
    @Temporal(TemporalType.DATE)
    protected Date dateCreation;
    protected String etat;
    protected int scoreJ1;
    protected int scoreJ2;

    @ManyToOne
    private Sport sport;

    @ManyToOne
    private Joueur joueur1;

    @ManyToOne
    private Joueur joueur2;

    public Long getId(){return id;}
    public String getEtat(){return etat;}
    public void setEtat(String s){etat=s;}
    public Date getDateMatch(){return dateMatch;}
    public void setDateMatch(Date n){dateMatch=n;}
    public Date getDateCreation(){return dateCreation;}
    public void setDateCreation(Date n){dateCreation=n;}
    public int getScoreJ1(){return scoreJ1;}
    public void setScoreJ1(int s){scoreJ1=s;}
    public int getScoreJ2(){return scoreJ2;}
    public void setScoreJ2(int s){scoreJ2=s;}
    public Sport getSport(){return sport;}
    public Joueur getJoueur1(){return joueur1;}
    public Joueur getJoueur2(){return joueur2;}

    protected Matchs(){}

    public Matchs(Date d1, Date d2, String e, int s1, int s2){
        dateMatch = d1;
        dateCreation = d2;
        etat = e;
        scoreJ1 = s1;
        scoreJ2 = s2;
    }

    public Matchs(Sport s, Joueur j1, Joueur j2, Date date){
        sport = s;
        joueur1 = j1;
        joueur2 = j2;
        dateMatch = date;
        dateCreation = new Date();
        etat = "EN_ATTENTE_J2";
    }

    public String toString(){
        return "{ id : "+id+" , dateMatch : "+dateMatch+" , dateCreation : "+dateCreation+" , etat : "+etat+" , scoreJ1 : "+scoreJ1+" , scoreJ2 : "+scoreJ2+" }";
    }

}
