package sn.ept.ventesvelos;

import jakarta.ejb.*;
import jakarta.inject.Inject;
import sn.ept.ventesvelos.entites.Stock;
import sn.ept.ventesvelos.facades.StockFacade;

import java.util.List;

@Singleton
@Startup
public class StockNotificationBean {

     @Inject
     private SendEmailBean emailSender;
     @EJB
     private StockFacade stockFacade;

     public String getStockInformation() {
         String stockInformation = "L'état actuel des stocks est le suivant: \n\n";
         List<Stock> stockList = stockFacade.findAll();
         for (Stock e: stockList) {
             stockInformation += "Le stock au magasin" + e.getMagasinId().getNom() + " pour le produit " + e.getProduitId().getNom() + " est de " + e.getQuantite() + "\n\n\n";
         }
         return stockInformation;
     }

     @Schedule(hour="*", minute = "0", persistent = false)
     public void periodicStockEmail(Timer timer) {
         emailSender.sendEmail("tunknowed@gmail.com", "Etat des stocks",
                 "Voici l'état actuel des stocks " + getStockInformation());
     }
}
