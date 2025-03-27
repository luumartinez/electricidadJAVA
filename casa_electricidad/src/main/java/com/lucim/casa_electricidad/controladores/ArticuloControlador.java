package com.lucim.casa_electricidad.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lucim.casa_electricidad.entidades.Articulo;
import com.lucim.casa_electricidad.entidades.Fabrica;
import com.lucim.casa_electricidad.excepciones.MiExcepcion;
import com.lucim.casa_electricidad.servicios.ArticuloServicio;
import com.lucim.casa_electricidad.servicios.FabricaServicio;

@Controller
@RequestMapping("/articulo")
public class ArticuloControlador {

    @Autowired
    private ArticuloServicio articuloServicio;

    @Autowired
    private FabricaServicio fabricaServicio;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/lista")
    public String listaArticulos(ModelMap modelo) throws MiExcepcion {
        try {
            List<Articulo> articulos = articuloServicio.listarArticulos();
            modelo.addAttribute("articulos", articulos != null ? articulos : new ArrayList<>());
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
        }
        return "listaarticulo.html";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/nuevo")
    public String crearArticulo(ModelMap modelo) throws MiExcepcion {
        try {
            List<Fabrica> fabricas = fabricaServicio.listarFabricas();
            modelo.addAttribute("fabricas", fabricas);
            return "crearArticulo.html";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "crearArticulo.html";
        }
    }

    @PostMapping("/nuevo")
    public String crearArticulo(@RequestParam String nombre, @RequestParam String descripcion, @RequestParam UUID idFabrica, ModelMap modelo) throws MiExcepcion{
        try {
            articuloServicio.crearArticulo(nombre, descripcion, idFabrica);
            modelo.put("exito", "El artículo se cargó correctamente!");
            return "redirect:/articulo/lista";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "crearArticulo.html";
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable UUID id, ModelMap modelo) throws MiExcepcion {
        List<Fabrica> fabricas = fabricaServicio.listarFabricas();
        modelo.addAttribute("fabricas", fabricas);
        modelo.put("articulo", articuloServicio.buscarPorId(id));
        return "editarArticulo.html";
    }

    @PostMapping("/editar/{id}")
    public String editar(@PathVariable("id") UUID idArticulo, @RequestParam String nombre, @RequestParam String descripcion, @RequestParam UUID idFabrica, ModelMap modelo) throws MiExcepcion{
        try {
            articuloServicio.modificarArticulo(idArticulo, nombre, descripcion, idFabrica);
            return "redirect:../lista";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return"editarArticulo.html";
        }
    }
}
