package com.xpresspayments.airtime.vtu;

import com.xpresspayments.airtime.vtu.model.entity.AppUser;
import com.xpresspayments.airtime.vtu.model.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class AppUserRepoTests {
    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    public void shouldSaveUser(){
        AppUser appUser = getAppUser();
        AppUser savedUser = appUserRepository.save(appUser);
        assertThat(savedUser).usingRecursiveComparison().ignoringFields("id").isEqualTo(appUser);
    }

    private AppUser getAppUser() {
        AppUser appUser = new AppUser();
        appUser.setFullName("Test");
        appUser.setEmail("Test");
        appUser.setPhoneNo("Test");
        appUser.setPasswordHash("Test");
        return appUser;
    }
}
