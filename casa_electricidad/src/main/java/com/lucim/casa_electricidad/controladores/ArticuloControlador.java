package com.lucim.casa_electricidad.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lucim.casa_electricidad.entidades.Articulo;
import com.lucim.casa_electricidad.excepciones.MiExcepcion;
import com.lucim.casa_electricidad.servicios.ArticuloServicio;

@Controller
@RequestMapping("/articulo")
public class ArticuloControlador {

    @Autowired
    private ArticuloServicio articuloServicio;

    @GetMapping("/lista")
    public String listaArticulos(ModelMap modelo) throws MiExcepcion {
        try {
            List<Articulo> articulos = articuloServicio.listarArticulos();
            modelo.addAttribute("articulos", articulos != null ? articulos : new ArrayList<>());
        } catch (MiExcepcion ex) {
            modelo.addAttribute("error", ex.getMessage());
            modelo.addAttribute("articulos", new ArrayList<>());
        }
        return "listaarticulo.html";
    }

    @GetMapping("/nuevo")
    public String nuevo() {
        return "crearArticulo.html";
    }
}
