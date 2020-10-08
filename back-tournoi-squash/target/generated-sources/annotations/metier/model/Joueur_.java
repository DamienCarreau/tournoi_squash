package metier.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.model.Level;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-17T22:02:14")
@StaticMetamodel(Joueur.class)
public class Joueur_ extends Users_ {

    public static volatile ListAttribute<Joueur, Level> niveaux;
    public static volatile SingularAttribute<Joueur, Character> sexe;
    public static volatile SingularAttribute<Joueur, Integer> pause;
    public static volatile SingularAttribute<Joueur, Integer> autorisation;

}