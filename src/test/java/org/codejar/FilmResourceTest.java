package org.codejar;


import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class FilmResourceTest {

    @Test
    public void getContainsFilmByTrueId() {
        /**
         * kita mengetest method get() untuk mengecek apakah title dari film yang dicari ada di database
         * /filmId/5 endpoint ini ada pada ...Resource.java dimana di mehthod itu digunakan untuk mengambil title film berdasarkan ID
         * contansString("") jika yang ada dalam parameter terdapat di database dan sesuai dengan endpoint yang dipanggil dimana endpoint disini mengambil title berdasarkan id
         * maka parameter yang ada di containsString() adalah expedted dari title yang akan dicari
         */
        given()
                .when()
                .get("/filmId/5")//method yang akan digunakan sebagai testing
                .then()
                .statusCode(200)
                .body(containsString("AFRICAN EGG"));
    }


    @Test
    public void getNotContainsFilmById() {

        //ini akan error karna pada Id 5 judul film bukanlah SCORPION KING

        given()
                .when()
                .get("/filmId/5")//method yang akan digunakan sebagai testing
                .then()
                .statusCode(200)
                .body(containsString("AFRICAN EGG"));
    }


}
