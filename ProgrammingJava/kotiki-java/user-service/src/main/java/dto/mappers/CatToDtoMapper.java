package dto.mappers;

import dto.CatDto;
import models.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CatToDtoMapper {

    private final OwnerToDtoMapper ownerToDtoMapper;

    @Autowired
    public CatToDtoMapper(OwnerToDtoMapper ownerToDtoMapper) {
        this.ownerToDtoMapper = ownerToDtoMapper;
    }

    public CatDto convert(Cat cat) {
        CatDto catDto = new CatDto();
        catDto.setId(cat.getId());
        catDto.setName(cat.getName());
        catDto.setBirthDate(cat.getBirthDate());
        catDto.setBreed(cat.getBreed());
        catDto.setColor(cat.getColor());
        catDto.setOwner(null);
        if (cat.getOwner() != null) {
            catDto.setOwner(ownerToDtoMapper.convert(cat.getOwner()));
        }
        catDto.setFriends(cat.getFriends().stream().map(Cat::getId).collect(Collectors.toSet()));
        return catDto;
    }
}
