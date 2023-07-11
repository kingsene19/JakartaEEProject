package sn.ept.ventesvelos.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sn.ept.ventesvelos.entites.Client;

@Stateless
public class ClientFacade extends AbstractFacade{

    @PersistenceContext(unitName = "velos_PU")
    private EntityManager em;

    public ClientFacade() {super(Client.class);}

    @Override
    protected EntityManager getEntityManager() {return this.em;}
}
