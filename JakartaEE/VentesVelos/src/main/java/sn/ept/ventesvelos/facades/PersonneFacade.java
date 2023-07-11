package sn.ept.ventesvelos.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sn.ept.ventesvelos.entites.Client;
import sn.ept.ventesvelos.entites.Personne;

@Stateless
public class PersonneFacade extends AbstractFacade{

    @PersistenceContext(unitName = "velos_PU")
    private EntityManager em;

    public PersonneFacade() {super(Personne.class);}

    @Override
    protected EntityManager getEntityManager() {return this.em;}
}
