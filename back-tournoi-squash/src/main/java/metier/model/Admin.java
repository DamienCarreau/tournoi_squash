package metier.model;

import java.util.Date;
import javax.persistence.Entity;

@Entity
public class Admin extends Users {
    protected Admin() {}

    public Admin(String email, String nom, String prenom, String tel, Date date, String motDePasse) {
        super(email,nom,prenom,tel,date,motDePasse);
    }

    public String toString(){
        return "Admin[] : "+super.toString();
    }
}

