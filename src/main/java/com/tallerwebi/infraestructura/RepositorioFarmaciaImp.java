package com.tallerwebi.infraestructura;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.Farmacia;
import com.tallerwebi.dominio.Localidad;
import com.tallerwebi.dominio.RepositorioFarmacia;

/*
 * SELECT * FROM Farmacia WHERE nombre LIKE 'farma%'
 * gt() mayor que
 * ge() mayor o igual
 */
@Repository
public class RepositorioFarmaciaImp implements RepositorioFarmacia {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Farmacia> buscarFarmaciasPorNombre(String nombre) {
        var session = sessionFactory.getCurrentSession();//seria nuestro FROM Farmacia 
        return session.createCriteria(Farmacia.class)
                .add(Restrictions.like("nombre", nombre + "%"))
                .list(); 
    }

    /*
     * SELECT * FROM Farmacia f
     * join Direccion d ON f.idDireccion = d.if
     * where d.calle = "rivadavia"
     */
    //.createAlias(calle, calle)
    //me permite hacer un join con direccion. 
    //Y createAlias tiene 2 parametros. 
    //1 uno el nombre de la propiedad de farmacia con lo que yo quiero vincular
    //2do seria el alias de esa propiedad estaria haciendo {esto join Direccion d}

    @Override
    public List<Farmacia> buscarFarmaciasPorCalle(String calle) {
        Session sesion = sessionFactory.getCurrentSession();
        return sesion.createCriteria(Farmacia.class)
                .createAlias("direccion", "d") //join Direccion d
                .add(Restrictions.eq("d.calle", calle)) //where d.calle = "rivadavia"  no uso solo calle xq pensara q es un campo de farmacia
                .list();
    }

    /*
     * select * from Farmacia f
     * join Direccion d on f.idDireccion = d.id
     * join Localidad l on d.idLocalidad = l.id
     * where l.nombre = "san justo"
     * 
     */
    @Override
    public List<Farmacia> buscarFarmaciasPorNombreLocalidad(String nombre) {
        var sesion = sessionFactory.getCurrentSession();
        List<Farmacia>farmacias = sesion.createCriteria(Farmacia.class)
                                        .createAlias("direccion", "d")
                                        .createAlias("d.localidad", "l")
                                        .add(Restrictions.eq("l.nombre", nombre))
                                        .list();
        return farmacias;

    }

    @Override //BUSCO EL OBJETO LOCALIDAD
    public List<Farmacia> buscarFarmaciasPorLocalidad(Localidad localidad) {
         var sesion = sessionFactory.getCurrentSession();
         return sesion.createCriteria(Farmacia.class)
         .createAlias("direccion", "d")
         .add(Restrictions.eq("d.localidad", localidad))
         .list();
    
    
    }


}
