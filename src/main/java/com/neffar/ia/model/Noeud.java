package com.neffar.ia.model;


/**
 * @author Soufiane neffar
 */

public class Noeud {

    private final int[][] matrice; // la grille de l'état courante du jeu
    private boolean max; // le type du joueur (true -> max -> machine) (false -> min -> l'humain)
    private int h; // la fonction heuritique du noeud

    public Noeud(boolean max, int[][] matrice) {
        this.max = max;
        this.matrice = matrice;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int[][] getMatrice() {
        return matrice;
    }

    public boolean isMax() {
        return max;
    }

    public void setMax(boolean max) {
        this.max = max;
    }

    /**
     * calcule si 4 pions sont alignes en diagonale
     *
     * @param typeJoueur type du joueur dont il faut verifier s'il a 4 pions alignes en diagonale
     * @return 0 si pas d'alignement, 1000 si 4 pions sont alignes
     */
    public int quatrePionsAlignesDiagonaleRight(Boolean typeJoueur) {
        boolean find = false;
        int jeton = typeJoueur ? 1 : 2;
        final String pattern = "" + jeton + jeton + jeton + jeton;
        StringBuilder diag = new StringBuilder();

        for (int j = 4; j <= matrice.length + matrice[0].length - 4; j++) {
            diag.setLength(0);
            for (int k = 0; k <= j; k++) { // cols
                int l = j - k; //  rows
                int mirror = matrice.length - l;
                if (mirror >= 0 && mirror < matrice.length && k < matrice[0].length) {
                    diag.append(matrice[mirror][k]);
                }
            }
            if (diag.toString().contains(pattern)) {
                find = true;
                break;
            }
        }

        return find ? 1000 : 0;
    }

    /**
     * calcule si 4 pions sont alignes en contre-diagonale
     *
     * @param typeJoueur type du joueur dont il faut verifier s'il a 4 pions alignes en contre-diagonale
     * @return 0 si pas d'alignement, 1000 si 4 pions sont alignes
     */
    int quatrePionsAlignesDiagonaleLeft(Boolean typeJoueur) {
        boolean find = false;
        int jeton = typeJoueur ? 1 : 2;
        final String pattern = "" + jeton + jeton + jeton + jeton;
        StringBuilder diag = new StringBuilder();

        // First half of the matrix
        for (int i = 3; i < matrice.length; i++) {
            diag.setLength(0);
            for (int j = i; j > -1; j--) {
                diag.append(matrice[j][i - j]);
            }
            if (diag.toString().contains(pattern)) {
                find = true;
                break;
            }
        }

        // Second half of the matrix
        for (int i = 0; i < matrice[0].length - 4; i++) {
            diag.setLength(0);
            for (int j = matrice.length - 1; j >= i; j--) {
                diag.append(matrice[j][i + matrice[0].length - 1 - j]);
            }
            if (diag.toString().contains(pattern)) {
                find = true;
                break;
            }
        }

        return find ? 1000 : 0;
    }

    /**
     * calcule si 4 pions sont alignes en ligne
     *
     * @param typeJoueur type du joueur dont il faut verifier s'il a 4 pions alignes dans une ligne
     * @return 0 si pas d'alignement, 1000 si 4 pions sont alignes
     */
    public int quatrePionsAlignesLigne(Boolean typeJoueur) {
        int contigue;
        boolean find = false;
        int jeton = typeJoueur ? 1 : 2;

        for (int i = 0; i < matrice.length && !find; i++) {
            contigue = 0;
            for (int j = 0; j < matrice.length && !find; j++) {
                if (matrice[i][j] != jeton) contigue = 0;
                else contigue++;

                find = contigue == 4;
            }
        }

        return find ? 1000 : 0;
    }

    /**
     * calcule si 4 pions sont alignes en colonne
     *
     * @param typeJoueur type du joueur dont il faut verifier s'il a 4 pions alignes en colonne
     * @return 0 si pas d'alignement, 1000 si 4 pions sont alignes
     */
    public int quatrePionsAlignesColonne(Boolean typeJoueur) {
        int contigue;
        boolean find = false;
        int jeton = typeJoueur ? 1 : 2;

        for (int j = 0; j < matrice[0].length && !find; j++) {
            contigue = 0;
            for (int i = 0; i < matrice.length && !find; i++) {
                if (matrice[i][j] != jeton) contigue = 0;
                else contigue++;

                find = contigue == 4;
            }
        }

        return find ? 1000 : 0;
    }

    /**
     * fonction qui retourne la valeur d'une diagonale
     * si 3 pions de type typeJoueur alignes en diagonale et accolles a une case vide -> val = 200
     * si 2 pions de type typeJoueur accolle en diagonale a une case vide -> val = 30
     */
    private int quatrePionsPossiblesDiagonaleRight(Boolean typeJoueur) {
        int retour = 0;
        StringBuilder diag = new StringBuilder();
        int jeton = typeJoueur ? 1 : 2;

        String case1 = "0" + jeton + jeton + jeton;
        String case2 = jeton + "0" + jeton + jeton;
        String case3 = jeton + jeton + "0" + jeton;
        String case4 = jeton + jeton + jeton + "0";

        String case5 = "0" + jeton + jeton;
        String case6 = jeton + "0" + jeton;
        String case7 = jeton + jeton + "0";

        for (int j = 4; j <= matrice.length + matrice[0].length - 4; j++) {
            diag.setLength(0);
            for (int k = 0; k <= j; k++) { // cols
                int l = j - k; //  rows
                int mirror = matrice.length - l;
                if (mirror >= 0 && mirror < matrice.length && k < matrice[0].length) {
                    diag.append(matrice[mirror][k]);
                }
            }
            String strDiag = diag.toString();

            if (strDiag.contains(case1) || strDiag.contains(case2) || strDiag.contains(case3) || strDiag.contains(case4))
                retour += 200;

            if (strDiag.contains(case5) || strDiag.contains(case6) || strDiag.contains(case7))
                retour += 30;
        }

        return retour;
    }

    /**
     * fonction qui retourne la valeur d'une contre-diagonale
     * si 3 pions de type typeJoueur alignes en contre-diagonale et accolles a une case vide -> val = 200
     * si 2 pions de type typeJoueur accolle en contre-diagonale a une case vide -> val = 30
     */
    private int quatrePionsPossiblesDiagonaleLeft(Boolean typeJoueur) {
        int retour = 0;
        StringBuilder diag = new StringBuilder();
        int jeton = typeJoueur ? 1 : 2;

        String case1 = "0" + jeton + jeton + jeton;
        String case2 = jeton + "0" + jeton + jeton;
        String case3 = jeton + jeton + "0" + jeton;
        String case4 = jeton + jeton + jeton + "0";

        String case5 = "0" + jeton + jeton;
        String case6 = jeton + "0" + jeton;
        String case7 = jeton + jeton + "0";

        // First half of the matrix
        for (int i = 3; i < matrice.length; i++) {
            diag.setLength(0);
            for (int j = i; j > -1; j--) {
                diag.append(matrice[j][i - j]);
            }
            String strDiag = diag.toString();

            if (strDiag.contains(case1) || strDiag.contains(case2) || strDiag.contains(case3) || strDiag.contains(case4))
                retour += 200;

            if (strDiag.contains(case5) || strDiag.contains(case6) || strDiag.contains(case7))
                retour += 30;
        }

        // Second half of the matrix
        for (int i = 0; i < matrice[0].length - 4; i++) {
            diag.setLength(0);
            for (int j = matrice.length - 1; j >= i; j--) {
                diag.append(matrice[j][i + matrice[0].length - 1 - j]);
            }
            String strDiag = diag.toString();

            if (strDiag.contains(case1) || strDiag.contains(case2) || strDiag.contains(case3) || strDiag.contains(case4))
                retour += 200;

            if (strDiag.contains(case5) || strDiag.contains(case6) || strDiag.contains(case7))
                retour += 30;
        }

        return retour;
    }

    /**
     * fonction qui retourne la valeur d'une ligne
     * si 3 pions de type typeJoueur alignes en ligne et accolles a une case vide -> val = 200
     * si 2 pions de type typeJoueur accolle en ligne a une case vide -> val = 30
     */
    private int quatrePionsPossiblesLigne(Boolean typeJoueur) {
        int retour = 0;
        int jeton = typeJoueur ? 1 : 2;
        for (int[] ints : matrice) {
            StringBuilder ligne = new StringBuilder();
            for (int j = 0; j < matrice[0].length; j++) {
                ligne.append(ints[j]);
            }

            String case1 = "0" + jeton + jeton + jeton;
            String case2 = jeton + "0" + jeton + jeton;
            String case3 = jeton + jeton + "0" + jeton;
            String case4 = jeton + jeton + jeton + "0";

            String case5 = "0" + jeton + jeton;
            String case6 = jeton + "0" + jeton;
            String case7 = jeton + jeton + "0";

            String strligne = ligne.toString();

            if (strligne.contains(case1) || strligne.contains(case2) || strligne.contains(case3) || strligne.contains(case4))
                retour += 200;

            if (strligne.contains(case5) || strligne.contains(case6) || strligne.contains(case7))
                retour += 30;
        }

        return retour;
    }

    /**
     * fonction qui retourne la valeur d'une colonne
     * si 3 pions de type typeJoueur alignes en colonne et accolles a une case vide -> val = 200
     * si 2 pions de type typeJoueur accolle en colonne a une case vide -> val = 30
     */
    private int quatrePionsPossiblesColonne(Boolean typeJoueur) {
        int retour = 0;
        int jeton = typeJoueur ? 1 : 2;
        for (int i = 0; i < matrice.length; i++) {
            StringBuilder colonne = new StringBuilder();
            for (int[] ints : matrice) {
                colonne.append(ints[i]);
            }

            String case1 = "0" + jeton + jeton + jeton;
            String case2 = jeton + "0" + jeton + jeton;
            String case3 = jeton + jeton + "0" + jeton;
            String case4 = jeton + jeton + jeton + "0";

            String case5 = "0" + jeton + jeton;
            String case6 = jeton + "0" + jeton;
            String case7 = jeton + jeton + "0";

            String strCol = colonne.toString();

            if (strCol.contains(case1) || strCol.contains(case2) || strCol.contains(case3) || strCol.contains(case4))
                retour += 200;

            if (strCol.contains(case5) || strCol.contains(case6) || strCol.contains(case7)) retour += 30;
        }
        return retour;
    }

    /**
     * fonction d'évaluation
     */
    public void evaluer() {
        h = -2 * quatrePionsAlignesLigne(false)
                + quatrePionsAlignesLigne(true)

                - 2 * quatrePionsAlignesColonne(false)
                + quatrePionsAlignesColonne(true)

                - 2 * quatrePionsPossiblesLigne(false)
                + quatrePionsPossiblesLigne(true)

                - 2 * quatrePionsPossiblesColonne(false)
                + quatrePionsPossiblesColonne(true)

                - 2 * quatrePionsAlignesDiagonaleRight(false)
                + quatrePionsAlignesDiagonaleRight(true)
                - 2 * quatrePionsAlignesDiagonaleLeft(false)
                + quatrePionsAlignesDiagonaleLeft(true)

                - 2 * quatrePionsPossiblesDiagonaleRight(false)
                + quatrePionsPossiblesDiagonaleRight(true)
                - 2 * quatrePionsPossiblesDiagonaleLeft(false)
                + quatrePionsPossiblesDiagonaleLeft(true);
    }

    @Override
    public String toString() {
        System.out.println("Évaluation h = " + h);
        StringBuilder node = new StringBuilder();
        for (int[] ints : matrice) {
            node.append("\n");
            for (int j = 0; j < matrice[0].length; j++) {
                node.append(ints[j]).append("|");
            }
        }
        node.append("\n------------\n");
        return node.toString();
    }

}