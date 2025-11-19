/**
 * Représente une matrice de dimension M x N (M lignes, N colonnes).
 * Fournit des opérations d'algèbre linéaire (multiplication vectorielle)
 * et des méthodes pour générer des matrices d'applications linéaires remarquables
 * (identité, homothétie, rotation, etc.).
 */
public class Matrice {
    /**
     * Stockage des coefficients de la matrice sous forme d'un tableau 2D (lignes, puis colonnes).
     * Utilise des primitifs 'double' pour garantir une initialisation à 0.0 (matrice nulle).
     */
    double[][] tab;

    /**
     * Construit une matrice de M lignes (pm) et N colonnes (pn).
     * La matrice est initialisée avec des zéros (matrice nulle).
     *
     * @param pm Le nombre de lignes.
     * @param pn Le nombre de colonnes.
     */
    public Matrice(final int pm, final int pn){
        tab = new double[pm][pn];
    }

    /**
     * Retourne le coefficient à la ligne pi et à la colonne pj.
     *
     * @param pi L'indice de la ligne (0-based).
     * @param pj L'indice de la colonne (0-based).
     * @return La valeur du coefficient.
     */
    public double getCoefficient(final int pi, final int pj){
        return tab[pi][pj];
    }

    /**
     * Retourne le nombre de colonnes (N) de la matrice.
     *
     * @return Le nombre de colonnes.
     */
    public int getNbColonnes(){
        return tab[0].length;
    }

    /**
     * Retourne le nombre de lignes (M) de la matrice.
     *
     * @return Le nombre de lignes.
     */
    public int getNbLignes(){
        return tab.length;
    }

    /**
     * Modifie le coefficient à la ligne pi et à la colonne pj.
     * L'opération modifie la matrice courante.
     *
     * @param pi L'indice de la ligne (0-based).
     * @param pj L'indice de la colonne (0-based).
     * @param pCoefficient La nouvelle valeur à assigner.
     */
    public void setCoefficient(final int pi, final int pj, final double pCoefficient){
        tab[pi][pj] = pCoefficient;
    }

    /**
     * Retourne un nouveau vecteur correspondant à la ligne pi de la matrice.
     *
     * @param pi L'indice de la ligne à extraire (0-based).
     * @return Un nouveau Vecteur contenant les coefficients de la ligne pi.
     */
    public Vecteur getVecteurLigne(final int pi){
        double[] valeurs = tab[pi];
        Vecteur v = new Vecteur(valeurs.length);
        for (int k = 0; k < valeurs.length; k++)
            v.setCoordonnee(k, valeurs[k]);
        return v;
    }

    /**
     * Calcule le produit de cette matrice (M) par un vecteur (v) (calcul M * v).
     * Le nombre de colonnes de la matrice doit être égal à la dimension du vecteur.
     *
     * @param pv Le vecteur (v) à multiplier (doit être un vecteur colonne).
     * @return Un nouveau Vecteur résultat de la multiplication (de dimension M, le nombre de lignes de la matrice),
     * ou null si les dimensions sont incompatibles.
     */
    public Vecteur multiplicationVectorielle(Vecteur pv){
        if (pv.getDimension()!=getNbColonnes()) return null;

        Vecteur v = new Vecteur(getNbLignes());
        for (int i = 0; i < getNbLignes(); i++) {
            v.setCoordonnee(i, getVecteurLigne(i).produitScalaire(pv));
        }
        return v;
    }

    /**
     * Configure la matrice en matrice identité (en appelant setHomothetie(1.0)).
     * L'opération modifie la matrice courante.
     * Ne fait rien si la matrice n'est pas carrée.
     */
    public void setIdentite(){
        setHomothetie(1.0);
    }

