package metier.model;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Joueur extends Users {

    protected int pause;
    protected int autorisation;
    protected char sexe;

    @OneToMany(mappedBy = "Joueur")
    protected List<Level> niveaux;

    public int getPause(){return pause;}
    public int getAutorisation(){return autorisation;}
    public char getSexe(){return sexe;}

    public Joueur() {}

    public Joueur(String email, String nom, String prenom, String tel, Date date, String motDePasse, char sexe) {
        super(email,nom,prenom,tel,date,motDePasse);
        pause = 0;
        autorisation = 0;
        this.sexe = sexe;
    }

    public String toString(){
        return "Joueur[] : "+super.toString()+" pause : "+pause+" autorisation : "+autorisation+" sexe : "+sexe;
    }
}
