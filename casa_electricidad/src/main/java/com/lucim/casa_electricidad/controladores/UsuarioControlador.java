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

import com.lucim.casa_electricidad.entidades.Usuario;
import com.lucim.casa_electricidad.servicios.UsuarioServicio;

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/usuarios")
public class UsuarioControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/lista")
    public String listarUsuarios(ModelMap modelo){
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "listaUsuarios.html";
    }

    @GetMapping("/cambiarRol/{id}")
    public String cambiarRol(@PathVariable UUID id) throws Exception {
        usuarioServicio.cambiarRol(id);
        return "redirect:../lista";
    }
    
}
