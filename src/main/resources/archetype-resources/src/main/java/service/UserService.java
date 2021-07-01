package ${package}.service;

import ${package}.exception.EntityNotFoundException;
import ${package}.exception.code.EnumUserServiceCode;
import ${package}.exception.message.EnumUserServiceMessage;
import ${package}.mapper.UserMapper;
import ${package}.model.dto.mapper.UserDtoMapper;
import ${package}.model.dto.response.UserDtoResponse;
import ${package}.model.entities.User;
import ${package}.util.DatabaseOrderUtils;
import com.github.pagehelper.PageInfo;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.pagehelper.page.PageMethod.startPage;

@Service
public class UserService {

    private final UserMapper userMapper;

    private final DatabaseOrderUtils databaseOrderUtils;

    @Autowired
    public UserService(UserMapper userMapper,
                       DatabaseOrderUtils databaseOrderUtils) {
        this.userMapper = userMapper;
        this.databaseOrderUtils = databaseOrderUtils;
    }

    public List<User> getAll() {
        return userMapper.findAll();
    }

    public User getByUsername(String username) {
        return userMapper.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        EnumUserServiceMessage.ENTITY_NOT_FOUND.getMessage(),
                        EnumUserServiceCode.ENTITY_NOT_FOUND.getCode()));
    }

    public void updateSessionUser(Long id, String hashSession, String refreshToken) {
        userMapper.updateSessionUser(id, hashSession, refreshToken);
    }

    public User getById(Long id) {
        return userMapper.findById(id).orElseThrow(() -> new EntityNotFoundException(
                EnumUserServiceMessage.ENTITY_NOT_FOUND.getMessage(),
                EnumUserServiceCode.ENTITY_NOT_FOUND.getCode()));
    }

    public User getByUsername(String refreshToken, String hashSession) {
        return userMapper.findByRefreshTokenHashSession(refreshToken, hashSession)
                .orElseThrow(() -> new EntityNotFoundException(
                        EnumUserServiceMessage.ENTITY_NOT_FOUND.getMessage(),
                        EnumUserServiceCode.ENTITY_NOT_FOUND.getCode()));
    }

    public PageInfo<UserDtoResponse> doFindAllUserAndCreateDto(Pageable pageable) {
        String orderByDatabase = databaseOrderUtils.doCreateStringOrdebyDatabase(pageable, User.class);
        startPage(pageable.getPageNumber(), pageable.getPageSize(), orderByDatabase);

        List<User> users = this.getAll();

        return new PageInfo<>(users.parallelStream().map(Mappers.getMapper(UserDtoMapper.class)::userToUserDtoResponse)
                .collect(Collectors.toList()));
    }

    public UserDtoResponse doFindByIdAndCreateDto(Long id) {
        return Mappers.getMapper(UserDtoMapper.class).userToUserDtoResponse(this.getById(id));
    }
}
