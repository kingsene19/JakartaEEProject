package sn.ept.ventesvelos.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sn.ept.ventesvelos.entites.Marque;

@Stateless
public class MarqueFacade extends AbstractFacade{
    @PersistenceContext(name="velos_PU")
    private EntityManager em;

    public MarqueFacade() {
        super(Marque.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

}
