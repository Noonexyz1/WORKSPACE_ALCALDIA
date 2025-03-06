package com.prototipo.infrastructure.http.rest.controller;

import com.prototipo.application.port.in.AutenticacionService;
import com.prototipo.domain.model.Credencial;
import com.prototipo.domain.model.Usuario;
import com.prototipo.infrastructure.http.rest.model.request.CredencialRequest;
import com.prototipo.infrastructure.http.rest.model.request.NuevoPassRequest;
import com.prototipo.infrastructure.http.rest.model.response.UsuarioResponse;
import com.prototipo.infrastructure.persistence.db.adapter.CredencialImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 86400)
@RestController
@RequestMapping(path = "/autenticacion")
public class AutenticacionController {

    @Autowired
    private AutenticacionService autenticacionService;
    @Autowired
    private CredencialImpl credencial;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping(
            path = {"/iniciarSesion"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsuarioResponse> iniciarSesion(
            @RequestBody CredencialRequest credencialRequest, HttpServletRequest request){

        UserDetails userDetails = credencial.loadUserByUsername(credencialRequest.getCi());

        if (userDetails != null && passwordEncoder.matches(credencialRequest.getPass(), userDetails.getPassword())) {
        //if (userDetails != null) {
            // Autenticar manualmente
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authToken);

            // Asignar sesión a Spring Security
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);


            Usuario usuario = autenticacionService
                    .iniciarSesion(credencialRequest.getCi());

            UsuarioResponse response = UsuarioResponse
                    .builder()
                    .id(usuario.getId())
                    .nombres(usuario.getNombres())
                    .paterno(usuario.getPaterno())
                    .materno(usuario.getMaterno())
                    .ci(usuario.getCi())
                    .correo(usuario.getCorreo())
                    .nombreRol(usuario.getNombreRol())
                    .nombreUnidad(usuario.getNombreUnidad())
                    .nombreCargo(usuario.getNombreCargo())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(
            path = {"/cambiarPass"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void cambiarPass(@RequestBody NuevoPassRequest request){
        Credencial credencial = Credencial.builder()
                .ci(request.getCi())
                .pass(request.getPass())
                .build();

        autenticacionService.cambiarPass(
                credencial,
                request.getNuevoPass()
        );

        /*var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {

        }*/
    }

    @GetMapping(
            path = {"/cerrarSesion"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void cerrarSesion(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Cierra la sesión
        }

        SecurityContextHolder.clearContext(); // Borra la autenticación en memoria

        // Invalida la cookie de sesión JSESSIONID
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Pon `true` si usas HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expira inmediatamente
        response.addCookie(cookie);

        autenticacionService.cerrarSesion();
    }
}
