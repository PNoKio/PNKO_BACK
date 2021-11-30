package PNoKio.Server.service;

import PNoKio.Server.domain.Owner;
import PNoKio.Server.dto.OwnerDto;
import PNoKio.Server.exception.EmailDuplicateException;
import PNoKio.Server.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerServiceImpl implements OwnerService{

    private final PasswordEncoder passwordEncoder;
    private final OwnerRepository ownerRepository;


    @Override
    public void create(OwnerDto ownerDto) throws RuntimeException{
        String encodePassword = passwordEncoder.encode(ownerDto.getPassword());

        ownerRepository.findByEmail(ownerDto.getEmail())
                .ifPresent(
                        m -> {
                            throw new EmailDuplicateException("이미 있는 아이디 입니다.");
                        }
                );
        Owner owner = Owner.builder()
                .ownerName(ownerDto.getOwnerName())
                .email(ownerDto.getEmail())
                .password(encodePassword)
                .build();
        ownerRepository.save(owner);
    }

    @Override
    public Owner login(OwnerDto ownerDto) {
        return ownerRepository.findByEmail(ownerDto.getEmail())
                .orElse(null);

    }
}
