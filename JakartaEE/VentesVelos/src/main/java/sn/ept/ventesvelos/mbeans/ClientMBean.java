package sn.ept.ventesvelos.mbeans;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import sn.ept.ventesvelos.entites.Adresse;
import sn.ept.ventesvelos.entites.ArticleCommande;
import sn.ept.ventesvelos.entites.Client;
import sn.ept.ventesvelos.entites.Commande;
import sn.ept.ventesvelos.facades.ArticleCommandeFacade;
import sn.ept.ventesvelos.facades.ClientFacade;
import sn.ept.ventesvelos.facades.CommandeFacade;
import sn.ept.ventesvelos.facades.PersonneFacade;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named("clientMBean")
@ViewScoped
public class ClientMBean implements Serializable {

    @EJB
    private ClientFacade clientFacade;

    private List<Client> clients;
    private Client selectedClient;
    private List<Client> selectedClients;

    @PostConstruct
    public void init() {
        if (this.clients == null) {
            this.clients = clientFacade.findAll();
        }
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Client getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }

    public List<Client> getSelectedClients() {
        return selectedClients;
    }

    public void setSelectedClients(List<Client> selectedClients) {
        this.selectedClients = selectedClients;
    }

    public void openNew() {
        this.selectedClient = new Client();
        this.selectedClient.setAdresse(new Adresse());
    }

    public void saveClient() {
        if (this.selectedClient.getId() == null ) {
            clientFacade.create(this.selectedClient);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client Créé"));
        } else {
            clientFacade.edit(this.selectedClient);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client Modifié"));
        }
        this.selectedClient = null;
        PrimeFaces.current().executeScript("PF('manageClientDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-clients");
    }

    public void deleteClient() {
        clientFacade.remove(this.selectedClient);
        if (hasSelectedClients()) {
            this.selectedClients.remove(this.selectedClient);
        }
        this.selectedClient = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client Supprimé"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-clients");
    }

    public boolean hasSelectedClients() {
        return this.selectedClients!=null && !this.selectedClients.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedClients()) {
            int size  = this.selectedClients.size();
            return size > 1 ? size + " clients selectionnés" : "1 client selectionné";
        }
        return "Supprimer";
    }

    public void deleteSelectedClients() {
        for (int i=0; i<this.selectedClients.size(); i++) {
            clientFacade.remove(this.selectedClients.get(i));
        }
        this.selectedClients = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Clients Supprimés"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-clients");
        PrimeFaces.current().executeScript("PF('dtClients').clearFilters()");
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}
