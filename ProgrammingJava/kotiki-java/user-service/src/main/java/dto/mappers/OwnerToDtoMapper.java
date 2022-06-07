package dto.mappers;

import dto.OwnerDto;
import models.Cat;
import models.Owner;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OwnerToDtoMapper {

    public OwnerDto convert(Owner owner) {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(owner.getId());
        ownerDto.setName(owner.getName());
        ownerDto.setBirthDate(owner.getBirthDate());
        ownerDto.setCats(owner.getCats().stream().map(Cat::getId).collect(Collectors.toSet()));
        return ownerDto;
    }
}
