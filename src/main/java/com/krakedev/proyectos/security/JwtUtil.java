package com.krakedev.proyectos.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	private static final String CLAVE_SECRETA = "EstaEsUnaClaveSuperSecretaYMuyLarga1234567890!";

	private static final String EMISOR = "KrakeDevBackend";

	private static final long TIEMPO_EXPIRACION = 3600000;

	public static String generarToken(String username, String rol) {
		Algorithm algoritmo = Algorithm.HMAC256(CLAVE_SECRETA);
		long ahora = System.currentTimeMillis();

		return JWT.create()
				.withIssuer(EMISOR)
				.withSubject(username)
				.withIssuedAt(new Date(ahora))
				.withExpiresAt(new Date(ahora + TIEMPO_EXPIRACION))
				.withClaim("rol", rol)
				.sign(algoritmo);
	}

	public static DecodedJWT validarToken(String token) {
		try {
			Algorithm algoritmo = Algorithm.HMAC256(CLAVE_SECRETA);
			JWTVerifier verificador = JWT.require(algoritmo).withIssuer(EMISOR).build();
			return verificador.verify(token);
		} catch (Exception e) {
			logger.error("Error de validación del Token: " + e.getMessage());
			return null;
		}
	}
}