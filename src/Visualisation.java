/**
 * Classe utilitaire (non instantiable) fournissant des méthodes statiques
 * pour dessiner des formes géométriques (droites, cercles) dans une fenêtre Plan.
 * Utilise les transformations linéaires (matrices) pour générer les points des formes.
 */
public class Visualisation {

    /**
     * Constructeur privé pour empêcher l'instanciation de cette classe utilitaire.
     */
    private Visualisation() {
    }

    /**
     * Trace une droite 2D dans une nouvelle fenêtre Plan.
     * La droite passe par l'origine (0,0) et suit la direction donnée par le vecteur.
     * Le tracé est effectué en dessinant de nombreux points à l'aide d'homothéties
     * successives appliquées au vecteur direction.
     *
     * @param pDirection Un Vecteur (de dimension 2) indiquant la direction de la droite.
     */
    public static void tracerDroite(final Vecteur pDirection){
        Plan p = new Plan();
        double d = Math.sqrt(6*6*2);
        double kmax = d/pDirection.norme();
        Matrice matTransform = new Matrice(2,2);
        Vecteur pointATracer = new Vecteur(2);

        for (double vAlpha = -1.0; vAlpha <= 1.0 + 1E-15; vAlpha += 0.01){
            double k = vAlpha*kmax;
            matTransform.setHomothetie(k);
            pointATracer = matTransform.multiplicationVectorielle(pDirection);
            p.dessinerPointEn2d(pointATracer);
        }
    }

    /**
     * Trace un cercle 2D dans une nouvelle fenêtre Plan.
     * Le cercle est centré à l'origine (0,0) et a le rayon spécifié.
     * Le tracé est effectué en partant d'un point initial (rayon, 0) et
     * en appliquant de nombreuses petites rotations successives pour générer les
     * points du périmètre.
     *
     * @param pRayon Le rayon du cercle à dessiner.
     */
    public static void tracerCercle(final double pRayon){
        Plan p = new Plan();
        Vecteur point = new Vecteur(2);
        point.setCoordonnee(0,pRayon);
        point.setCoordonnee(1,0);
        p.dessinerPointEn2d(point);

        double petitAngle = (2.0 * Math.PI) / 1000.0;
        Matrice matRotation = new Matrice(2, 2);
        matRotation.setRotation2d(petitAngle);

        for (int i = 0; i < 1000; i++){
            point = matRotation.multiplicationVectorielle(point);
            p.dessinerPointEn2d(point);
        }
    }
}