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
import com.portfolio.app.Interface.ISkillsService;
import com.portfolio.app.Interface.IUserService;
import com.portfolio.app.model.entities.ErrorDetails;
import com.portfolio.app.model.entities.Skills;

import jakarta.validation.Valid;


@RestController
public class SkillsController {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ISkillsService skillsService;

    @Autowired
    IUserService userService;

    @GetMapping("/skills")
    public List<Skills> getSkills() {
        return skillsService.getList();
    }

    @PostMapping("/skills/add")
    public ResponseEntity<String> addSkills(@Valid @RequestBody Skills skills, BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            String errorMessage = getErrorMessage(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage + " tu JSON decía:" + objectMapper.writeValueAsString(skills));
        }

        // Verificar si el campo userid es nulo
        if (skills.getUserid() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El campo userid no puede ser nulo");
        }

        Long userId = skills.getUserid().getId();  // Obtén el ID de usuario de las habilidades

        // Verificar si el ID de usuario corresponde a un usuario existente
        if (!userService.existById(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ID no corresponde a ningún usuario");
        }

        skillsService.save(skills);
        return ResponseEntity.ok("Habilidades creadas correctamente");
    }

    @DeleteMapping("/skills/del/{id}")
    public String deleteSkills(@PathVariable Long id) {
        if (skillsService.existById(id)) {
            skillsService.deleteById(id);
            return "Habilidades eliminadas exitosamente";
        }
        return "Las habilidades no existen";
    }

    @PutMapping("/skills/edit/{id}")
    public ResponseEntity<String> editSkills(
            @PathVariable Long id,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Double cantidad) {

        Skills skills = skillsService.find(id);

        if (skills == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Las habilidades no existen");
        }

        if (StringUtils.hasText(nombre)) {
            skills.setNombre(nombre);
        }

        if (cantidad != null) {
            skills.setCantidad(cantidad);
        }

        skillsService.save(skills);

        return ResponseEntity.ok("Habilidades editadas correctamente");
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
