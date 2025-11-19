public class VecteurEn2d {
    private double x;
    private double y;

    /**
     * Construit un vecteur à partir de ses coordonnées x et y.
     * @param pX La coordonnée en x.
     * @param pY La coordonnée en y.
     */
    public VecteurEn2d(final double pX, final double pY){
        this.x=pX;
        this.y=pY;
    }

    /**
     * Construit un vecteur par recopie d'un autre vecteur.
     * @param pVecteur Le vecteur à copier.
     */
    public VecteurEn2d(final VecteurEn2d pVecteur){
        this.x=pVecteur.x;
        this.y=pVecteur.y;
    }

    /**
     * Retourne la coordonnée x du vecteur.
     * @return La valeur de la coordonnée x.
     */
    public double getX(){
        return this.x;
    }

    /**
     * Retourne la coordonnée y du vecteur.
     * @return La valeur de la coordonnée y.
     */
    public double getY(){
        return this.y;
    }

    /**
     * Calcule la norme (longueur) du vecteur.
     * @return La norme du vecteur.
     */
    public double norme(){
        return Math.sqrt(x*x + y*y);
    }

    /**
     * Multiplie le vecteur courant par un scalaire.
     * @param pScalaire La valeur scalaire pour la multiplication.
     */
    public void multiplicationScalaire(double pScalaire){
        this.x*=pScalaire;
        this.y*=pScalaire;
    }

    /**
     * Ajoute un vecteur au vecteur courant.
     * @param pVecteur Le vecteur à ajouter.
     */
    public void sommeVectorielle(VecteurEn2d pVecteur){
        this.x+=pVecteur.x;
        this.y+=pVecteur.y;
    }

    /**
     * Calcule le produit scalaire entre le vecteur courant et un autre vecteur.
     * @param pVecteur L'autre vecteur pour le calcul du produit scalaire.
     * @return Le résultat du produit scalaire.
     */
    public double produitScalaire(VecteurEn2d pVecteur){
        return this.x * pVecteur.x + this.y * pVecteur.y;
    }

    /**
     * Vérifie si le vecteur courant est orthogonal à un autre vecteur.
     * @param pVecteur Le vecteur avec lequel tester l'orthogonalité.
     * @return true si les vecteurs sont orthogonaux, sinon false.
     */
    public boolean estOrthogonal(VecteurEn2d pVecteur){
        return approche(produitScalaire(pVecteur), 0, 1e-9,1e-5);
    }

    /**
     * Obtient un nouveau vecteur qui est orthogonal au vecteur courant.
     * @return Un nouveau vecteur orthogonal.
     */
    public VecteurEn2d obtenirVectOrthogonal(){
        return new VecteurEn2d(this.y,-this.x);
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

    /**
     * Vérifie si le vecteur courant est colinéaire à un autre vecteur.
     * @param pVecteur Le vecteur avec lequel tester la colinéarité.
     * @return true si les vecteurs sont colinéaires, sinon false.
     */
    public boolean estColineaire(VecteurEn2d pVecteur){
        return approche(this.x*pVecteur.y, this.y*pVecteur.x, 1e-9,1e-5);
    }

    /**
     * Calcule la distance euclidienne entre le point représenté par le vecteur courant et un autre point.
     * @param pVecteur Le vecteur représentant l'autre point.
     * @return La distance entre les deux points.
     */
    public double distanceA(VecteurEn2d pVecteur){
        VecteurEn2d tmp = new VecteurEn2d(this.x,this.y);
        tmp.sommeVectorielle(new  VecteurEn2d(-pVecteur.x,-pVecteur.y));
        return tmp.norme();
    }

    /**
     * Vérifie si le segment [B,A] est orthogonal au segment [B,C], où B est le point représenté par l'instance courante.
     * @param pVecteurA Le vecteur représentant le point A.
     * @param pVecteurC Le vecteur représentant le point C.
     * @return true si les segments sont orthogonaux, sinon false.
     */
    public boolean segmentsSontOrthogonaux(final VecteurEn2d pVecteurA, final VecteurEn2d pVecteurC){
        VecteurEn2d vecteurBA = new VecteurEn2d(pVecteurA.x - this.x, pVecteurA.y - this.y);
        VecteurEn2d vecteurBC = new VecteurEn2d(pVecteurC.x - this.x, pVecteurC.y - this.y);
        return vecteurBA.estOrthogonal(vecteurBC);
    }
}