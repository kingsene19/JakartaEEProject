package sn.ept.ventesvelos.mbeans;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import sn.ept.ventesvelos.entites.Adresse;
import sn.ept.ventesvelos.entites.ArticleCommande;
import sn.ept.ventesvelos.entites.Commande;
import sn.ept.ventesvelos.entites.Employe;
import sn.ept.ventesvelos.facades.ArticleCommandeFacade;
import sn.ept.ventesvelos.facades.CommandeFacade;
import sn.ept.ventesvelos.facades.EmployeFacade;
import sn.ept.ventesvelos.facades.PersonneFacade;

import java.io.Serializable;
import java.util.List;

@Named("employeMBean")
@ViewScoped
public class EmployeMBean implements Serializable {

    @EJB
    private EmployeFacade employeFacade;
    @EJB
    private CommandeFacade commandeFacade;
    @EJB
    private ArticleCommandeFacade articleCommandeFacade;
    @EJB
    private PersonneFacade personneFacade;

    private List<Employe> employes;
    private Employe selectedEmploye;
    private List<Employe> selectedEmployes;

    @PostConstruct
    public void init() {
        if (this.employes == null) {
            this.employes = employeFacade.findAll();
        }
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public Employe getSelectedEmploye() {
        return selectedEmploye;
    }

    public void setSelectedEmploye(Employe selectedEmploye) {
        this.selectedEmploye = selectedEmploye;
        if (this.selectedEmploye.getAdresse() == null ) {
            this.selectedEmploye.setAdresse(new Adresse());
        }
    }

    public List<Employe> getSelectedEmployes() {
        return selectedEmployes;
    }

    public void setSelectedEmployes(List<Employe> selectedEmployes) {
        this.selectedEmployes = selectedEmployes;
    }

    public void openNew() {
        this.selectedEmploye = new Employe();
        this.selectedEmploye.setAdresse(new Adresse());
    }

    public void saveEmploye() {
        if (this.selectedEmploye.getId() == null ) {
            employeFacade.create(this.selectedEmploye);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employe Créé"));
        } else {
            employeFacade.edit(this.selectedEmploye);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employe Modifié"));
        }
        this.selectedEmploye = null;
        PrimeFaces.current().executeScript("PF('manageEmployeDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-employes");
    }

    public void deleteEmploye() {
        employeFacade.remove(this.selectedEmploye);
        if (hasSelectedEmployes()) {
            this.selectedEmployes.remove(this.selectedEmploye);
        }
        this.selectedEmploye = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employe Supprimé"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-employes");
    }

    public boolean hasSelectedEmployes() {
        return this.selectedEmployes!=null && !this.selectedEmployes.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedEmployes()) {
            int size  = this.selectedEmployes.size();
            return size > 1 ? size + " employés selectionnés" : "1 employé selectionné";
        }
        return "Supprimer";
    }

    public void deleteSelectedEmployes() {
        for (int i=0; i<this.selectedEmployes.size(); i++) {
            employeFacade.remove(this.selectedEmployes.get(i));
        }
        this.selectedEmployes = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employes Supprimés"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-employes");
        PrimeFaces.current().executeScript("PF('dtEmployes').clearFilters()");
    }
}
