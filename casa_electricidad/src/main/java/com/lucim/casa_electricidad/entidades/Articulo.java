package com.lucim.casa_electricidad.entidades;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Articulo {

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "num_articulo")
    private Integer nroArticulo;

    @Column(nullable = false, name = "nombre_articulo")
    private String nombreArticulo;

    @Column(nullable = false, name = "descripcion_articulo")
    private String descripcionArticulo;

    @ManyToOne
    private Fabrica fabrica;

    @PrePersist
    private void asignarNroArticulo() {
        this.nroArticulo = atomicInteger.getAndIncrement();
    }
}
