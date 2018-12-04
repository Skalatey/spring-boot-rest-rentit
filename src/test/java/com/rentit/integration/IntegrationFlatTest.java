package com.rentit.integration;

import com.rentit.dto.FlatDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationFlatTest {

    private static final int UNKNOWN_ID = Integer.MAX_VALUE;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void when_get_all_flats_by_available_id_street_then_success() throws Exception {

        ResponseEntity<FlatDTO[]> forEntity = restTemplate.getForEntity("/steets/2", FlatDTO[].class);

        List<FlatDTO> flats = Arrays.asList(forEntity.getBody());

        FlatDTO flatDTO = flats.get(0);

        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(flatDTO.getDescription()).isEqualTo("Best flat");
        assertThat(flatDTO.getId()).isEqualTo(22L);
        assertThat(flatDTO.getMetro()).isEqualTo("Kutuzovkay");
    }

    @Test
    public void when_get_all_flats_by_unknown_street_then_fail_404_no_content() throws Exception {
        try {
            restTemplate.getForEntity("/steets/" + UNKNOWN_ID, FlatDTO.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(e.getMessage()).isEqualTo("For street with " + UNKNOWN_ID + ", flats are not found");
        }
    }
}
