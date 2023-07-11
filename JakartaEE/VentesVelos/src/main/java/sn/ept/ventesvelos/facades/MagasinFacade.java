package sn.ept.ventesvelos.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sn.ept.ventesvelos.entites.Magasin;

@Stateless
public class MagasinFacade extends AbstractFacade{

    @PersistenceContext(unitName = "velos_PU")
    private EntityManager em;

    public MagasinFacade() {super(Magasin.class);}

    @Override
    protected EntityManager getEntityManager() {return this.em;}
}
