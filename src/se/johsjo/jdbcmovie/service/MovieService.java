package se.johsjo.jdbcmovie.service;

import java.util.List;

import se.johsjo.jdbcmovie.model.*;
import se.johsjo.jdbcmovie.repository.MovieRepositoryImpl;

public class MovieService {
	MovieRepositoryImpl movieRepository;

	public MovieService(MovieRepositoryImpl movieRepository) {
		this.movieRepository = movieRepository;
	}

	public Object saveMovieObject(Object obj) {
		return movieRepository.saveMovieObject(obj);
	}

	public List<Movie> getMovies() {
		return movieRepository.getMovies();
	}

	public List<Actor> getActors() {
		return movieRepository.getActors();
	}

	public List<Genre> getGenre() {
		return movieRepository.getGenre();
	}

	public List<Review> getReviews() {
		return movieRepository.getReviews();
	}

	public List<ReviewUser> getReviewUsers() {
		return movieRepository.getReviewUsers();
	}

	public int deleteObject(Object obj) {
		return movieRepository.deleteObject(obj);
	}

}
