package org.codejar.models;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Film {

    public Film() {
    }

    public Film(Short id, String title, Short length) {
        this.filmId = id;
        this.title = title;
        this.length = length;
    }

    /**
     * dalam menulis entity dalam spring boot maupun quarkus diusahakan memakai type Primitive
     * misal Short, Integer bukan short, integer
     * jika kita menggunakan type non primitive maka akan rawan terkena null pointer exeption
     */



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short filmId;

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "release_year")
    private Short releaseYear;

    @Basic
    @Column(name = "language_id", columnDefinition = "TINYINT UNSIGNED")
    private Short languageId;

    @Basic
    @Column(name = "original_language_id", columnDefinition = "TINYINT UNSIGNED")
    private Short originalLanguageId;

    @Basic
    @Column(name = "rental_duration")
    private Short rentalDuration;

    @Basic
    @Column(name = "rental_rate")
    private Double rentalRate;

    @Basic
    @Column(name = "length")
    private Short length;

    @Basic
    @Column(name = "replacement_cost")
    private Double replacementCost;

    @Basic
    @Column(name = "rating", columnDefinition = "enum('G', 'PG', 'PG-13', 'R', 'NC-17')")
    private Object rating;

    @Basic
    @Column(name = "special_features", columnDefinition = "set('Trailers', 'Commentaries' ,'Deleted Scenes', 'Behind the Scenes')")
    private Object specialFeatures;

    @Basic
    @Column(name = "last_update")
    private Timestamp lastUpdate;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "film_actor",
            joinColumns = {
                    @JoinColumn(name = "film_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "actor_id")
            }
    )
    private List<Actor> actors = new ArrayList<>();


}
