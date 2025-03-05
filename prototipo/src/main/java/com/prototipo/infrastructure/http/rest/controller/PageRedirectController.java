package com.prototipo.infrastructure.http.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageRedirectController {

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

}
