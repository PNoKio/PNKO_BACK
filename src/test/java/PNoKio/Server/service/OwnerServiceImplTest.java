package PNoKio.Server.service;

import PNoKio.Server.domain.Owner;
import PNoKio.Server.dto.LoginDto;
import PNoKio.Server.dto.OwnerDto;
import PNoKio.Server.exception.EmailDuplicateException;
import PNoKio.Server.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OwnerServiceImplTest {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    OwnerService ownerService;

    @Test
    void repositoryTest(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        Owner owner2 = makeOwner("lee", "kim2299", "asdff");
        ownerRepository.save(owner1);
        ownerRepository.save(owner2);

        Owner findOwner1 = ownerRepository.findByEmail("rkdlem48").get();
        assertThat(findOwner1.getOwnerName()).isEqualTo(owner1.getOwnerName());

        Owner findOwner2 = ownerRepository.findByEmail("kim2299").get();
        assertThat(findOwner2.getPassword()).isEqualTo(owner2.getPassword());
    }

    @Test
    void ownerServiceCreateSuccessTest(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        Owner owner2 = makeOwner("lee", "kim2299", "asdff");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(),owner1.getPassword()));
        ownerService.create(new OwnerDto(owner2.getEmail(),owner2.getOwnerName(),owner2.getPassword()));

        Owner findOwner1 = ownerRepository.findByEmail("rkdlem48").get();
        assertThat(findOwner1.getOwnerName()).isEqualTo(owner1.getOwnerName());

        Owner findOwner2 = ownerRepository.findByEmail("kim2299").get();
        assertThat(findOwner2.getPassword()).isEqualTo(owner2.getPassword());

    }

    @Test
    void ownerServiceCreateFailTest(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(),owner1.getPassword()));

        assertThatThrownBy(() -> ownerService.create(new OwnerDto(owner1.getEmail()
                ,owner1.getOwnerName()
                , owner1.getPassword()))).isInstanceOf(EmailDuplicateException.class);
    }

    @Test
    void ownerServiceLoginSuccessTest(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(),owner1.getPassword()));
        Owner login = ownerService.login(new LoginDto(owner1.getEmail(), owner1.getPassword()));
        assertThat(login.getEmail()).isEqualTo(owner1.getEmail());
    }
    @Test
    void ownerServiceLoginIdFailTest(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(),owner1.getPassword()));
        assertThatThrownBy(() -> ownerService.login(new LoginDto(owner1.getEmail(), owner1.getPassword())))
                .isInstanceOf(IllegalStateException.class);
    }
    @Test
    void ownerServiceLoginPasswordFailTest(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(),owner1.getPassword()));
        assertThatThrownBy(() -> ownerService.login(new LoginDto(owner1.getEmail(), owner1.getPassword())))
                .isInstanceOf(IllegalStateException.class);
    }

    private Owner makeOwner(String ownerName,String email, String password) {
        Owner owner = Owner.builder()
                .ownerName(ownerName)
                .email(email)
                .password(password)
                .build();
        return owner;
    }
}