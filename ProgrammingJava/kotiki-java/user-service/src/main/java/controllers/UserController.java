package controllers;

import dto.UserDto;
import dto.mappers.DtoToUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final DtoToUserMapper dtoToUserMapper;

    @Autowired
    public UserController(UserService userService, DtoToUserMapper dtoToUserMapper) {
        this.userService = userService;
        this.dtoToUserMapper = dtoToUserMapper;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public void save(@RequestBody UserDto user) {
        userService.save(dtoToUserMapper.convert(user));
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable int id) {
        try {
            userService.deleteById(id);
        }
        catch (Exception __) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
