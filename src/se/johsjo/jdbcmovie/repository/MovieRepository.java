package se.johsjo.jdbcmovie.repository;

import java.util.List;

import se.johsjo.jdbcmovie.model.Actor;
import se.johsjo.jdbcmovie.model.Genre;
import se.johsjo.jdbcmovie.model.Movie;
import se.johsjo.jdbcmovie.model.Review;
import se.johsjo.jdbcmovie.model.ReviewUser;

public interface MovieRepository {

	Object saveMovieObject(Object obj);

	List<Movie> getMovies();

	List<Actor> getActors();

	List<Genre> getGenre();

	List<Review> getReviews();

	List<ReviewUser> getReviewUsers();

	int deleteObject(Object obj);

}
