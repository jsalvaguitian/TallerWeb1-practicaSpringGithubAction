package com.tallerwebi.infraestructura;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.RepositorioCarta;

public class RepositorioCartaImpl implements RepositorioCarta {
    private SessionFactory sessionFactory;

    public RepositorioCartaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Boolean crear(Carta carta) {
        this.sessionFactory.getCurrentSession().save(carta);// sesionFactory obtene la sesion actual y guarda los datos
                                                            // del objeto y sus datos relacionados con otra entidad
        // save puede funcionar como insertar o actualizar
        return true;
    }

    @Override
    public Carta obtenerPorId(Long id) {
        String hql = "FROM Carta where id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        return (Carta)query.getSingleResult();
    }

    @Override
    public List<Carta> obtener() {
        String hql = "FROM Carta";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        return query.getResultList();

    }

    @Override
    public void actualizar(Carta carta) {
        String hql = "UPDATE Carta SET nombre = :nombre FROM Carta WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", carta.getId());
        query.setParameter("nombre", carta.getNombre());
        int cantidadActualizaciones = query.executeUpdate();

        if(cantidadActualizaciones > 1){
            //rollback
            // throw new MuchosRegistrosAfectados("Actualizo mas de uno");

        }
    }

}
