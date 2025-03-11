package com.prototipo.infrastructure.http.rest.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageRedirectController implements ErrorController {

    @RequestMapping(
            value = {
                    "/{path:[^\\.]*}",
                    "/responsable/{path:[^\\.]*}",
                    "/solicitante/{path:[^\\.]*}",
                    "/administrador/{path:[^\\.]*}"
            },
            method = {
                    RequestMethod.GET,
                    RequestMethod.POST
            })
    public String redirectToIndex() {
        return "forward:/index.html";
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "redirect:/error-403.html"; // Página personalizada
            }
        }
        return "redirect:/error-general.html"; // Otra página de error
    }

}
