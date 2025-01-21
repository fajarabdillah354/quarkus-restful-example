package org.codejar.resource;


import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.codejar.models.Film;
import org.codejar.repository.FilmRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Path("/")
public class FilmResource {

    @Inject
    private FilmRepository filmRepository;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello(){
        return "hello my name is fajar abdillah ahmad";
    }


    @GET
    @Path("filmId/{filmId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFilm(@PathParam("filmId") Short filmId){
        Optional<Film> film = filmRepository.getFilmId(filmId);
        return film.map(Film::getTitle)
                .orElse("Film NOT FOUND");
    }


    @GET
    @Path("pageFilms/{page}/{minLength}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPaged(@PathParam("page") long page, @PathParam("minLength") short minLength){
        return filmRepository.paged(page, minLength)
                .map(f -> String.format("%s (%d min)", f.getTitle(), f.getLength()))
                .collect(Collectors.joining("\n"));
    }

    @GET
    @Path("actors/{startWith}/{minLength}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getJoinActor(@PathParam("startWith") String startWith, @PathParam("minLength") short minLength){
        return filmRepository.actorsJoin(startWith, minLength)
                .map(film -> String.format("%s (%d min) : %s",
                        film.getTitle(),
                        film.getLength(),
                        film.getActors().stream()
                                .map(actor -> String.format("%s %s", actor.getFirstName(), actor.getLastName()))
                                .collect(Collectors.joining(", "))))
                .collect(Collectors.joining("\n "));

    }


    //this for updates minLength and rentalRate

    @GET
    @Path("update/{minLength}/{rentalRate}")
    @Produces(MediaType.TEXT_PLAIN)
    public String update(@PathParam("minLength") short minLength, @PathParam("rentalRate") double rentalRate){
        filmRepository.updateRentalRate(minLength, rentalRate);
        return filmRepository.getFilmLength(minLength)
                .map(film -> String.format("%s (%d min) - %f", film.getTitle(), film.getLength(), film.getRentalRate()))
                .collect(Collectors.joining("\n"));
    }


}
