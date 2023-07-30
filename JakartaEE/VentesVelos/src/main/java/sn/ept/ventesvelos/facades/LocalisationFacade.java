package sn.ept.ventesvelos.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sn.ept.ventesvelos.entites.Localisation;

@Stateless
public class LocalisationFacade extends AbstractFacade{
    @PersistenceContext(name="velos_PU")
    private EntityManager em;

    public LocalisationFacade() {
        super(Localisation.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
}
