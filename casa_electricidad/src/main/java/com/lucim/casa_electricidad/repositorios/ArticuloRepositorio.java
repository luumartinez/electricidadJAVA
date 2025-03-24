package com.lucim.casa_electricidad.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucim.casa_electricidad.entidades.Articulo;

@Repository
public interface ArticuloRepositorio extends JpaRepository<Articulo, UUID> {
}