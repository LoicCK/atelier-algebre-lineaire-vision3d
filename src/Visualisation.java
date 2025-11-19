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

    /**

     * Crée les sommets d'un cube centré sur l'origine et les met dans un tableau.

     * @return Tableau de Vecteur

     */

    private static Vecteur[] creerSommets() {

        Vecteur p1 = new Vecteur(3);   // sommet arrière gauche bas

        p1.setCoordonnee(0, -1);

        p1.setCoordonnee(1, -1);

        p1.setCoordonnee(2, -1);

        Vecteur p2 = new Vecteur(3);   // sommet arrière gauche haut

        p2.setCoordonnee(0, -1);

        p2.setCoordonnee(1, 1);

        p2.setCoordonnee(2, -1);

        Vecteur p3 = new Vecteur(3);   // sommet arrière droite haut

        p3.setCoordonnee(0, 1);

        p3.setCoordonnee(1, 1);

        p3.setCoordonnee(2, -1);

        Vecteur p4 = new Vecteur(3);   // sommet arrière droite bas

        p4.setCoordonnee(0, 1);

        p4.setCoordonnee(1, -1);

        p4.setCoordonnee(2, -1);

        Vecteur p5 = new Vecteur(3);   // sommet avant gauche bas

        p5.setCoordonnee(0, -1);

        p5.setCoordonnee(1, -1);

        p5.setCoordonnee(2, 1);

        Vecteur p6 = new Vecteur(3);   // sommet avant gauche haut

        p6.setCoordonnee(0, -1);

        p6.setCoordonnee(1, 1);

        p6.setCoordonnee(2, 1);

        Vecteur p7 = new Vecteur(3);   // sommet avant droite haut

        p7.setCoordonnee(0, 1);

        p7.setCoordonnee(1, 1);

        p7.setCoordonnee(2, 1);

        Vecteur p8 = new Vecteur(3);   // sommet avant droite bas

        p8.setCoordonnee(0, 1);

        p8.setCoordonnee(1, -1);

        p8.setCoordonnee(2, 1);



        return new Vecteur[]{p1, p2, p3, p4, p5, p6, p7, p8};

    }

    /**

     * Dessine sur le Plan les arêtes du cube dont les sommets sont dans le tableau de Vecteur.

     * @param pPlan~: plan où effectuer le dessin

     * @param pSommets~: sommets du cube

     */

    private static void dessinerCube(final Plan pPlan, final Vecteur[] pSommets) {

        pPlan.effacer();



        // Face arrière

        pPlan.dessinerSegmentEn2d(pSommets[0], pSommets[1]);

        pPlan.dessinerSegmentEn2d(pSommets[1], pSommets[2]);

        pPlan.dessinerSegmentEn2d(pSommets[2], pSommets[3]);

        pPlan.dessinerSegmentEn2d(pSommets[3], pSommets[0]);



        // Face avant

        pPlan.dessinerSegmentEn2d(pSommets[4], pSommets[5]);

        pPlan.dessinerSegmentEn2d(pSommets[5], pSommets[6]);

        pPlan.dessinerSegmentEn2d(pSommets[6], pSommets[7]);

        pPlan.dessinerSegmentEn2d(pSommets[7], pSommets[4]);



        // Relier la face avant à la face arrière

        pPlan.dessinerSegmentEn2d(pSommets[0], pSommets[4]);

        pPlan.dessinerSegmentEn2d(pSommets[1], pSommets[5]);

        pPlan.dessinerSegmentEn2d(pSommets[2], pSommets[6]);

        pPlan.dessinerSegmentEn2d(pSommets[3], pSommets[7]);

    }

    /**
     * Crée un plan, génère les sommets d'un cube et l'affiche sans transformation.
     */
    public static void afficherCubeBase(){
        Plan p = new Plan();
        Vecteur[] sommets = creerSommets();
        dessinerCube(p, sommets);
    }

    /**

     * Interface pour le traitement des vecteurs.

     */

    public interface FunctionDeTraitementVecteur {

        Vecteur exécuter(final Vecteur pVecteur);

    }

    /**

     * Applique une transformation définie par une implémentation de FunctionDeTraitementVecteur

     * à chaque vecteur d'un tableau.

     *

     * @param pFTV une implémentation de FunctionDeTraitementVecteur

     * @param pVecteurs le tableau de vecteurs à transformer

     * @return un nouveau tableau contenant les vecteurs transformés

     */

    public static Vecteur[] appliquer(FunctionDeTraitementVecteur pFTV, Vecteur[] pVecteurs) {

        Vecteur[] result = new Vecteur[pVecteurs.length];

        for (int i = 0; i < pVecteurs.length; i++) {

            result[i] = pFTV.exécuter(pVecteurs[i]);

        }

        return result;

    }

    /**
    * Affiche un cube après lui avoir appliqué une rotation autour de l'axe Ox.
    */
    public static void afficherCubeRotation(){
        Plan p = new Plan();
        Vecteur[] sommets = creerSommets();
        Matrice matriceRotationX = new Matrice(3, 3);
        matriceRotationX.setRotation3dOx(Math.PI / 4);
        sommets = appliquer(matriceRotationX::multiplicationVectorielle, sommets);
        dessinerCube(p, sommets);
    }

    /**
     * Affiche un cube après lui avoir appliqué une homothétie (changement d'échelle).
     */
    public static void afficherCubeHomothetie(){
        Plan p = new Plan();
        Vecteur[] sommets = creerSommets();
        Matrice matriceHomothetie = new Matrice(3, 3);
        matriceHomothetie.setHomothetie(2.0);
        sommets = appliquer(matriceHomothetie::multiplicationVectorielle, sommets);
        dessinerCube(p, sommets);
    }

    /**
     * Affiche un cube après lui avoir appliqué une rotation composée (autour de plusieurs axes).
     */
    public static void afficherCubeRotationComposee(){
        Plan p = new Plan();
        Vecteur[] sommets = creerSommets();
        Matrice matRotComp = Matrice.getRotation(Math.PI/4.0, Math.PI/4.0, Math.PI/4.0);
        sommets = appliquer(matRotComp::multiplicationVectorielle, sommets);
        dessinerCube(p, sommets);
    }

    /**
     * Affiche une animation du cube tournant sur lui-même indéfiniment.
     * Utilise des rotations incrémentales et une pause (Thread.sleep) entre chaque image.
     */
    public static void afficherCubeAnime(){
        Plan p = new Plan();
        Vecteur[] sommets = creerSommets();
        Matrice matRotComp = Matrice.getRotation(Math.PI/60.0, Math.PI/60.0, Math.PI/60.0);
        while (true){
            sommets = appliquer(matRotComp::multiplicationVectorielle, sommets);
            dessinerCube(p, sommets);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        }
    }

    /**
     * Affiche un cube après une rotation suivie d'une translation.
     * Utilise les coordonnées homogènes pour la translation.
     */
    public static void afficherCubeRotationTranslation() {
        Plan p = new Plan();
        Vecteur[] sommets = creerSommets();

        Matrice rot = Matrice.getRotation(Math.PI / 4, Math.PI / 4, Math.PI / 4);
        sommets = appliquer(rot::multiplicationVectorielle, sommets);

        sommets = appliquer(Vecteur::versHomogene, sommets);

        Matrice trans = new Matrice(4, 4);
        Vecteur t = new Vecteur(3);
        t.setCoordonnee(0, 2);
        t.setCoordonnee(1, 2);
        t.setCoordonnee(2, 0);
        trans.setTranslationHomogene3d(t);

        sommets = appliquer(trans::multiplicationVectorielle, sommets);

        sommets = appliquer(Vecteur::depuisHomogene, sommets);

        dessinerCube(p, sommets);
    }

    /**
     * Affiche un cube après rotation et translation spécifique sur l'axe Oz.
     * Permet d'observer les effets de la projection orthogonale.
     */
    public static void afficherCubeRotationTranslationOz() {
        Plan p = new Plan();
        Vecteur[] sommets = creerSommets();

        Matrice rot = Matrice.getRotation(Math.PI / 4, Math.PI / 4, Math.PI / 4);
        sommets = appliquer(rot::multiplicationVectorielle, sommets);

        sommets = appliquer(Vecteur::versHomogene, sommets);

        Matrice trans = new Matrice(4, 4);
        Vecteur t = new Vecteur(3);
        t.setCoordonnee(2, 4);
        trans.setTranslationHomogene3d(t);

        sommets = appliquer(trans::multiplicationVectorielle, sommets);

        sommets = appliquer(Vecteur::depuisHomogene, sommets);

        dessinerCube(p, sommets);
    }

    /**
     * Compare visuellement l'impact de l'ordre des transformations.
     * Affiche deux cubes :
     * 1. Homothétie puis Translation.
     * 2. Translation puis Homothétie.
     */
    public static void afficherCubeRotationHomothetieTranslation() {
        Plan p = new Plan();

        // Cas 1: Homothétie puis Translation
        Vecteur[] s1 = creerSommets();
        Matrice rot = Matrice.getRotation(Math.PI/6, Math.PI/6, 0);
        s1 = appliquer(rot::multiplicationVectorielle, s1);

        s1 = appliquer(Vecteur::versHomogene, s1);

        Matrice hom = new Matrice(4, 4);
        hom.setHomothetieHomogene3d(0.5);

        Matrice trans = new Matrice(4, 4);
        Vecteur t = new Vecteur(3);
        t.setCoordonnee(0, -3);
        trans.setTranslationHomogene3d(t);

        s1 = appliquer(hom::multiplicationVectorielle, s1);
        s1 = appliquer(trans::multiplicationVectorielle, s1);

        s1 = appliquer(Vecteur::depuisHomogene, s1);
        dessinerCube(p, s1);

        // Cas 2: Translation puis Homothétie
        Vecteur[] s2 = creerSommets();
        s2 = appliquer(rot::multiplicationVectorielle, s2);

        s2 = appliquer(Vecteur::versHomogene, s2);

        Vecteur t2 = new Vecteur(3);
        t2.setCoordonnee(0, 3);
        trans.setTranslationHomogene3d(t2);

        s2 = appliquer(trans::multiplicationVectorielle, s2);
        s2 = appliquer(hom::multiplicationVectorielle, s2);

        s2 = appliquer(Vecteur::depuisHomogene, s2);
        dessinerCube(p, s2);
    }

    /**
     * Réalise une animation complexe en trois phases :
     * 1. Translation progressive.
     * 2. Homothétie progressive.
     * 3. Translation retour progressive.
     */
    public static void afficherCubeAnimationTranslationHomothetieRetour() {
        Plan p = new Plan();
        Vecteur[] sommets = creerSommets();

        sommets = appliquer(Vecteur::versHomogene, sommets);

        Matrice trans = new Matrice(4, 4);
        Vecteur vTrans = new Vecteur(3);
        vTrans.setCoordonnee(0, 0.1);
        trans.setTranslationHomogene3d(vTrans);

        Matrice hom = new Matrice(4, 4);
        hom.setHomothetieHomogene3d(1.02);

        Matrice transRetour = new Matrice(4, 4);
        Vecteur vTransRetour = new Vecteur(3);
        vTransRetour.setCoordonnee(0, -0.27);
        transRetour.setTranslationHomogene3d(vTransRetour);

        for (int i = 0; i < 150; i++) {
            p.effacer();

            if (i < 50) {
                sommets = appliquer(trans::multiplicationVectorielle, sommets);
            } else if (i < 100) {
                sommets = appliquer(hom::multiplicationVectorielle, sommets);
            } else {
                sommets = appliquer(transRetour::multiplicationVectorielle, sommets);
            }

            Vecteur[] aDessiner = appliquer(Vecteur::depuisHomogene, sommets);
            dessinerCube(p, aDessiner);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        }
    }

    public static void main(String[] args){
        afficherCubeAnimationTranslationHomothetieRetour();
    }
}