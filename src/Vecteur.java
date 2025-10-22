import java.util.Arrays;

/**
 * Représente un vecteur de dimension N (N >= 2) et fournit diverses opérations
 * d’algèbre linéaire de base (norme, normalisation, somme, produit scalaire,
 * produit vectoriel en 3D, tests d’orthogonalité, colinéarité et coplanarité).
 */
public class Vecteur {
    private double[] coords;

    /**
     * Construit un vecteur de dimension pn. Si pn < 2, la dimension est forcée à 2.
     * Toutes les coordonnées sont initialisées à 0.
     * @param pn Dimension souhaitée du vecteur (au minimum 2)
     */
    public Vecteur(final int pn){
        if (pn<2) coords = new double[2];
        else coords = new double[pn];
        Arrays.fill(coords,0);
    }

    /**
     * Construit un nouveau vecteur en copiant les coordonnées d’un autre vecteur.
     * @param pVecteur Le vecteur à copier (même dimension).
     */
    public Vecteur(final Vecteur pVecteur){
        coords = Arrays.copyOf(pVecteur.coords,pVecteur.coords.length);
    }

    /**
     * Retourne la dimension (le nombre de coordonnées) du vecteur.
     * @return La dimension du vecteur.
     */
    public int getDimension(){
        return coords.length;
    }

    /**
     * Retourne la coordonnée à l’indice donné.
     * Aucune vérification de bornes n’est effectuée.
     * @param pi Indice de la coordonnée (0-based).
     * @return La valeur de la coordonnée pi.
     */
    public double getCoordonnee(final int pi){
        return coords[pi];
    }

    /**
     * Modifie la coordonnée à l’indice donné.
     * Aucune vérification de bornes n’est effectuée.
     * @param pi Indice de la coordonnée (0-based).
     * @param pCoordonnee Nouvelle valeur pour la coordonnée pi.
     */
    public void setCoordonnee(final int pi, final double pCoordonnee){
        coords[pi] = pCoordonnee;
    }

    /**
     * Retourne la première coordonnée (x) du vecteur.
     * @return La valeur de la coordonnée x.
     */
    public double getX(){
        return coords[0];
    }

    /**
     * Retourne la deuxième coordonnée (y) du vecteur.
     * @return La valeur de la coordonnée y.
     */
    public double getY(){
        return coords[1];
    }

    /**
     * Multiplie ce vecteur par un coefficient scalaire. L'opération modifie le vecteur courant.
     * @param pCoefficient Le coefficient de multiplication.
     */
    public void multiplicationScalaire(final double pCoefficient){
        for (int i = 0; i < coords.length; i++){
            coords[i] *= pCoefficient;
        }
    }

    /**
     * Calcule et retourne la norme euclidienne de ce vecteur.
     * @return La norme du vecteur.
     */
    public double norme(){
        double temp = 0;
        for (int i = 0; i < coords.length; i++){
            temp += coords[i]*coords[i];
        }
        return Math.sqrt(temp);
    }

    /**
     * Normalise ce vecteur pour que sa norme soit égale à 1.
     * L'opération modifie le vecteur courant. Ne fait rien si la norme est nulle.
     */
    public void normaliser(){
        double temp = norme();
        if (temp!=0) multiplicationScalaire(1/temp);
    }

    /**
     * Ajoute un autre vecteur à ce vecteur (somme vectorielle).
     * L'opération modifie le vecteur courant et ne s'effectue que si les deux vecteurs ont la même dimension.
     * @param pV Le vecteur à ajouter.
     */
    public void sommeVectorielle(final Vecteur pV){
        if (coords.length==pV.coords.length){
            for (int i = 0; i < coords.length; i++){
                coords[i] += pV.coords[i];
            }
        }
    }

