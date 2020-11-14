package com.neffar.ia.model;

/**
 * @author Soufiane neffar
 */

public class Coup {

    private int eval;
    private int colonne;

    public Coup(int eval, int colonne) {
        this.eval = eval;
        this.colonne = colonne;
    }

    public int getEval() {
        return eval;
    }

    public void setEval(int eval) {
        this.eval = eval;
    }

    public int getColonne() {
        return colonne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    @Override
    public String toString() {
        return "Coup{" +
                "eval=" + eval +
                ", colonne=" + colonne +
                '}';
    }
}