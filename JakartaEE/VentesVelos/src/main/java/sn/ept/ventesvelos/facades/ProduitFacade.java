package sn.ept.ventesvelos.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sn.ept.ventesvelos.entites.Produit;

@Stateless
public class ProduitFacade extends AbstractFacade{

    @PersistenceContext(unitName = "velos_PU")
    private EntityManager em;

    public ProduitFacade() {
        super(Produit.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

}