    /**
     * Configure la matrice en matrice d'homothétie de facteur k (k sur la diagonale, 0 ailleurs).
     * L'opération modifie la matrice courante.
     * Ne fait rien si la matrice n'est pas carrée.
     *
     * @param k Le facteur d'homothétie.
     */
    public void setHomothetie(double k){
        if (getNbLignes()!=getNbColonnes()) return;
        for (int i = 0; i < getNbLignes(); i++){
            for (int j = 0; j < getNbLignes(); j++){
                if (i == j) tab[i][j] = k;
                else tab[i][j] = 0.0;
            }
        }
    }

    /**
     * Configure la matrice en matrice de symétrie centrale (en appelant setHomothetie(-1.0)).
     * L'opération modifie la matrice courante.
     * Ne fait rien si la matrice n'est pas carrée.
     */
    public void setSymetrieCentrale(){
        setHomothetie(-1.0);
    }

    /**
     * Configure la matrice en matrice de réflexion 2D par rapport à l'axe Ox.
     * L'opération modifie la matrice courante.
     * Ne fait rien si la matrice n'est pas de dimension 2x2.
     */
    public void setReflexionOx(){
        if (getNbLignes()!=2 || getNbColonnes()!=2) return;
        setHomothetie(1.0);
        setCoefficient(1,1,-1.0);
    }

    /**
     * Configure la matrice en matrice de réflexion 3D par rapport au plan (Ox, Oy).
     * L'opération modifie la matrice courante.
     * Ne fait rien si la matrice n'est pas de dimension 3x3.
     */
    public void setReflexionOxOy(){
        if (getNbColonnes()!=3 || getNbLignes()!=3) return;
        setHomothetie(1.0);
        setCoefficient(2,2,-1.0);
    }

    /**
     * Configure la matrice en matrice de rotation 2D d'angle alpha (en radians) autour de l'origine.
     * L'opération modifie la matrice courante.
     * Ne fait rien si la matrice n'est pas de dimension 2x2.
     *
     * @param alpha L'angle de rotation en radians.
     */
    public void setRotation2d(double alpha){
        if (getNbLignes()!=2 || getNbColonnes()!=2) throw new RuntimeException("La matrice devrait être en 2x2");
        setCoefficient(0,0,Math.cos(alpha));
        setCoefficient(0,1,-Math.sin(alpha));
        setCoefficient(1,0,Math.sin(alpha));
        setCoefficient(1,1,Math.cos(alpha));
    }

    /**
     * Configure la matrice en matrice de rotation 3D d'angle alpha (en radians) autour de l'axe Ox.
     * L'opération modifie la matrice courante.
     * Ne fait rien si la matrice n'est pas de dimension 3x3.
     *
     * @param alpha L'angle de rotation en radians.
     */
    public void setRotation3dOx(double alpha){
        if (getNbLignes()!=3 || getNbColonnes()!=3) throw new RuntimeException("La matrice devrait être en 3x3");
        setIdentite();
        setCoefficient(1,1,Math.cos(alpha));
        setCoefficient(1,2,-Math.sin(alpha));
        setCoefficient(2,1,Math.sin(alpha));
        setCoefficient(2,2,Math.cos(alpha));
    }

    /**
     * Extrait une colonne de la matrice sous forme de vecteur.
     *
     * @param n L'indice de la colonne à extraire (0-based).
     * @return Un vecteur contenant les éléments de la colonne n.
     * @throws RuntimeException Si l'indice n est invalide.
     */
    public Vecteur getVecteurColonne(int n){
        if (n < 0 || n >= getNbColonnes()){
            throw new RuntimeException(n + " n'est pas un argument valable");
        }
        Vecteur vect = new Vecteur(getNbLignes());
        for (int i = 0; i < getNbLignes(); i++){
            vect.setCoordonnee(i, tab[i][n]);
        }
        return vect;
    }

