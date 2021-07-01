package ${package}.resource;

import ${package}.model.dto.response.UserDtoResponse;
import ${package}.service.SecurityResourceService;
import ${package}.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersResource {

    private static final String ID_USERNAME = "ID_USERNAME";

    private final UserService service;

    private final SecurityResourceService securityResourceService;

    @Autowired
    public UsersResource(UserService service, SecurityResourceService securityResourceService) {
        this.service = service;
        this.securityResourceService = securityResourceService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageInfo<UserDtoResponse>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(service.doFindAllUserAndCreateDto(pageable));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDtoResponse> getId(@PathVariable("id") Long id, @RequestAttribute(ID_USERNAME) Long idToken) {
        securityResourceService.doValidateIdTokenAccessResource(id, idToken);
        return ResponseEntity.ok(service.doFindByIdAndCreateDto(id));
    }
}
