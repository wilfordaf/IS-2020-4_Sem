package dto.mappers;

import dto.CatDto;
import models.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.CatService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DtoToCatMapper {

    private final CatService catService;
    private final DtoToOwnerMapper dtoToOwnerMapper;

    @Autowired
    public DtoToCatMapper(CatService catService, DtoToOwnerMapper dtoToOwnerMapper) {
        this.catService = catService;
        this.dtoToOwnerMapper = dtoToOwnerMapper;
    }

    public Cat convert(CatDto catDto) {
        Cat cat = new Cat();
        cat.setId(catDto.getId());
        cat.setName(catDto.getName());
        cat.setBirthDate(catDto.getBirthDate());
        cat.setBreed(catDto.getBreed());
        cat.setColor(catDto.getColor());
        cat.setOwner(dtoToOwnerMapper.convert(catDto.getOwner()));
        Set<Cat> friends = new HashSet<>();
        if (catDto.getFriends() != null) {
            friends = catDto.getFriends().stream().map(catService::getById).collect(Collectors.toSet());
        }

        cat.setFriends(friends);
        cat.setFriendOf(friends);
        return cat;
    }
}
