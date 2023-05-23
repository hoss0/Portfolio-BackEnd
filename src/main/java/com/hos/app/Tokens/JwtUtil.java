package com.hos.app.Tokens;


import com.hos.app.Interface.IUserService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

public class JwtUtil {
	@Autowired static IUserService userService;
    private static final String SECRET_KEY = "secret-key"; // Clave secreta para firmar el token
    private static final long EXPIRATION_TIME = 15 * 60 * 1000; // Tiempo de expiración del token en milisegundos

    public static String generateToken(String username) {
        try {
            // Crear los claims del token
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .build();

            // Crear el objeto SignedJWT con los claims
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

            // Firmar el token con la clave secreta
            JWSSigner signer = new MACSigner(SECRET_KEY);
            signedJWT.sign(signer);

            // Serializar el token como una cadena JWT
            return signedJWT.serialize();
        } catch (Exception e) {
            // Error al generar el token
            e.printStackTrace();
            return null;
        }
    }

    public static boolean validateToken(String token) {
    	
        try {
        	JWT jwt = JWTParser.parse(token);
            // Crear un objeto JWSVerifier con la clave secreta
            JWSVerifier verifier = new MACVerifier(SECRET_KEY);

            // Verificar la firma del token
            SignedJWT signedJWT = SignedJWT.parse(token);
            if (!signedJWT.verify(verifier)) {
                // La firma del token no es válida
                return false;
            }

            // Verificar la expiración del token
            if (signedJWT.getJWTClaimsSet().getExpirationTime().before(new Date())) {
                // El token ha expirado
                return false;
            }

         // Validar la existencia del usuario (consulta a la base de datos u otro origen de datos)
            String username = jwt.getJWTClaimsSet().getSubject();
            boolean user = userService.existByUsername(username);
            if (user == false) {
                return false; // El usuario no existe
            }

            // El token es válido y el usuario existe
            return true;
        } catch (Exception e) {
            return false; // Error al validar el token
        }
    }
}