    /**
     * Calcule le produit matriciel de l'instance courante par une autre matrice.
     *
     * @param mat La matrice avec laquelle multiplier (doit avoir un nombre de lignes égal au nombre de colonnes de l'instance courante).
     * @return Une nouvelle matrice résultat du produit, ou null si les dimensions sont incompatibles.
     */
    public Matrice produitMatriciel(Matrice mat){
        Matrice resultat = null;

        if (this.getNbColonnes() == mat.getNbLignes()){
            resultat = new Matrice(this.getNbLignes(), mat.getNbColonnes());
            for (int i = 0; i < resultat.getNbLignes(); i++){
                for (int j = 0; j < resultat.getNbColonnes(); j++){
                    double temp = 0;
                    for (int k = 0; k < this.getNbColonnes(); k++){
                        temp += this.getCoefficient(i, k) * mat.getCoefficient(k, j);
                    }
                    resultat.setCoefficient(i, j, temp);
                }
            }
        }

        return resultat;
    }

    /**
     * Configure la matrice en matrice de rotation 3D d'angle alpha autour de l'axe Oy.
     * L'opération modifie la matrice courante (doit être 3x3).
     *
     * @param alpha L'angle de rotation en radians.
     */
    public void setRotation3dOy(double alpha){
        if (getNbLignes()!=3 || getNbColonnes()!=3) throw new RuntimeException("La matrice devrait être en 3x3");
        setIdentite();
        setCoefficient(0,0,Math.cos(alpha));
        setCoefficient(0,2,Math.sin(alpha));
        setCoefficient(2,0,-Math.sin(alpha));
        setCoefficient(2,2,Math.cos(alpha));
    }

    /**
     * Configure la matrice en matrice de rotation 3D d'angle alpha autour de l'axe Oz.
     * L'opération modifie la matrice courante (doit être 3x3).
     *
     * @param alpha L'angle de rotation en radians.
     */
    public void setRotation3dOz(double alpha){
        if (getNbLignes()!=3 || getNbColonnes()!=3) throw new RuntimeException("La matrice devrait être en 3x3");
        setIdentite();
        setCoefficient(0,0,Math.cos(alpha));
        setCoefficient(0,1,-Math.sin(alpha));
        setCoefficient(1,0,Math.sin(alpha));
        setCoefficient(1,1,Math.cos(alpha));
    }

    /**
     * Crée une matrice de rotation composée en combinant des rotations successives
     * autour des axes Ox, Oy et Oz.
     *
     * @param pAlphaX Angle de rotation autour de l'axe X (en radians).
     * @param pAlphaY Angle de rotation autour de l'axe Y (en radians).
     * @param pAlphaZ Angle de rotation autour de l'axe Z (en radians).
     * @return Une nouvelle Matrice 3x3 représentant la rotation composée.
     */
    public static Matrice getRotation(double pAlphaX, double pAlphaY, double pAlphaZ){
        Matrice rotX = new Matrice(3, 3);
        rotX.setRotation3dOx(pAlphaX);
        Matrice rotY = new Matrice(3,3);
        rotY.setRotation3dOy(pAlphaY);
        Matrice rotZ = new Matrice(3, 3);
        rotZ.setRotation3dOz(pAlphaZ);
        return rotZ.produitMatriciel(rotY.produitMatriciel(rotX));
    }

    /**
     * Configure la matrice pour représenter une homothétie en coordonnées homogènes 3D.
     * Modifie la matrice courante (doit être 4x4).
     *
     * @param k Le facteur d'homothétie.
     */
    public void setHomothetieHomogene3d(double k) {
        if (getNbLignes() != 4 || getNbColonnes() != 4) return;
        setIdentite();
        setCoefficient(0, 0, k);
        setCoefficient(1, 1, k);
        setCoefficient(2, 2, k);
    }

    /**
     * Configure la matrice pour représenter une translation en coordonnées homogènes 3D.
     * Modifie la matrice courante (doit être 4x4).
     *
     * @param t Le vecteur de translation (dimension 3).
     */
    public void setTranslationHomogene3d(Vecteur t) {
        if (getNbLignes() != 4 || getNbColonnes() != 4) return;
        setIdentite();
        setCoefficient(0, 3, t.getCoordonnee(0));
        setCoefficient(1, 3, t.getCoordonnee(1));
        setCoefficient(2, 3, t.getCoordonnee(2));
    }
}