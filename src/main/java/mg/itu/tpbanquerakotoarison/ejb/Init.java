/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.tpbanquerakotoarison.ejb;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import mg.itu.tpbanquerakotoarison.entities.CompteBancaire;

/**
 *
 * @author s.rakotoarison
 */
@Singleton
@Startup
public class Init {

    @EJB
    private GestionnaireCompte gc;

    @PostConstruct
    public void init() {
        if (gc.nbComptes() == 0) {
            gc.creerCompte(new CompteBancaire("John Lennon", 150000));
            gc.creerCompte(new CompteBancaire("Paul McCartney", 950000));
            gc.creerCompte(new CompteBancaire("JRingo Starrn", 20000));
            gc.creerCompte(new CompteBancaire("Georges Harrisson", 100000));
        }
    }
}
