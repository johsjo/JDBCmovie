package se.johsjo.jdbcmovie.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.johsjo.jdbcmovie.model.*;
import se.johsjo.jdbcmovie.repository.SQLMovieRepository;

public class MovieServiceTest {
	Movie movie;
	Actor actor;

	@Before
	public void setUp() {
		movie = new Movie(-1, "Futurama II", "1996", "comedy");
		actor = new Actor(-1, "Kalle", "Nilsson");
	}

	@Test
	public final void test() {

		MovieService movieService = new MovieService(new SQLMovieRepository());

		// MovieTest
		Movie returnedMovie = (Movie) movieService.saveMovieObject(movie);
		System.out.println(returnedMovie.getId());
		List<Movie> listMovies = movieService.getMovies();
		listMovies.forEach(o -> System.out.println(o.getTitle() + " " + o.getGenre()));

		// ActorTest
		Actor reActor = (Actor) movieService.saveMovieObject(actor);
		System.out.println(reActor.getId());
		List<Actor> listActors = movieService.getActors();
		listActors.forEach(o -> System.out.println(o.getFirst_name()));

		movieService.deleteObject(returnedMovie);
		movieService.deleteObject(reActor);

	}

}
