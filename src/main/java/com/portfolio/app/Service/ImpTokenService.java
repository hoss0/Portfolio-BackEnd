package com.portfolio.app.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.portfolio.app.Interface.ITokenService;

@Service
public class ImpTokenService implements ITokenService{

  
private final JwtEncoder encoder;
	
  public ImpTokenService(JwtEncoder encoder) {
    this.encoder = encoder;
  }
  
  @Override
  public String generateToken(Authentication authentication){

    /*
     * Creamos las claims o payloads para la token
     */

    JwtClaimsSet claims = JwtClaimsSet.builder()
    .claim("scope","test")
    .build();
    
    /*
     * Codificamos la token e insertamos los payloads
     */

    return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

}