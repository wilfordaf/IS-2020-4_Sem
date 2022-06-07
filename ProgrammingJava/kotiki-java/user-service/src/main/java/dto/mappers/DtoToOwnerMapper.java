package dto.mappers;

import dto.OwnerDto;
import models.Cat;
import models.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.CatService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DtoToOwnerMapper {

    private final CatService catService;

    @Autowired
    public DtoToOwnerMapper(CatService catService) {
        this.catService = catService;
    }

    public Owner convert(OwnerDto ownerDto) {
        Owner owner = new Owner();
        owner.setId(ownerDto.getId());
        owner.setName(ownerDto.getName());
        owner.setBirthDate(ownerDto.getBirthDate());
        Set<Cat> cats = new HashSet<>();
        if (ownerDto.getCats() != null) {
            cats = ownerDto.getCats().stream().map(catService::getById).collect(Collectors.toSet());
        }

        owner.setCats(cats);
        return owner;
    }
}
