package com.epam.smartcityhousingsystem.repositories;

import com.epam.smartcityhousingsystem.entities.AdvertisementArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jakhongir Rasulov on 11/25/21
 * @project smartcity-housing-system
 */
@ActiveProfiles({"test"})
@DataJpaTest
@Import(BCryptPasswordEncoder.class)
class AdvertisementArchiveRepositoryTest {

    @Autowired
    AdvertisementArchiveRepository advertisementArchiveRepository;

    @BeforeEach
    void setUp() {

        AdvertisementArchive archive = new AdvertisementArchive();
        archive.setResidentCode(12345);
        archive.setTitle("title");
        archive.setPhone("999999");
        archive.setPrice(123);
        archive.setDescription("description");
        archive.setMoneyTransferUUID("sfhskjf");
        archive.setId(1);


        advertisementArchiveRepository.save(archive);
    }

    @Test
    @DisplayName("tests retrieving advertisement archive with a correct advertisement archive uuid")
    void testRetrievingArchiveWithUUIDSuccess() {
        // uuid is generated by the server due to @PrePersist annotation,
        // therefore, the advertisement is retrieved first, and then,
        // the method is checked,
        AdvertisementArchive archive = advertisementArchiveRepository.findAll().get(0);

        Optional<AdvertisementArchive> archiveOptional = advertisementArchiveRepository.findByUuid(archive.getUuid());
        assertAll("tests archive object",
                ()->{
                    assertTrue(archiveOptional.isPresent());
                },
                ()->{
                    assertEquals(12345,archiveOptional.get().getResidentCode());
                },
                ()->{
                    assertEquals("title",archiveOptional.get().getTitle());
                }
          );
    }

    @Test
    @DisplayName("tests retrieving advertisement archive with an incorrect advertisement archive uuid")
    void testRetrievingArchiveWithIncorrectUUID() {

        Optional<AdvertisementArchive> archiveOptional = advertisementArchiveRepository.findByUuid("this_is_not_correct_UUID");
        assertFalse(archiveOptional.isPresent());
    }

    @Test
    @DisplayName("tests deleting advertisement archive with a correct uuid")
    void deleteAdvertisementArchiveWithCorrectUUID() {
        // uuid is generated by the server due to @PrePersist annotation,
        // therefore, the advertisement is retrieved first, and then,
        // the method is checked
        AdvertisementArchive archive = advertisementArchiveRepository.findAll().get(0);
        Integer deletedAmount = advertisementArchiveRepository.deleteByUuid(archive.getUuid());
        assertEquals(1,deletedAmount);
    }

    @Test
    @DisplayName("tests deleting advertisement archive with an incorrect uuid")
    void deleteAdvertisementArchiveWithIncorrectUUID() {

        Integer deletedAmount = advertisementArchiveRepository.deleteByUuid("incorrect_uuid)");
        assertEquals(0,deletedAmount);
    }
}