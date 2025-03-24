package com.lucim.casa_electricidad.controladores;

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

import com.lucim.casa_electricidad.entidades.Fabrica;
import com.lucim.casa_electricidad.excepciones.MiExcepcion;
import com.lucim.casa_electricidad.servicios.FabricaServicio;

@Controller
@RequestMapping("/fabrica")
public class FabricaControlador {

    @Autowired
    private FabricaServicio fabricaServicio;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/lista")
    public String listarFabricas(ModelMap modelo) throws MiExcepcion {
        List<Fabrica> fabricas = fabricaServicio.listarFabricas();
        modelo.addAttribute("fabricas", fabricas);
        return "listafabrica.html";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/nuevo")
    public String crearFabrica() {
        return "crearFabrica.html";
    }

    @PostMapping("/nuevo")
    public String crearFabrica(@RequestParam String nombre, ModelMap modelo) throws MiExcepcion {
        try {
            fabricaServicio.crearFabrica(nombre);
            modelo.put("exito", "Fábrica creada correctamente!");
            return "redirect:/fabrica/lista";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "crearFabrica.html";
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable UUID id, ModelMap modelo) {
        modelo.put("fabrica", fabricaServicio.buscarPorID(id));
        return "editarFabrica.html";
    }

    @PostMapping("/editar/{id}")
    public String editar(@PathVariable UUID id, @RequestParam String nombre, ModelMap modelo) throws MiExcepcion{
        try {
            fabricaServicio.modificarFabrica(nombre, id);
            return "redirect:../lista";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "editarFabrica.html";
        }
    }
}