package PNoKio.Server.service;

import PNoKio.Server.domain.Owner;
import PNoKio.Server.dto.OwnerDto;

public interface OwnerService{


    void create(OwnerDto ownerDto);
    Owner login(OwnerDto ownerDto);
}
