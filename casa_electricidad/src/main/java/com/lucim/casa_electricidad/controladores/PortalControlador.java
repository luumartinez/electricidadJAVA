package com.lucim.casa_electricidad.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lucim.casa_electricidad.excepciones.MiExcepcion;
import com.lucim.casa_electricidad.servicios.UsuarioServicio;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/inicio")
    public String inicio() {
        return "inicio.html";
    }

    @GetMapping("/registrar")
    public String registro() {
        return "registrousuario.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String email, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo) throws MiExcepcion {
        try {
            usuarioServicio.registrar(email, nombre, apellido, password, password2);
            modelo.put("exito", "Usuario registrado correctamente");
            return "login.html";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "registrousuario.html";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña inválidos!");
        }
        return "login.html";
    }
}

/*
 * private AtomicInteger atomicInteger;
 * 
 * public ArticuloServicio(FabricaRepositorio fabricaRepositorio,
 * ArticuloRepositorio articuloRepositorio){
 * this.fabricaRepositorio = fabricaRepositorio;
 * this.articuloRepositorio= articuloRepositorio;
 * Integer ultimoNumero = articuloRepositorio.buscarUltimoNumero();
 * atomicInteger = new AtomicInteger(ultimoNumero);
 * }
 */

/*
 * @Transactional
 * public void crearArticulo(String nombre, String descripcion, String
 * idFabrica) throws MiException{
 * validar(nombre, descripcion, idFabrica);
 * fabricaRepositorio.findById(idFabrica).orElseThrow(() -> new
 * MiException("fabrica no encontrada con ID: "+ idFabrica));
 * 
 * Articulo articulo = new Articulo();
 * 
 * Integer nroArticulo = atomicInteger.getAndIncrement();
 * articulo.setNombreArticulo(nombre);
 * articulo.setDescripcionArticulo(descripcion);
 * articulo.setNroArticulo(nroArticulo);
 * articuloRepositorio.save(articulo);
 * }
 */