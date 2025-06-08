package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioFarmacia {

	List<Farmacia> buscarFarmaciasPorNombre(String nombre);

    List<Farmacia> buscarFarmaciasPorCalle(String calle);

    List<Farmacia> buscarFarmaciasPorNombreLocalidad(String nombre);

    List<Farmacia> buscarFarmaciasPorLocalidad(Localidad localidad);

}
