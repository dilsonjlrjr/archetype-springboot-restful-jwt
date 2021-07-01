package ${package}.resource;

import ${package}.model.dto.request.AuthLoginDtoRequest;
import ${package}.model.dto.request.AuthRefreshDtoRequest;
import ${package}.model.dto.response.AuthLoginDtoResponse;
import ${package}.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthResource {

    private final AuthService authService;

    @Autowired
    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<AuthLoginDtoResponse> doLogin(@RequestBody @Valid AuthLoginDtoRequest auth) {
        AuthLoginDtoResponse token = authService.authenticate(auth.getUsername(), auth.getPassword());
        return ResponseEntity.ok(token);
    }

    @PostMapping(value = "/refresh-token", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<AuthLoginDtoResponse> doRefreshToken(@RequestBody @Valid AuthRefreshDtoRequest auth) {

        AuthLoginDtoResponse token = authService.refreshToken(auth);
        return ResponseEntity.ok(token);
    }
}
