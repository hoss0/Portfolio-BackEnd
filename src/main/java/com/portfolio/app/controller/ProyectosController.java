package com.portfolio.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.app.Interface.IProyectosService;
import com.portfolio.app.Interface.IUserService;
import com.portfolio.app.model.entities.ErrorDetails;
import com.portfolio.app.model.entities.Proyectos;

import jakarta.validation.Valid;


@RestController
public class ProyectosController {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    IProyectosService proyectosService;

    @Autowired
    IUserService userService;

    @GetMapping("/proyectos")
    public List<Proyectos> getProyectos() {
        return proyectosService.getList();
    }

    @PostMapping("/proyectos/add")
    public ResponseEntity<String> addProyecto(@Valid @RequestBody Proyectos proyecto, BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            String errorMessage = getErrorMessage(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage + " tu JSON decía:" + objectMapper.writeValueAsString(proyecto));
        }

        // Verificar si el campo userid es nulo
        if (proyecto.getUserid() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El campo userid no puede ser nulo");
        }

        Long userId = proyecto.getUserid().getId();  // Obtén el ID de usuario del proyecto

        // Verificar si el ID de usuario corresponde a un usuario existente
        if (!userService.existById(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ID no corresponde a ningún usuario");
        }

        proyectosService.save(proyecto);
        return ResponseEntity.ok("Proyecto creado correctamente");
    }

    @DeleteMapping("/proyectos/del/{id}")
    public String deleteProyecto(@PathVariable Long id) {
        if (proyectosService.existById(id)) {
            proyectosService.deleteById(id);
            return "Proyecto eliminado exitosamente";
        }
        return "El proyecto no existe";
    }

    @PutMapping("/proyectos/edit/{id}")
    public ResponseEntity<String> editProyecto(
            @PathVariable Long id,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) String url) {

        Proyectos proyecto = proyectosService.find(id);

        if (proyecto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El proyecto no existe");
        }

        if (StringUtils.hasText(titulo)) {
            proyecto.setTitulo(titulo);
        }

        if (StringUtils.hasText(descripcion)) {
            proyecto.setDescripcion(descripcion);
        }

        if (StringUtils.hasText(url)) {
            proyecto.setUrl(url);
        }

        proyectosService.save(proyecto);

        return ResponseEntity.ok("Proyecto editado correctamente");
    }

    private String getErrorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append(fieldError.getDefaultMessage()).append(", ");
        }
        return errorMessage.toString();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String errorMessage = getErrorMessage(bindingResult);
        ErrorDetails errorDetails = new ErrorDetails(errorMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }
}
