package com.krakedev.proyectos.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.services.TareaService;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

	private final TareaService service;

	public TareaController(TareaService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<?> guardar(@RequestBody Tarea tarea) {
		try {
			if (tarea.getProyecto() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe indicar el proyecto (id)");
			}

			if (tarea.getEmpleados() == null || tarea.getEmpleados().isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe indicar al menos un empleado");
			}

			Tarea guardada = service.guardar(tarea);
			return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al guardar la tarea: " + e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<?> listar() {
		try {
			List<Tarea> tareas = service.listar();
			return ResponseEntity.ok(tareas);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al listar las tareas: " + e.getMessage());
		}
	}
}