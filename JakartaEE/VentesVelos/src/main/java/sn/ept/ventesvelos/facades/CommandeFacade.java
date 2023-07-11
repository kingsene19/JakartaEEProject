package sn.ept.ventesvelos.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sn.ept.ventesvelos.entites.Commande;

@Stateless
public class CommandeFacade extends AbstractFacade{
    @PersistenceContext(unitName = "velos_PU")
    private EntityManager em;

    public CommandeFacade() {super(Commande.class);}

    @Override
    protected EntityManager getEntityManager() {return this.em;}
}
