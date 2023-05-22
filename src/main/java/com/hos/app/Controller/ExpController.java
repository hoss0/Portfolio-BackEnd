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

import jakarta.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hos.app.Entities.ErrorDetails;
import com.hos.app.Entities.Exp;
import com.hos.app.Interface.IExpService;
import com.hos.app.Interface.IUserService;




@RestController
public class ExpController {
	 @Autowired ObjectMapper objectMapper;
    @Autowired IExpService expService;
    @Autowired IUserService iUserService;

    @GetMapping("/exps")
    public List<Exp> getExps() {
        return expService.getList();
    }

    @PostMapping("/exp/add")
    public ResponseEntity<String> addExp(@Valid @RequestBody Exp exp, BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            String errorMessage = getErrorMessage(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage + " tu JSON decía:" + objectMapper.writeValueAsString(exp));
        }

        // Verificar si el campo userid es nulo
        if (exp.getUserid() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El campo userid no puede ser nulo");
        }

        Long userId = exp.getUserid().getId();  // Obtén el ID de usuario de la experiencia

        // Verificar si el ID de usuario corresponde a un usuario existente
        if (!iUserService.existById(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ID no corresponde a ningún usuario");
        }

        expService.save(exp);
        return ResponseEntity.ok("Experiencia creada correctamente");
    }


    @DeleteMapping("/exp/del/{id}")
    public String deleteExp(@PathVariable Long id) {
        if (expService.existById(id)) {
            expService.deleteById(id);
            return "Experiencia eliminada exitosamente";
        }
        return "La experiencia no existe";
    }

    @PutMapping("/exp/edit/{id}")
    public ResponseEntity<String> editExp(
            @PathVariable Long id,
            @RequestParam(required = false) String titulo_empresa,
            @RequestParam(required = false) String titulo_puesto,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) String periodo) {

        Exp exp = expService.find(id);

        if (exp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La experiencia no existe");
        }

        if (StringUtils.hasText(titulo_empresa)) {
            exp.setTitulo_empresa(titulo_empresa);
        }

        if (StringUtils.hasText(titulo_puesto)) {
            exp.setTitulo_puesto(titulo_puesto);
        }

        if (StringUtils.hasText(descripcion)) {
            exp.setDescripcion(descripcion);
        }

        if (StringUtils.hasText(periodo)) {
            exp.setPeriodo(periodo);
        }

        expService.save(exp);

        return ResponseEntity.ok("Experiencia editada correctamente");
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
