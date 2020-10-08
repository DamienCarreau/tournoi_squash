package metier.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.model.Joueur;
import metier.model.Sport;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-17T22:02:14")
@StaticMetamodel(Matchs.class)
public class Matchs_ { 

    public static volatile SingularAttribute<Matchs, Joueur> joueur1;
    public static volatile SingularAttribute<Matchs, Date> dateCreation;
    public static volatile SingularAttribute<Matchs, Date> dateMatch;
    public static volatile SingularAttribute<Matchs, Integer> scoreJ1;
    public static volatile SingularAttribute<Matchs, Integer> scoreJ2;
    public static volatile SingularAttribute<Matchs, Long> id;
    public static volatile SingularAttribute<Matchs, String> etat;
    public static volatile SingularAttribute<Matchs, Sport> sport;
    public static volatile SingularAttribute<Matchs, Joueur> joueur2;

}