package com.lucim.casa_electricidad.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucim.casa_electricidad.entidades.Articulo;
import com.lucim.casa_electricidad.entidades.Fabrica;
import com.lucim.casa_electricidad.excepciones.MiExcepcion;
import com.lucim.casa_electricidad.repositorios.ArticuloRepositorio;
import com.lucim.casa_electricidad.repositorios.FabricaRepositorio;


@Service
public class ArticuloServicio {

    @Autowired
    ArticuloRepositorio articuloRepositorio;

    @Autowired
    FabricaRepositorio fabricaRepositorio;

    @Transactional
    public void crearArticulo(String nombreArticulo, String descripcionArticulo, UUID idFabrica) throws MiExcepcion {
        Fabrica fabrica = fabricaRepositorio.findById(idFabrica).get();
        validar(nombreArticulo, descripcionArticulo, idFabrica);
        Articulo articulo = new Articulo();
        articulo.setNroArticulo(Articulo.getAndIncrement());
        articulo.setNombreArticulo(nombreArticulo);
        articulo.setDescripcionArticulo(descripcionArticulo);
        articulo.setFabrica(fabrica);
        articuloRepositorio.save(articulo);
    }

    @Transactional
    public void modificarArticulo(UUID idArticulo, String nombreArticulo, String descripcionArticulo, UUID idFabrica) throws MiExcepcion {
        Optional<Fabrica> respFabrica = fabricaRepositorio.findById(idFabrica);
        Optional<Articulo> respArticulo = articuloRepositorio.findById(idArticulo);
        validar(nombreArticulo, descripcionArticulo, idFabrica);
        if(respArticulo.isPresent() && respFabrica.isPresent()){
            Articulo articulo = respArticulo.get();
            articulo.setNombreArticulo(nombreArticulo);
            articulo.setDescripcionArticulo(descripcionArticulo);
            articulo.setFabrica(respFabrica.get());
            articuloRepositorio.save(articulo);
        }
    }

    @Transactional(readOnly = true)
    public List<Articulo> listarArticulos() throws MiExcepcion{
        List<Articulo> articulos = new ArrayList<>();
        articulos = articuloRepositorio.findAll();
        return articulos;
    }

    public void validar(String nombreArticulo, String descripcionArticulo, UUID idFabrica) throws MiExcepcion {
        if (nombreArticulo.isEmpty() || nombreArticulo == null) {
            throw new MiExcepcion("Ingrese un nombre para el artículo");
        }
        if (descripcionArticulo.isEmpty() || descripcionArticulo == null) {
            throw new MiExcepcion("Ingrese una descripción para el artículo");
        }
        if (idFabrica == null) {
            throw new MiExcepcion("La fábrica no puede ser nula");
        }
    }

}