    /**
     * Calcule le produit scalaire de ce vecteur avec un autre.
     * @param pV L'autre vecteur pour le calcul.
     * @return La valeur du produit scalaire. Retourne 0 si les dimensions des vecteurs ne correspondent pas.
     */
    public double produitScalaire(final Vecteur pV){
        double temp = 0;
        if (coords.length==pV.coords.length){
            for (int i = 0; i < coords.length; i++){
                temp += coords[i]*pV.coords[i];
            }
        }
        return temp;
    }

    /**
     * Calcule le produit vectoriel de ce vecteur avec un autre en 3D.
     * Les deux vecteurs doivent être de dimension 3.
     * @param pV L'autre vecteur de dimension 3.
     * @return Un nouveau vecteur résultat du produit vectoriel, ou null si les dimensions ne sont pas 3.
     */
    public Vecteur produitVectoriel3d(final Vecteur pV){
        if (pV.getDimension()==3 && this.getDimension()==3){
            Vecteur temp = new Vecteur(3);
            temp.setCoordonnee(0, getCoordonnee(1)*pV.getCoordonnee(2) - getCoordonnee(2)*pV.getCoordonnee(1));
            temp.setCoordonnee(1, getCoordonnee(2)*pV.getCoordonnee(0) - getCoordonnee(0)*pV.getCoordonnee(2));
            temp.setCoordonnee(2, getCoordonnee(0)*pV.getCoordonnee(1) - getCoordonnee(1)*pV.getCoordonnee(0));
            return temp;
        }
        return null;
    }

    /**
     * Vérifie si ce vecteur est orthogonal à un autre vecteur.
     * Deux vecteurs sont orthogonaux si leur produit scalaire est proche de zéro.
     * @param pV Le vecteur à comparer.
     * @return true si les vecteurs sont orthogonaux et de même dimension, sinon false.
     */
    public boolean estOrthogonal(final Vecteur pV){
        if (coords.length==pV.coords.length) return (approche(produitScalaire(pV),0,1e-9,1e-5));
        return false;
    }

    /**
     * Vérifie si ce vecteur est colinéaire à un autre vecteur.
     * La vérification se base sur la comparaison entre le produit scalaire et le produit des normes.
     * @param pV Le vecteur à comparer.
     * @return true si les vecteurs sont colinéaires et de même dimension, sinon false.
     */
    public boolean estColineaire(final Vecteur pV){
        if (coords.length==pV.coords.length) return (approche(Math.abs(produitScalaire(pV))-pV.norme()*norme(), 0, 1e-9, 1e-5));
        return false;
    }

    /**
     * Vérifie si ce vecteur est coplanaire avec deux autres vecteurs en 3D.
     * Trois vecteurs sont coplanaires si leur produit mixte (produit scalaire avec le produit vectoriel des deux autres) est nul.
     * @param pV1 Le premier autre vecteur (dimension 3).
     * @param pV2 Le second autre vecteur (dimension 3).
     * @return true si les trois vecteurs sont de dimension 3 et coplanaires, sinon false.
     */
    public boolean estCoplanaire3d(final Vecteur pV1, final Vecteur pV2){
        if (coords.length!=3 || pV1.coords.length!=3 || pV2.coords.length!=3){
            return false;
        }
        Vecteur tempProduitVect = pV1.produitVectoriel3d(pV2);
        return approche(produitScalaire(tempProduitVect), 0, 1e-9, 1e-5);
    }

    /**
     * Détermine si deux nombres de type double sont suffisamment proches.
     * @param a Le premier nombre.
     * @param b Le second nombre.
     * @param epsilon_rel La tolérance relative.
     * @param epsilon_abs La tolérance absolue.
     * @return true si les nombres sont considérés comme proches, sinon false.
     */
    public boolean approche(double a, double b, double epsilon_rel, double epsilon_abs){
        return (Math.abs(a-b) <= Math.max(Math.max(Math.abs(a), Math.abs(b)) * epsilon_rel, epsilon_abs));
    }

    @Override
    public String toString() {
        return "Coordonnées: "+Arrays.toString(coords);
    }
}