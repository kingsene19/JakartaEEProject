package sn.ept.ventesvelos.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sn.ept.ventesvelos.entites.Categorie;

@Stateless
public class CategorieFacade extends AbstractFacade{
    @PersistenceContext(name="velos_PU")
    private EntityManager em;

    public CategorieFacade() {
        super(Categorie.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
}
