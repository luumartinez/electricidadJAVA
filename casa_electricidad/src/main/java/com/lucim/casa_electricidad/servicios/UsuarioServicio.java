package com.lucim.casa_electricidad.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lucim.casa_electricidad.entidades.Usuario;
import com.lucim.casa_electricidad.enumeraciones.Rol;
import com.lucim.casa_electricidad.excepciones.MiExcepcion;
import com.lucim.casa_electricidad.repositorios.UsuarioRepositorio;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServicio implements UserDetailsService {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public Usuario buscarPorEmail(String email) {
        Optional<Usuario> optionalUsuario = usuarioRepositorio.buscarPorEmail(email);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            return usuario;
        } else {
            return null;
        }
    }

    @Transactional
    public void registrar(String email, String nombre, String apellido, String password, String password2)
            throws MiExcepcion {
        try {
            validar(email, nombre, apellido, password, password2);
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setRol(Rol.USER);
            usuarioRepositorio.save(usuario);
        } catch (MiExcepcion ex) {
            throw new MiExcepcion(ex.getMessage());
        }
    }

    @Transactional
    public void modificarUsuario(String email, String nombre, String apellido) throws MiExcepcion {
        try {
            Optional<Usuario> respUsuario = usuarioRepositorio.buscarPorEmail(email);
            if(respUsuario.isPresent()) {
                validar(email, nombre, apellido);
                Usuario usuario = respUsuario.get();
                usuario.setEmail(email);
                usuario.setNombre(nombre);
                usuario.setApellido(apellido);
                usuarioRepositorio.save(usuario);
            }

        } catch (MiExcepcion ex) {
            throw new MiExcepcion("No se pudo modificar el usuario" + ex.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = usuarioRepositorio.findAll();
        return usuarios;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Usuario> optionalUsuario = usuarioRepositorio.buscarPorEmail(email);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }

    public void validar(String email, String nombre, String apellido, String password, String password2)
            throws MiExcepcion {
        if (email.isEmpty() || email == null) {
            throw new MiExcepcion("El campo email no puede estar vacío");
        }

        if (usuarioRepositorio.buscarPorEmail(email).isPresent()) {
            throw new MiExcepcion("El email ya se encuentra registrado");
        }

        if (nombre.isEmpty() || nombre == null || nombre.length() <= 2) {
            throw new MiExcepcion("El campo nombre no puede estar vacío y debe contener al menos dos caracteres");
        }
        if (apellido.isEmpty() || apellido == null || apellido.length() <= 2) {
            throw new MiExcepcion("El campo apellido no puede estar vacío y debe contener al menos dos caracteres");
        }

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiExcepcion("El campo password no puede estar vacío y debe contener al menos cinco caracteres");
        }

        if (!password.equals(password2)) {
            throw new MiExcepcion("Las contraseñas no coinciden");
        }
    }

    public void validar(String email, String nombre, String apellido)
            throws MiExcepcion {
        if (email.isEmpty() || email == null) {
            throw new MiExcepcion("El campo email no puede estar vacío");
        }

        if (usuarioRepositorio.buscarPorEmail(email).isPresent()) {
            throw new MiExcepcion("El email ya se encuentra registrado");
        }

        if (nombre.isEmpty() || nombre == null || nombre.length() <= 2) {
            throw new MiExcepcion("El campo nombre no puede estar vacío y debe contener al menos dos caracteres");
        }
        if (apellido.isEmpty() || apellido == null || apellido.length() <= 2) {
            throw new MiExcepcion("El campo apellido no puede estar vacío y debe contener al menos dos caracteres");
        }
    }
}
