package sn.ept.ventesvelos.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sn.ept.ventesvelos.entites.Employe;

@Stateless
public class EmployeFacade extends AbstractFacade{

    @PersistenceContext(unitName = "velos_PU")
    private EntityManager em;

    public EmployeFacade() {super(Employe.class);}

    @Override
    protected EntityManager getEntityManager() {return this.em;}
}
