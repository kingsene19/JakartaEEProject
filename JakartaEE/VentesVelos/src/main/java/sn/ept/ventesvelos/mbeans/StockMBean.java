package sn.ept.ventesvelos.mbeans;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import sn.ept.ventesvelos.entites.Stock;
import sn.ept.ventesvelos.entites.StockPK;
import sn.ept.ventesvelos.facades.StockFacade;
import java.io.Serializable;
import java.util.List;

@Named("stockMBean")
@ViewScoped
public class StockMBean implements Serializable {

    @EJB
    private StockFacade stockFacade;

    private List<Stock> stocks;
    private Stock selectedStock;
    private List<Stock> selectedStocks;

    @PostConstruct
    public void init() {
        if (this.stocks == null) {
            this.stocks = stockFacade.findAll();
        }
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public Stock getSelectedStock() {
        return selectedStock;
    }

    public void setSelectedStock(Stock selectedStock) {
        this.selectedStock = selectedStock;
    }

    public List<Stock> getSelectedStocks() {
        return selectedStocks;
    }

    public void setSelectedStocks(List<Stock> selectedStocks) {
        this.selectedStocks = selectedStocks;
    }

    public void openNew() {
        this.selectedStock = new Stock();
        this.selectedStock.setStockPK(new StockPK());
    }

    public void saveStock() {
        StockPK stockPK = new StockPK(this.selectedStock.getMagasinId().getId(), this.selectedStock.getProduitId().getId());
        this.selectedStock.setStockPK(stockPK);
        if (stockFacade.find(this.selectedStock.getStockPK()) == null ) {
            stockFacade.create(this.selectedStock);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Stock Créé"));
        } else {
            stockFacade.edit(this.selectedStock);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Stock Modifié"));
        }
        this.selectedStock = null;
        PrimeFaces.current().executeScript("PF('manageStockDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-stocks");
    }

    public void deleteStock() {
        stockFacade.remove(this.selectedStock);
        if (hasSelectedStocks()) {
            this.selectedStocks.remove(this.selectedStock);
        }
        this.selectedStock = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Stock Supprimé"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-stocks");
    }

    public boolean hasSelectedStocks() {
        return this.selectedStocks!=null && !this.selectedStocks.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedStocks()) {
            int size  = this.selectedStocks.size();
            return size > 1 ? size + " stocks selectionnées" : "1 stock selectionnée";
        }
        return "Supprimer";
    }

    public void deleteSelectedStocks() {
        for (int i=0; i<this.selectedStocks.size(); i++) {
            stockFacade.remove(this.selectedStocks.get(i));
        }
        this.selectedStocks = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Stocks Supprimés"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-stocks");
        PrimeFaces.current().executeScript("PF('dtStocks').clearFilters()");
    }
}
