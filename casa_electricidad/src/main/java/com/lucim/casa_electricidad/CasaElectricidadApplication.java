package com.lucim.casa_electricidad;

import java.util.List;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.lucim.casa_electricidad.entidades.Articulo;
import com.lucim.casa_electricidad.excepciones.MiExcepcion;
import com.lucim.casa_electricidad.servicios.ArticuloServicio;
import com.lucim.casa_electricidad.servicios.FabricaServicio;
import com.lucim.casa_electricidad.servicios.UsuarioServicio;

@SpringBootApplication
public class CasaElectricidadApplication {

	public static void main(String[] args) {

		SpringApplication.run(CasaElectricidadApplication.class, args);

	}

	// @Bean
	// CommandLineRunner init(ApplicationContext context) {
	// return args -> {
	// UsuarioServicio usuarioServicio = context.getBean(UsuarioServicio.class);
	// try {
	// usuarioServicio.registrar("admin@admin.com", "Juan", "Pérez", "123456",
	// "123456");
	// } catch (MiExcepcion e) {
	// System.out.println("Error al registrar usuario: " + e.getMessage());
	// }
	// };
	// }
/* 	@Bean
	CommandLineRunner init(ApplicationContext context) {
		return args -> {
			ArticuloServicio articuloServicio = context.getBean(ArticuloServicio.class);
			try {
				articuloServicio.crearArticulo("Cable", "TPU",
				UUID.fromString("3eef0d36-85d5-4d2f-812c-0bcad90990fd"));
				// articuloServicio.modificarArticulo(UUID.fromString("bc8c8171-cbd0-44d5-9d66-b840fab70717"),
				// "Reflector", "Solar",
				// UUID.fromString("3eef0d36-85d5-4d2f-812c-0bcad90990fd"));
				// List<Articulo> articulos = articuloServicio.listarArticulos();
				// System.out.println("Lista de artículos:");
				// for (Articulo articulo : articulos) {
				// 	System.out.println("ID: " + articulo.getId() +
				// 			", Nombre: " + articulo.getNombreArticulo() +
				// 			", Descripción: " + articulo.getDescripcionArticulo() +
				// 			", Fábrica: " + (articulo.getFabrica() != null ? articulo.getFabrica().getNombreFabrica()
				// 					: "Sin fábrica"));
				// }
			} catch (MiExcepcion e) {
				System.out.println("Error al registrar usuario: " + e.getMessage());
			}
		};
	} */
	// @Bean
	// CommandLineRunner init(ApplicationContext context) {
	// return args -> {
	// FabricaServicio fabricaServicio = context.getBean(FabricaServicio.class);
	// try {
	// fabricaServicio.crearFabrica("TuElectricity");
	// fabricaServicio.crearFabrica("EleCript");
	// fabricaServicio.crearFabrica("Starlight");
	// } catch (MiExcepcion e) {
	// System.out.println("Error al registrar crear fábrica: " + e.getMessage());
	// }
	// };
	// }
}