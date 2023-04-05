package com.tvacher.shortener.repository;

import com.tvacher.shortener.model.entity.Url;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UrlRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UrlRepository urlRepository;

    @Before
    public void setupTest() {
        testEntityManager.persist(new Url("http://this-is-a-testing-url.com","d431b34a" ));
    }

    @Test
    public void testSaveNewUrl() {
        Url newUrl = new Url("http://this-is-another-testing-url.com","3b89f894" );
        urlRepository.save(newUrl);
        Assertions.assertNotNull(newUrl.getId());

        List<Url> allUrls = urlRepository.findAll();
        Assertions.assertNotNull(allUrls);
        Assertions.assertEquals(2, allUrls.size());
    }

    @Test
    public void testFindUrlByShortUrlExisting() {
        Url url = urlRepository.findUrlByShortUrl("d431b34a");
        Assertions.assertNotNull(url);
        Assertions.assertEquals("http://this-is-a-testing-url.com", url.getLongUrl());
    }

    @Test
    public void testFindUrlByShortUrlNotExisting() {
        Url url = urlRepository.findUrlByShortUrl("3b89f894");
        Assertions.assertNull(url);
    }

    @Test
    public void testFindUrlByLongUrlExisting() {
        Url url = urlRepository.findUrlByLongUrl("http://this-is-a-testing-url.com");
        Assertions.assertNotNull(url);
        Assertions.assertEquals("d431b34a", url.getShortUrl());
    }

    @Test
    public void testFindUrlByLongUrlNotExisting() {
        Url url = urlRepository.findUrlByLongUrl("http://this-is-another-testing-url.com");
        Assertions.assertNull(url);
    }
}
