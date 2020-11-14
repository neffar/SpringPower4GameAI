package com.neffar.ia.model;

import java.util.Arrays;

/**
 * @author Soufiane neffar
 */

public class Puissance {

    private final int[][] matriceJeu;
    public static final int HEIGHT = 6;
    public static final int WIDTH = 7;

    public Puissance() {
        this.matriceJeu = new int[HEIGHT][WIDTH];
    }

    public int[][] getMatriceJeu() {
        return matriceJeu;
    }

    private static boolean isFull(int[][] matrix) {
        boolean full = true;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (matrix[i][j] == 0) {
                    full = false;
                    break;
                }
            }
        }
        return full;
    }

    /**
     * jouer la colonne j de la matrice passé en paramètre selon le type du joueur :
     * jeton = 1 si MAX (Machine) et jeton = 2 si MIN (Humain)
     */
    public boolean jouer(Boolean typeJoueur, int colonne, int[][] matrice) {
        boolean faisable = false;
        int jeton;
        jeton = typeJoueur ? 1 : 2;
        for (int i = HEIGHT - 1; i >= 0; i--) {
            if (matrice[i][colonne] == 0) {
                matrice[i][colonne] = jeton;
                faisable = true;
                break;
            }
        }
        return faisable;
    }

    /**
     * @return true si c'est la fin du jeu : grille pleine, MAX a gagné ou MIN a gagné
     */
    public boolean estFinJeu(Boolean typeJoueur, int[][] matrice) {
        Noeud noeud = new Noeud(typeJoueur, matrice);
        return isFull(matrice) || noeud.quatrePionsAlignesLigne(typeJoueur) == 1000 || noeud.quatrePionsAlignesColonne(typeJoueur) == 1000
                || noeud.quatrePionsAlignesDiagonaleRight(typeJoueur) == 1000 || noeud.quatrePionsAlignesDiagonaleLeft(typeJoueur) == 1000;
    }

    public void copieMatrice(int[][] mSource, int[][] mDest) {
        for (int i = 0; i < HEIGHT; i++)
            System.arraycopy(mSource[i], 0, mDest[i], 0, HEIGHT);
    }

    /**
     * l'algorithme alpha beta
     *
     * @return Coup (eval + colonne)
     * il s'agit de l'évaluation du meilleur successeur avec le meilleur coup à jouer
     */
    public Coup alpha_beta(Noeud n, int alpha, int beta, int profondeur) {
        int[][] mCopy = new int[HEIGHT][WIDTH];
        int bestJ = 0;

        copieMatrice(n.getMatrice(), mCopy);
        if (profondeur == 1 || estFinJeu(n.isMax(), mCopy)) {
            n.evaluer();
            return new Coup(n.getH(), -1);
        }
        if (n.isMax()) {
            for (int j = 0; j < WIDTH; j++) {
                if (jouer(n.isMax(), j, mCopy)) {
                    Noeud successeur = new Noeud(!n.isMax(), mCopy);
                    Coup coup = alpha_beta(successeur, alpha, beta, profondeur - 1);
                    successeur.setH(coup.getEval());
                    if (coup.getEval() > alpha) {
                        alpha = coup.getEval();
                        bestJ = j;
                    }
                    if (alpha >= beta) {
                        return new Coup(alpha, bestJ);
                    }
                }
            }
            return new Coup(alpha, bestJ);
        } else {
            for (int j = 0; j < WIDTH; j++) {
                if (jouer(n.isMax(), j, mCopy)) {
                    Noeud successeur = new Noeud(!n.isMax(), mCopy);

                    Coup coup = alpha_beta(successeur, alpha, beta, profondeur - 1);
                    successeur.setH(coup.getEval());

                    if (coup.getEval() < beta) {
                        beta = coup.getEval();
                        bestJ = j;
                    }
                    if (beta <= alpha) {
                        return new Coup(beta, bestJ);
                    }
                }
            }
            return new Coup(beta, bestJ);
        }
    }

    @Override
    public String toString() {
        return "Puissance{" +
                "matriceJeu=" + Arrays.deepToString(matriceJeu) +
                ", WIDTH=" + WIDTH +
                ", HEIGHT=" + HEIGHT +
                '}';
    }

}