package metier.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-17T22:02:14")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, Date> dateDeNaissance;
    public static volatile SingularAttribute<Users, String> motDePasse;
    public static volatile SingularAttribute<Users, String> telephone;
    public static volatile SingularAttribute<Users, Long> id;
    public static volatile SingularAttribute<Users, String> nom;
    public static volatile SingularAttribute<Users, String> prenom;
    public static volatile SingularAttribute<Users, String> email;

}