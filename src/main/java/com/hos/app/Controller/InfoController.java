package com.hos.app.Controller;

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
import com.hos.app.Entities.ErrorDetails;
import com.hos.app.Entities.Info;
import com.hos.app.Interface.IInfoService;
import com.hos.app.Interface.IUserService;


import jakarta.validation.Valid;

@RestController
public class InfoController {
    @Autowired ObjectMapper objectMapper;

    @Autowired IInfoService infoService;

    @Autowired IUserService userService;

    @GetMapping("/infos")
    public List<Info> getInfos() {
        return infoService.getList();
    }

    @PostMapping("/info/add")
    public ResponseEntity<String> addInfo(@Valid @RequestBody Info info, BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            String errorMessage = getErrorMessage(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage + " tu JSON decía:" + objectMapper.writeValueAsString(info));
        }

        // Verificar si el campo userid es nulo
        if (info.getUserid() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El campo userid no puede ser nulo");
        }

        Long userId = info.getUserid().getId();  // Obtén el ID de usuario de la información

        // Verificar si el ID de usuario corresponde a un usuario existente
        if (!userService.existById(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ID no corresponde a ningún usuario");
        }

        // Verificar si ya existe una información asociada a este usuario
        if (infoService.existById(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe una información asociada a este usuario");
        }

        infoService.save(info);
        return ResponseEntity.ok("Información creada correctamente");
    }

    @DeleteMapping("/info/del/{id}")
    public String deleteInfo(@PathVariable Long id) {
        if (infoService.existById(id)) {
            infoService.deleteById(id);
            return "Información eliminada exitosamente";
        }
        return "La información no existe";
    }

    @PutMapping("/info/edit/{id}")
    public ResponseEntity<String> editInfo(
            @PathVariable Long id,
            @RequestParam(required = false) String nombrecompleto,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) byte[] imagen) {

        Info info = infoService.find(id);

        if (info == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La información no existe");
        }

        if (StringUtils.hasText(nombrecompleto)) {
            info.setNombrecompleto(nombrecompleto);
        }

        if (StringUtils.hasText(titulo)) {
            info.setTitulo(titulo);
        }

        if (StringUtils.hasText(descripcion)) {
            info.setDescripcion(descripcion);
        }

        if (imagen != null) {
            info.setImagen(imagen);
        }

        infoService.save(info);

        return ResponseEntity.ok("Información editada correctamente");
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