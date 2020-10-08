package metier.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.model.Level;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-17T22:02:14")
@StaticMetamodel(Sport.class)
public class Sport_ { 

    public static volatile SingularAttribute<Sport, String> niveaux;
    public static volatile ListAttribute<Sport, Level> classement;
    public static volatile SingularAttribute<Sport, Long> id;
    public static volatile SingularAttribute<Sport, String> nom;

}