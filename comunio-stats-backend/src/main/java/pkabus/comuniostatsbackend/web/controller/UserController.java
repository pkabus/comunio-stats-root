package pkabus.comuniostatsbackend.web.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/principal")
public class UserController {

    @GetMapping
    public Principal retrievePrincipal(final Principal principal) {
        return principal;
    }
}
