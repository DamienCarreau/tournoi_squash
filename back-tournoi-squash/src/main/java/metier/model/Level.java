package metier.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
public class Level implements Serializable {
      
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String niveau;
    protected Long classement;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    Joueur joueur;

    @ManyToOne
    @JoinColumn(name="sport_id")
    Sport sport;

    public String getNiveau(){return niveau;}
    public void setNiveau(String n){niveau = n;}
    public Long getClassement(){return classement;}
    public void setClassement(Long c){classement=c;}
    public Long getId(){return id;}
    public Sport getSport(){return sport;}
    public Joueur getJoueur(){return joueur;}

    protected Level(){}

    public Level(Joueur joueur, Sport sport, String niveau){
        this.joueur = joueur;
        this.sport = sport;
        this.niveau = niveau;
        this.classement = null;
    }

    public String toString(){
        return "{ niveau : "+niveau+" , classement : "+classement+" }";
    }

}