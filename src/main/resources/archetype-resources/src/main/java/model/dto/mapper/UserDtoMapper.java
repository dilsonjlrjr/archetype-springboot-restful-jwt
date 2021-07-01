package ${package}.model.dto.mapper;

import ${package}.model.dto.response.UserDtoResponse;
import ${package}.model.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserDtoMapper {
    UserDtoResponse userToUserDtoResponse(User user);
}
