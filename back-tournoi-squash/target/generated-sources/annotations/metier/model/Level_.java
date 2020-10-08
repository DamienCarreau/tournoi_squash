package metier.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.model.Joueur;
import metier.model.Sport;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-17T22:02:14")
@StaticMetamodel(Level.class)
public class Level_ { 

    public static volatile SingularAttribute<Level, Joueur> joueur;
    public static volatile SingularAttribute<Level, Long> classement;
    public static volatile SingularAttribute<Level, Long> id;
    public static volatile SingularAttribute<Level, String> niveau;
    public static volatile SingularAttribute<Level, Sport> sport;

}