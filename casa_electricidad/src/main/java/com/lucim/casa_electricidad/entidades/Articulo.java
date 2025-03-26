package com.lucim.casa_electricidad.entidades;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, name = "num_articulo")
    private Integer nroArticulo;

    @Column(nullable = false, name = "nombre_articulo")
    private String nombreArticulo;

    @Column(nullable = false, name = "descripcion_articulo")
    private String descripcionArticulo;

    @ManyToOne
    private Fabrica fabrica;

}
