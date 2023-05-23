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
import com.hos.app.Entities.User;
import com.hos.app.Interface.IUserService;
import com.hos.app.Tokens.JwtUtil;

import jakarta.validation.Valid;


@RestController
public class UserController {

    @Autowired IUserService iUserService;


    @GetMapping("/users")
    public List<User> getUsers() {
        return iUserService.getList();
    }

//    --------------------
    @Autowired ObjectMapper objectMapper;
    
    @PostMapping("/user/add")
    public ResponseEntity<String> addUser(@Valid @RequestBody User user, BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            String errorMessage = getErrorMessage(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage+" tu json decia:"+objectMapper.writeValueAsString(user));
        }
        
        if (iUserService.existByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya existe");
        }
        String token = JwtUtil.generateToken(user.getUsername());
        iUserService.addToken(user, token);
        iUserService.saveUser(user);
        return ResponseEntity.ok("Usuario creado correctamente, su token es: "+user.getToken());
    }

    @DeleteMapping("/user/del/{id}")
    public String delUser(@PathVariable Long id) {
    	if (iUserService.existById(id)) {
    		iUserService.deleteById(id);
    		return "Usuario eliminado con exito";
    	}
    	return "El usuario no existe";
    }
    
    
    @PutMapping("/user/edit/{id}")
    public ResponseEntity<String> editUser(
            @PathVariable Long id,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String pass) {
        
        User user = iUserService.findUser(id);
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe");
        }
        
        if (StringUtils.hasText(username)) {
            if (!isValidUsername(username)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario no cumple con los requisitos");
            }
            if (iUserService.existByUsername(username)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario ya está en uso");
            }
            user.setUsername(username);
        }
        
        if (StringUtils.hasText(pass)) {
            if (!isValidPass(pass)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La contraseña no cumple con los requisitos");
            }
            user.setPass(pass);
        }
        
        iUserService.saveUser(user);
        
        return ResponseEntity.ok("Usuario editado correctamente");
    }
    

    
//    --------------------------
    private boolean isValidUsername(String username) {
        return username.matches("\\S+") && username.length() >= 1 && username.length() <= 20;
    }
    
    private boolean isValidPass(String pass) {
        return pass.matches("\\S+") && pass.length() >= 1 && pass.length() <= 20;
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String errorMessage = getErrorMessage(bindingResult);
        ErrorDetails errorDetails = new ErrorDetails(errorMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    private String getErrorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append(fieldError.getDefaultMessage()).append(", ");
        }

        return errorMessage.toString();
    }
    
//    ---------------------
      
}