package dto.mappers;

import dto.UserDto;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DtoToUserMapper {

    private final DtoToOwnerMapper dtoToOwnerMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DtoToUserMapper(DtoToOwnerMapper dtoToOwnerMapper, PasswordEncoder passwordEncoder) {
        this.dtoToOwnerMapper = dtoToOwnerMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User convert(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        user.setOwner(dtoToOwnerMapper.convert(userDto.getOwner()));
        return user;
    }
}
