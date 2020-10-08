package metier.model;

import java.util.List;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Sport implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nom;
    protected String niveaux;

    @OneToMany(mappedBy = "Sport")
    protected List<Level> classement;

    public Long getId(){return id;}
    public String getNom(){return nom;}
    public void setNom(String n){nom=n;}
    public String getNiveaux(){return niveaux;}

    protected Sport(){}

    public Sport(String nom, String niv){
        this.nom = nom;
        if(niv.substring(niv.length()-1).indexOf(",") != -1)
            this.niveaux = niv.substring(0,niv.length()-1);
        else
            this.niveaux = niv;
    }

    public String toString(){
        return "{ id : "+id+" , nom : "+nom+" }";
    }

}
