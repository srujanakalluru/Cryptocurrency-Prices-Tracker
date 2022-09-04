package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class SwaggerController {

    /**
     * Sets the index page mapping to point to the Swagger UI.
     *
     * @return A redirect to the Swagger UI.
     */
    @GetMapping("/")
    public String index() {
        return "redirect:swagger-ui/";
    }
}
