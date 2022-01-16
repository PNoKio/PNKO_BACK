package PNoKio.Server.service;

import PNoKio.Server.domain.Owner;
import PNoKio.Server.dto.LoginDto;
import PNoKio.Server.dto.OwnerDto;
import PNoKio.Server.exception.EmailDuplicateException;
import PNoKio.Server.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerServiceImpl implements OwnerService{

    private final OwnerRepository ownerRepository;


    @Override
    @Transactional
    public Long create(OwnerDto ownerDto) throws RuntimeException{
        ownerRepository.findByEmail(ownerDto.getEmail())
                .ifPresent(
                        m -> {
                            throw new EmailDuplicateException("이미 있는 아이디 입니다.");
                        }
                );
        Owner owner = Owner.builder()
                .ownerName(ownerDto.getOwnerName())
                .email(ownerDto.getEmail())
                .password(ownerDto.getPassword())
                .build();
        return ownerRepository.save(owner).getId();
    }

    @Override
    @Transactional
    public Owner login(LoginDto loginDto) {
        Owner owner = ownerRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> {
            throw new IllegalStateException("아이디나 비밀번호가 틀렸습니다.");
        });
        if(!owner.getPassword().equals(loginDto.getPassword())){
            throw new IllegalStateException("아이디나 비밀번호가 틀렸습니다.");
        }
        return owner;
    }
}
