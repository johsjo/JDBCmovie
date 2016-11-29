package se.johsjo.jdbcmovie.service;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import se.johsjo.jdbcmovie.model.*;
import se.johsjo.jdbcmovie.repository.MovieRepositoryException;
import se.johsjo.jdbcmovie.repository.SQLMovieRepository;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {
	Movie movie;
	Actor actor;
	
	MovieService movieService;

	@Before
	public void setUp() {
		movie = new Movie(-1, "Futurama II", "1996", "comedy");
		actor = new Actor(-1, "Kalle", "Nilsson");
		
		movieService = new MovieService(new SQLMovieRepository());
	}
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public final void testAllNoneExceptions() {

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
	
	@Test
	public void shouldThrowExceptionWhenDeleteObjectThatDoesNotExist(){
		
		expectedException.expect(MovieRepositoryException.class);
		expectedException.expectMessage("Couldn't delete the object from database");
		
		movieService.deleteObject(new Movie(-1,"test", "test","test"));
		
	}
}
