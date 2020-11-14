package com.neffar.ia.controller;

import com.neffar.ia.model.Coup;
import com.neffar.ia.model.Noeud;
import com.neffar.ia.model.Puissance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Soufiane neffar
 */

@Controller
public class IndexController {

    public static final int HEIGHT = 6;
    public static final int WIDTH = 7;
    public static int LEVEL;
    public static int[][] grille = new int[HEIGHT][WIDTH];
    public static int[][] copieGrille = new int[HEIGHT][WIDTH];

    public static Noeud n;
    public static Puissance p = new Puissance();

    @GetMapping("/")
    public String index(Model model) {

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grille[i][j] = 0;
            }
        }

        LEVEL = 4;

        n = new Noeud(false, grille);
        n.evaluer();
        n.setMax(true);

        model.addAttribute("matrix", grille);

        return "index";
    }

    @GetMapping("/profondeur/{p}")
    public String indexLevel(Model model, @PathVariable String p) {

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grille[i][j] = 0;
            }
        }

        LEVEL = Integer.parseInt(p);

        n = new Noeud(false, grille);
        n.evaluer();
        n.setMax(true);

        model.addAttribute("p", p);
        model.addAttribute("matrix", grille);

        return "index";
    }

    /*
    @RequestMapping(value = "/level", method = {RequestMethod.POST})
    @ResponseBody
    public String updateLevel(Model model, @RequestParam int profondeur) {
        LEVEL = profondeur;
        return "index" + LEVEL;
    }
    */

    @RequestMapping(value = "/play/{col}", method = RequestMethod.GET)
    public String play(Model model, @PathVariable("col") String colNum) {

        boolean turn = false;

        p.copieMatrice(grille, copieGrille);
        int col = Integer.parseInt(colNum);

        if (!p.estFinJeu(true, grille) || !p.estFinJeu(false, grille)) {
            // Tour de l'humain
            if (p.jouer(false, col, copieGrille)) {
                p.jouer(false, col, grille);
                n.setMax(false);
                n.evaluer();
                turn = true;
            }
        }

        if (turn && (!p.estFinJeu(true, grille) || !p.estFinJeu(false, grille))) {
            // Tour de la machine
            Coup c2 = p.alpha_beta(new Noeud(true, copieGrille), Integer.MIN_VALUE, Integer.MAX_VALUE, LEVEL);
            if (p.jouer(true, c2.getColonne(), copieGrille)) {
                p.jouer(true, c2.getColonne(), grille);
                n.setH(c2.getEval());
                n.setMax(true);
            }
        }

        if (p.estFinJeu(true, grille))
            model.addAttribute("gameOver", "La machine a");
        if (p.estFinJeu(false, grille))
            model.addAttribute("gameOver", "Vous avez");


        model.addAttribute("matrix", grille);
        return "fragments/matrix :: resultFragment";
    }

}
