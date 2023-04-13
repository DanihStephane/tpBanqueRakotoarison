/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanquerakotoarison.jsf;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import mg.itu.tpbanquerakotoarison.ejb.GestionnaireCompte;
import mg.itu.tpbanquerakotoarison.entities.CompteBancaire;

/**
 *
 * @author s.rakotoarison
 */
@Named(value = "listeComptes")
@ViewScoped
public class ListeComptes implements Serializable {

    @EJB
    private GestionnaireCompte gestionnaireCompte;

    private List<CompteBancaire> allComptes;

    /**
     * Creates a new instance of ListeComptes
     */
    public ListeComptes() {
    }

    public List<CompteBancaire> getAllComptes() {
        return (allComptes == null) ? gestionnaireCompte.getAllComptes() : allComptes;
    }

    public String supprimerCompte(CompteBancaire compte) {
        gestionnaireCompte.supprimerCompte(compte);
        Util.addFlashInfoMessage("Compte de " + compte.getNom() + " supprimé");
        return "listeComptes?faces-redirect=true";
    }
}
