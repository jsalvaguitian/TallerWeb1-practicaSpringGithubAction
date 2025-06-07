package com.tallerwebi.infraestructura;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.RepositorioCarta;
import com.tallerwebi.dominio.Tipo;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import javax.persistence.Query;
import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
public class RepositorioCartaTest {
    
    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioCarta repositorioCarta;

    @BeforeEach
    public void init(){
        this.repositorioCarta = new RepositorioCartaImpl(this.sessionFactory);
    }

    
    @Test
    @Transactional //metelo en una transaccional
    @Rollback//Deshacelo para luego no tocar otros test
    public void cuandoCreoUnaCartaConDatosCorrectosEntoncesSeGuardaEnLaBaseDeDatos(){
        Tipo tipo = new Tipo();
        tipo.setNombre("rayo");
        this.sessionFactory.getCurrentSession().save(tipo);

        Carta carta = new Carta();
        carta.setNombre("MiCarta");
        carta.setTipo(tipo);
        //guardo en la db
        boolean guardada = this.repositorioCarta.crear(carta);

        //consulto si fue guardada

        String hql = "FROM Carta WHERE nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "MiCarta");
        Carta obtenida = (Carta)query.getSingleResult(); //Podemos jugar excepciones con try catch

        assertThat(guardada, is(true));
        assertThat(obtenida, equalTo(carta));
    }

    @Test
    @Transactional //metelo en una transaccional
    @Rollback//Deshacelo para luego no tocar otros test
    public void dadoQueExisteUnaCartaEnLaBDCuandoLaObtengoPorIdMeDevuelveLaCartaCorrespondiente(){
        //Preparacion
        Carta carta = new Carta();
        carta.setNombre("MiCarta");
        this.sessionFactory.getCurrentSession().save(carta);

        //ejecucion
       String hql = "FROM Carta WHERE nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "MiCarta");
        Carta guardada = (Carta)query.getSingleResult(); //Podemos jugar excepciones con try catch

        Carta obtenida = this.repositorioCarta.obtenerPorId(guardada.getId());
        //validacion
        assertThat(obtenida,equalTo(carta));//para que funcione el equal en tdd tenemos q hacer un overrida del metodo equalto en carta
    }
}
