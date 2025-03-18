package com.lucim.casa_electricidad.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucim.casa_electricidad.entidades.Fabrica;
import com.lucim.casa_electricidad.excepciones.MiExcepcion;
import com.lucim.casa_electricidad.repositorios.FabricaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class FabricaServicio {

    @Autowired
    FabricaRepositorio fabricaRepositorio;

    @Transactional
    public void crearFabrica(String nombre) throws MiExcepcion {
        validar(nombre);
        Fabrica fabrica = new Fabrica();
        fabrica.setNombreFabrica(nombre);
        fabricaRepositorio.save(fabrica);
    }

    public void validar(String nombre) throws MiExcepcion {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiExcepcion("Ingrese un nombre para la fábrica");
        }
    }
}
