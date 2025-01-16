package org.codejar.repository;


import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.codejar.models.Film;
import org.codejar.models.Film$;

import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class FilmRepository {

    @Inject
    private JPAStreamer jpaStreamer;

    private static final int PAGE_SIZE= 20;

    //in contoh yang menggunakan lambda
    public Optional<Film> getFilmId(Short filmId){
        return jpaStreamer.stream(Film.class)
                .filter(film -> film.getFilmId().equals(filmId))
                .findFirst();
    }


    //    //ini contoh yang menggunakan resource root dari class Film
//    public Optional<Film> getFilmId2(Short filmId){
//        return jpaStreamer.stream(Film.class)
//                .filter(Film$.filmId.equal(filmId))
//                .findFirst();
//    }

    public Stream<Film> getFilmLength(short minLength){
        return jpaStreamer.stream(Film.class)
                .filter(Film$.length.greaterThan(minLength))
                .sorted(Film$.length);
    }





    //paging
    public Stream<Film> paged(long page, short minLength) {
        //agar database lebih optimal kita menggunakan Projection.select(entity.field yang mau di select)
        return jpaStreamer.stream(Projection.select(Film$.filmId, Film$.title, Film$.length))
                .filter(Film$.length.greaterThan(minLength))
                .sorted(Film$.length)
                .skip(page * PAGE_SIZE)
                .limit(PAGE_SIZE);

    }


    //join to actor
    public  Stream<Film> actorsJoin(String startWith, short minLength){
        final StreamConfiguration<Film> sc = StreamConfiguration.of(Film.class).joining(Film$.actors);
        return jpaStreamer.stream(sc)
                .filter(Film$.title.startsWith(startWith).and(Film$.length.greaterThan(minLength)))
                .sorted(Film$.length.reversed());
    }


    //update
    @Transactional
    public void updateRentalRate(short minLength, double rentalRate){
        jpaStreamer.stream(Film.class)
                .filter(Film$.length.greaterThan(minLength))
                .forEach(film -> {
                    film.setRentalRate(rentalRate);
                });
    }


    //update
    @Transactional
    public void updateRentalRate2(short minLength, double rentalRate){
        jpaStreamer.stream(Film.class)
                .filter(Film$.length.greaterThan(minLength))
                .forEach(film -> {
                    film.setRentalRate(rentalRate);
                });
    }




}
