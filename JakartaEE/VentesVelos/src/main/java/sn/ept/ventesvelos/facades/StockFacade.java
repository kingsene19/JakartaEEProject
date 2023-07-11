package sn.ept.ventesvelos.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sn.ept.ventesvelos.entites.Stock;

@Stateless
public class StockFacade extends AbstractFacade{

    @PersistenceContext(unitName = "velos_PU")
    private EntityManager em;

    public StockFacade() {super(Stock.class);}

    @Override
    protected EntityManager getEntityManager() {return this.em;}
}
