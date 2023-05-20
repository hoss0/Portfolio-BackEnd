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
import com.portfolio.app.Interface.IEducacionService;
import com.portfolio.app.Interface.IUserService;
import com.portfolio.app.model.entities.Educacion;
import com.portfolio.app.model.entities.ErrorDetails;

import jakarta.validation.Valid;


@RestController
public class EducacionController {
    @Autowired ObjectMapper objectMapper;

    @Autowired IEducacionService educacionService;

    @Autowired IUserService userService;

    @GetMapping("/educaciones")
    public List<Educacion> getEducaciones() {
        return educacionService.getList();
    }

    @PostMapping("/educacion/add")
    public ResponseEntity<String> addEducacion(@Valid @RequestBody Educacion educacion, BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            String errorMessage = getErrorMessage(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage + " tu JSON decía:" + objectMapper.writeValueAsString(educacion));
        }

        // Verificar si el campo userid es nulo
        if (educacion.getUserid() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El campo userid no puede ser nulo");
        }

        Long userId = educacion.getUserid().getId();  // Obtén el ID de usuario de la educación

        // Verificar si el ID de usuario corresponde a un usuario existente
        if (!userService.existById(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ID no corresponde a ningún usuario");
        }

        educacionService.save(educacion);
        return ResponseEntity.ok("Educación creada correctamente");
    }

    @DeleteMapping("/educacion/del/{id}")
    public String deleteEducacion(@PathVariable Long id) {
        if (educacionService.existById(id)) {
            educacionService.deleteById(id);
            return "Educación eliminada exitosamente";
        }
        return "La educación no existe";
    }

    @PutMapping("/educacion/edit/{id}")
    public ResponseEntity<String> editEducacion(
            @PathVariable Long id,
            @RequestParam(required = false) String institucion,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) String periodo) {

        Educacion educacion = educacionService.find(id);

        if (educacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La educación no existe");
        }

        if (StringUtils.hasText(institucion)) {
            educacion.setInstitucion(institucion);
        }

        if (StringUtils.hasText(titulo)) {
            educacion.setTitulo(titulo);
        }

        if (StringUtils.hasText(descripcion)) {
            educacion.setDescripcion(descripcion);
        }

        if (StringUtils.hasText(periodo)) {
            educacion.setPeriodo(periodo);
        }

        educacionService.save(educacion);

        return ResponseEntity.ok("Educación editada correctamente");
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

