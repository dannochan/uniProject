package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieTest {

    private Movie movie;
    private Movie movie1;

    @BeforeEach
    public void setUp(){
        movie=new Movie("His dark Material", 1, "hd");
       movie1=new Movie("His dark Material", 1, "4k");
    }

    @Test
    public void setMovieShouldGiveOutRightQuality(){

        System.out.println(movie.getTitle());
        System.out.println(movie.getCharge(3));
        System.out.println(movie1.getTitle());
        System.out.println(movie1.getCharge(3));

    }

}
