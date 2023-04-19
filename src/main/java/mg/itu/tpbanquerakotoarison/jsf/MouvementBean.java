/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanquerakotoarison.jsf;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.persistence.OptimisticLockException;
import java.io.Serializable;
import mg.itu.tpbanquerakotoarison.ejb.GestionnaireCompte;
import mg.itu.tpbanquerakotoarison.entities.CompteBancaire;

/**
 *
 * @author user050
 */
@Named(value = "mouvementBean")
@ViewScoped
public class MouvementBean implements Serializable {

    @EJB
    GestionnaireCompte gestionnaireCompte;

    private Long id;
    private CompteBancaire compte;
    private String typeMouvement;
    private int montant;

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(String typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompteBancaire getCompte() {
        return compte;
    }

    public void loadCompte() {
        compte = gestionnaireCompte.findById(id);
    }

    /**
     * Creates a new instance of MouvementBean
     */
    public MouvementBean() {
    }

    public void validateSolde(FacesContext fc, UIComponent composant, Object valeur) {
        UIInput composantTypeMouvement = (UIInput) composant.findComponent("typeMouvement");

        String valeurTypeMouvement = (String) composantTypeMouvement.getLocalValue();
        if (valeurTypeMouvement == null) {
            return;
        }
        if (valeurTypeMouvement.equals("retrait")) {
            int retrait = (int) valeur;
            if (compte.getSolde() < retrait) {
                FacesMessage message
                        = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Le retrait doit être inférieur au solde du compte",
                                "Le retrait doit être inférieur au solde du compte");
                throw new ValidatorException(message);
            }
        }
    }

    public String enregistrerMouvement() {
        try {
            if ("retrait".equals(typeMouvement)) {
                gestionnaireCompte.retirer(compte, montant);
            } else {
                gestionnaireCompte.deposer(compte, montant);
            }
            Util.addFlashInfoMessage("Mouvement enregistré sur compte de " + compte.getNom());
            return "listeComptes?faces-redirect=true";
        } catch (EJBException ex) {
            Throwable cause = ex.getCause();
            if (cause != null) {
                if (cause instanceof OptimisticLockException) {
                    Util.messageErreur("Le compte de " + compte.getNom()
                            + " a été modifié ou supprimé par un autre utilisateur !");
                } else {
                    Util.messageErreur(cause.getMessage());
                }
            } else {
                Util.messageErreur(ex.getMessage());
            }
            return null;
        }
    }

}
