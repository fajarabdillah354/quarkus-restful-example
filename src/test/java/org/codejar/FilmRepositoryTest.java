package org.codejar;


import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.codejar.models.Film;
import org.codejar.repository.FilmRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@QuarkusTest
public class FilmRepositoryTest {


    /**
     * selain mengetest resource/controller  kita juga bisa mengetest repositorynya atau servicenya
     * namun kita perlu meng inject class repository yang akan di test methodnya
     */

    @Inject
    private FilmRepository filmRepository;


    @Test
    void testFilmRepository() {
        Optional<Film> film = filmRepository.getFilmId((short) 5);
        Assertions.assertTrue(film.isPresent());
        Assertions.assertEquals("AFRICAN EGG", film.get().getTitle());

    }


}
