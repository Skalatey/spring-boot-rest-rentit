package com.rentit.repository;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import com.rentit.entity.Address;
import com.rentit.entity.Flat;
import com.rentit.entity.Street;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FlatRepositoryTest {

    @Resource
    private TestEntityManager entityManager;

    @Resource
    private FlatRepository repository;

    @Test
    public void testWhenGetValidIdOfStreetThenReturnFlats() throws Exception {

        getSliceFlats(10, new PageRequest(0, 4, new Sort(Sort.Direction.ASC, "id")));

        getSliceFlats(58, new PageRequest(2, 15, new Sort(Sort.Direction.DESC, "id")));

        getSliceFlats(100, new PageRequest(8, 10));
    }

    @Test
    public void testWhenGetNullIdOfStreetThenReturnZero() throws Exception {
        Pageable pageable = new PageRequest(1, 1);
        Page<Flat> byIdStreet = repository.findByIdStreet(null, pageable);
        MatcherAssert.assertThat(byIdStreet.getTotalElements(), is(0L));
    }

    private void getSliceFlats(int generateNumberFlats, Pageable pageable) {
        Street street = createFlatsForStreet(ThreadLocalRandom.current().nextLong(1, 20), generateNumberFlats);
        List<Flat> flats = street.getFlats();

        if (Objects.nonNull(pageable.getSort())) {
            String order = StreamSupport.stream(pageable.getSort().spliterator(), false)
                    .map(o -> o.getDirection().toString())
                    .findFirst().orElse("");

            if (Objects.equals(order, Sort.Direction.ASC.name())) {
                flats.sort(Comparator.comparing(Flat::getId));
            } else {
                flats.sort(Comparator.comparing(Flat::getId).reversed());
            }
        }

        Page<Flat> page = null;
        List<Flat> tempFlats = new ArrayList<>();
        int count = 0;
        int pageSize = pageable.getPageSize();
        int index = pageable.getPageNumber() * pageSize;

        for (int i = index; i < flats.size(); i++) {
            count++;
            tempFlats.add(flats.get(i));
            if (count == pageSize) {
                page = checkFlatsFromRequest(street.getId(), pageable, tempFlats, count);
                pageable = page.nextPageable();
                tempFlats.clear();
                count = 0;
            } else if (i == flats.size() - 1) {
                checkFlatsFromRequest(street.getId(), pageable, tempFlats, count);
            }
        }
    }

    private Page<Flat> checkFlatsFromRequest(Long idStreet,
                                       Pageable pageable,
                                       List<Flat> tempFlats,
                                       int count) {
        Page<Flat> page = repository.findByIdStreet(idStreet, pageable);
        List<Flat> content = page.getContent();
        MatcherAssert.assertThat(page, is(notNullValue()));
        MatcherAssert.assertThat(content, hasSize(count));
        MatcherAssert.assertThat(content, equalTo(tempFlats));
        return page;
    }

    private Street createFlatsForStreet(Long idStreet, int generateNumberFlats) {
        Street street = this.entityManager.find(Street.class, idStreet);
        IntStream.range(0, generateNumberFlats).boxed().map(m -> {
            Flat flat = new Flat();
            Random random = new Random();
            flat.setDescription(random.nextInt(3) + " room");
            flat.setPrice(new BigDecimal(String.valueOf(random.nextDouble() * 100000)));
            Address address = new Address();
            address.setMetro("Текстильщики");
            address.setHouseNumber(random.nextInt(1000) + "K");
            flat.setAddress(address);
            return flat;
        }).forEach(flat -> {
            flat.getAddress().setStreet(street);
            street.getFlats().add(flat);
        });
        entityManager.flush();
        return street;
    }
}