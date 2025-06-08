package com.tallerwebi.infraestructura;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tallerwebi.dominio.Direccion;
import com.tallerwebi.dominio.Farmacia;
import com.tallerwebi.dominio.Localidad;
import com.tallerwebi.dominio.RepositorioFarmacia;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioFarmaciaTest {

    @Autowired
    private SessionFactory sessionFactory;//lo necesito para q me brinde una conexion a la bbdd

    @Autowired
    RepositorioFarmacia repo; //lo necesito para probar sus metodos justamente

    /*
     * Puedo obtener una lista de farmacia buscando segun su nombre.
     * Pero a veces nosotros no sabemos que es todo lo que a a tener la farmacia.
     * Porque todavia estan pensando desde la funcionalidad.
     * Mi aplicacion tiene farmacias
     * Mi aplicacion va tener que listar esas farmacias va haber un criterio de busqueda
     */

     /*
      * Todos los test del repositorio deben ser @Transactional pero a la vez necesito que una vez
      que termine de ejecutarse los test se haga un @Rollback
      O sea cada cosa que guarde en la bd la voy a volver para atras
      Si yo en mi ejecucion de mi test tuve que crear 4 farmacias y llame a la bd y los guarde
      Cuando termine este test va eliminarme esas 4 farma q yo cree.
      Con rollback se lo deja en su punto inicial.

      Cada test tiene que hacer eso para que un test no afecte al otro
      */
     @Test
     @Transactional
     @Rollback     
     public void queSePuedaObtenerUnaListaDeFarmaciaConLikeNombre(){
        //givenTengoUnaListaDeFarmacia();
        Localidad l1 = givenTengoUnaLocalidad("San Justo");
        
        givenTengoUnFarmacia("farmacity", "rivadavia", 123,l1);
        givenTengoUnFarmacia("farmaonline", "rivadavia", 1232, l1);
        givenTengoUnFarmacia("doctor ahorro", "viedma", 33, l1);
        givenTengoUnFarmacia("natal", "ramon falcon", 222, l1);

        List<Farmacia> farmacias = whenBuscoFarmaciaPorNombre("farma");
        thenEncuentroFarmacia(farmacias, 2);
     }
/* Hay otra forma para solventar este error del como solventar este error y q tiene que ver 
un poco del como voy a definir yo el modelo de mis entidades y la relacion entre ellas.
Y que dependencias van a tener una  de otra.

Si yo le digo que una farmacia tiene una direccion y una direcicon corresponde a una farmacia.
Pero la realidad la vinculacion que tienen es bastante estrecha.
O sea por que voy a querer crear una farmacia sin direccion
o por que quisiera tener una direccion q no tenga una farmacia.

O sea deberian ir SIEMPRE DE LA MANOS

Cada vez que creo una farmacia si o si tiene que tener una direccion y viciversa
Y si esa farmacia deja de existir y la quiero borrar 
Tendria que decir che borrame la farmacia y borrame la direccion ya porque me diria para q quiero una
farmacia si no existe en una direccion

Entomces tenemos una forma que sera CASCADE
que indica que proceso se va llevar al cabo cuando yo afecte algo de una farmacia.
Cuando creo una farmacia, la elimine, haga un update.

CASCADE.MERGE: Significa que cuando actualice la farmacia, actualizame la direccion tambien 

CASCADE.PERSIST: Significa que cuando guarde una farmacia, guardame tambien la direccion

CASCADE.REMOVE: Cuando elimines una farmacia, elimina tambien la direccion

CASCADE.ALL: hace todas las operaciones osea todo lo que haga con la farmacia, replicalo tambien en direccion

Esto permite solucionar eso de lo anterior del test que tenia que decir explicitamente hace save Direccion, hace save Farmacia

En cambio si uso CASCADE.ALL ya no es necesario decirlo explicitamente o sea solo hacer SAVE FARMACIA
Hibernate tambien aplicara ese mismo proceso para direccion
*/

private Localidad givenTengoUnaLocalidad(String nombreLocalidad) {
    Localidad localidad = new Localidad();
    localidad.setNombre(nombreLocalidad);
    sessionFactory.getCurrentSession().save(localidad);
    return localidad;
}

    /*
     private void givenTengoUnaListaDeFarmacia() {
        Farmacia farmacia1 = new Farmacia("farmacity");
        Farmacia farmacia2 = new Farmacia("farmaonline");
        Farmacia farmacia3 = new Farmacia("doctor ahorro");
        Farmacia farmacia4 = new Farmacia("natal");

        sessionFactory.getCurrentSession().save(farmacia1); //abro sesion con la bbdd y guardo a farmacia y esto le asignara automaticamente un ID
        sessionFactory.getCurrentSession().save(farmacia2);
        sessionFactory.getCurrentSession().save(farmacia3);
        sessionFactory.getCurrentSession().save(farmacia4);
    }
*/
    private void givenTengoUnFarmacia(String nombreFarmacia, String calle, Integer numero, Localidad localidad){
        Direccion direccion = new Direccion(calle, numero, localidad);
        //sessionFactory.getCurrentSession().save(direccion);
        Farmacia farmacia = new Farmacia(nombreFarmacia, direccion);
        sessionFactory.getCurrentSession().save(farmacia);

    }
     private List<Farmacia> whenBuscoFarmaciaPorNombre(String nombre) {
        List<Farmacia> farmacias = repo.buscarFarmaciasPorNombre(nombre);
        return farmacias;
    }
    
    private void thenEncuentroFarmacia(List<Farmacia> farmacias, Integer cantidadEsperada) {

        assertThat(farmacias.size(), equalTo(cantidadEsperada));

    }

    @Test
    @Transactional
    @Rollback
    public void puedoBuscarFarmaciasPorCalle(){
        Localidad l1 = givenTengoUnaLocalidad("San Justo");

        givenTengoUnFarmacia("farmacity", "rivadavia", 123, l1);
        givenTengoUnFarmacia("farmaonline", "rivadavia", 1232, l1);
        givenTengoUnFarmacia("doctor ahorro", "viedma", 33, l1);
        givenTengoUnFarmacia("natal", "rivadavia", 222,l1);
        List<Farmacia> farmacias = whenBuscoFarmaciaPorCalle("rivadavia");
        thenEncuentroFarmacia(farmacias, 3);
    }

    private List<Farmacia> whenBuscoFarmaciaPorCalle(String calle) {
        List<Farmacia> farmacias = repo.buscarFarmaciasPorCalle(calle);
        return farmacias;

    }

    @Test
    @Transactional
    @Rollback
    public void puedoBuscarFarmaciasPorNombreDeLocalidad(){
        Localidad sanJusto = givenTengoUnaLocalidad("San Justo");
        Localidad haedo = givenTengoUnaLocalidad("Haedo");


        givenTengoUnFarmacia("farmacity", "rivadavia", 123, sanJusto);
        givenTengoUnFarmacia("farmaonline", "rivadavia", 1232, haedo);
        givenTengoUnFarmacia("doctor ahorro", "viedma", 33, haedo);
        givenTengoUnFarmacia("natal", "rivadavia", 222, haedo);

        List<Farmacia> farmacias = whenBuscoFarmaciaPorNombreLocalidad(sanJusto.getNombre());
        thenEncuentroFarmacia(farmacias, 1);
    }

    private List<Farmacia> whenBuscoFarmaciaPorNombreLocalidad(String nombre) {
        return repo.buscarFarmaciasPorNombreLocalidad(nombre);
    }

    @Test
    @Transactional
    @Rollback
    public void puedoBuscarFarmaciasPorLocalidad(){//2DA OPCION
        Localidad sanJusto = givenTengoUnaLocalidad("San Justo");
        Localidad haedo = givenTengoUnaLocalidad("Haedo");


        givenTengoUnFarmacia("farmacity", "rivadavia", 123, sanJusto);
        givenTengoUnFarmacia("farmaonline", "rivadavia", 1232, haedo);
        givenTengoUnFarmacia("doctor ahorro", "viedma", 33, haedo);
        givenTengoUnFarmacia("natal", "rivadavia", 222, haedo);

        List<Farmacia> farmacias = whenBuscoFarmaciaPorLocalidad(sanJusto);
        thenEncuentroFarmacia(farmacias, 1);
    }

    private List<Farmacia> whenBuscoFarmaciaPorLocalidad(Localidad localidad) {
        return repo.buscarFarmaciasPorLocalidad(localidad);
    }

    
}
