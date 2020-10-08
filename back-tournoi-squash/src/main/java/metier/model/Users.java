package metier.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(unique = true)
    protected String email;
    protected String nom;
    protected String prenom;
    protected String telephone;
    @Temporal(TemporalType.DATE)
    protected Date dateDeNaissance;
    protected String motDePasse;
    
    public Long getId(){return id;}
    public String getEmail(){return email;}
    public void setEmail(String s){email=s;}
    public String getNom(){return nom;}
    public void setNom(String n){nom=n;}
    public String getPrenom(){return prenom;}
    public void setPrenom(String p){prenom=p;}
    public String getTelephone(){return telephone;}
    public void setTelephone(String t){telephone=t;}
    public Date getDateDeNaissance(){return dateDeNaissance;}
    public void setDateDeNaissance(Date n){dateDeNaissance=n;}
    public String getMotDePasse(){return motDePasse;}
    public void setMotDePasse(String mdp){motDePasse=mdp;}

    public String toString(){
        return "{ id : "+id+" , email : "+email+" , nom : "+nom+" , prenom : "+prenom+" , telephone : "+telephone+" , dateDeNaissance : "+dateDeNaissance+" , motDePasse : "+motDePasse+" }";
    }

    protected Users() {}

    public Users(String email, String nom, String prenom, String tel, Date date, String motDePasse) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = tel;
        this.dateDeNaissance = date;
        this.motDePasse = motDePasse;
    }

}
