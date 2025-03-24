package com.lucim.casa_electricidad.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucim.casa_electricidad.entidades.Fabrica;
import com.lucim.casa_electricidad.excepciones.MiExcepcion;
import com.lucim.casa_electricidad.repositorios.FabricaRepositorio;


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

    @Transactional
    public void modificarFabrica(String nombre, UUID id) throws MiExcepcion {
        Optional<Fabrica> respFabrica = fabricaRepositorio.findById(id);
        if(respFabrica.isPresent()){
            validar(nombre);
            Fabrica fabrica = respFabrica.get();
            fabrica.setNombreFabrica(nombre);
            fabricaRepositorio.save(fabrica);
        }
    }

    @Transactional(readOnly = true)
    public List<Fabrica> listarFabricas() throws MiExcepcion{
        List<Fabrica> fabricas = new ArrayList<>();
        fabricas = fabricaRepositorio.findAll();
        return fabricas;
    }
    
    @Transactional(readOnly = true)
    public Fabrica buscarPorID(UUID id) {
       return fabricaRepositorio.getReferenceById(id);
    }

    public void validar(String nombre) throws MiExcepcion {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiExcepcion("Ingrese un nombre para la fábrica");
        }
    }
}
