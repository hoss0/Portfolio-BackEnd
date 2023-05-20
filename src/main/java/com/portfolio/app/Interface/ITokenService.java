package com.portfolio.app.Interface;

import org.springframework.security.core.Authentication;

public interface ITokenService {


	 public String generateToken(Authentication authentication);
}
