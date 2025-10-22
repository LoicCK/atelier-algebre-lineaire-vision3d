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
        if (getNbLignes()!=2 || getNbColonnes()!=2) return;
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
        if (getNbLignes()!=3 || getNbColonnes()!=3) return;
        setIdentite();
        setCoefficient(1,1,Math.cos(alpha));
        setCoefficient(1,2,-Math.sin(alpha));
        setCoefficient(2,1,Math.sin(alpha));
        setCoefficient(2,2,Math.cos(alpha));
    }
}