/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanquerakotoarison.jsf;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.persistence.OptimisticLockException;
import java.io.Serializable;
import mg.itu.tpbanquerakotoarison.ejb.GestionnaireCompte;
import mg.itu.tpbanquerakotoarison.entities.CompteBancaire;

/**
 *
 * @author s.rakotoarison
 */
@Named(value = "modificationBean")
@ViewScoped
public class ModificationBean implements Serializable {

    @EJB
    GestionnaireCompte gestionnaireCompte;

    private Long id;
    private CompteBancaire compte;

    /**
     * Creates a new instance of ModificationBean
     */
    public ModificationBean() {
    }

    public void loadCompte() {
        compte = gestionnaireCompte.findById(id);
    }

    public String update() {
        try {
            CompteBancaire old = gestionnaireCompte.findById(id);
            gestionnaireCompte.update(compte);
            Util.addFlashInfoMessage("Modification du nom du possesseur du compte " + old.getNom() + " en " + compte.getNom());
            return "listeComptes?faces-redirect=true";
        } catch(EJBException ex){
            Throwable cause = ex.getCause();
            if(cause != null){
                if(cause instanceof OptimisticLockException){
                  Util.messageErreur("Le compte de " + compte.getNom()
                      + " a été modifié ou supprimé par un autre utilisateur !");                    
                }
                else
                    Util.messageErreur(cause.getMessage());
            }
            else{
                Util.messageErreur(ex.getMessage());
            }
            return null;
        }
    }

    public Long getId() {
        return id;
    }

    public CompteBancaire getCompte() {
        return compte;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
