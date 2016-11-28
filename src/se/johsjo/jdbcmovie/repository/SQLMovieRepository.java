package se.johsjo.jdbcmovie.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import se.johsjo.jdbcmovie.model.*;

public class SQLMovieRepository implements MovieRepository {
	private static final String CONNECTION_URL = "jdbc:mysql://localhost/MovieDatabase?useSSL=false";

	private static final String SAVE_MOVIES = "INSERT INTO Movies VALUES (null , ? , ? , ? )";
	private static final String SAVE_ACTOR = "INSERT INTO Actors VALUES (null , ? , ? )";
	private static final String GET_GENRE_ID = "SELECT * FROM genre WHERE genre = ? ";

	@Override
	public Object saveMovieObject(Object obj) {

		try (Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "mysql")) {

			connection.setAutoCommit(false);

			if (obj instanceof Movie) {
				return saveMovie((Movie) obj, connection);
			} else if (obj instanceof Actor) {
				return saveActor((Actor) obj, connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new MovieRepositoryException("Counldn't save " + obj.getClass().getSimpleName());
	}

	private Movie saveMovie(Movie movie, Connection connection) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(SAVE_MOVIES, Statement.RETURN_GENERATED_KEYS)) {

			statement.setString(1, movie.getTitle());
			statement.setString(2, movie.getProduction_year());
			statement.setInt(3, getGenreId(movie.getGenre(), connection));

			statement.executeUpdate();
			connection.commit();

			ResultSet rs = statement.getGeneratedKeys();

			if (rs.next()) {
				return new Movie(rs.getInt("GENERATED_KEY"), movie.getTitle(), movie.getProduction_year(),
						movie.getGenre());
			}

		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
		return null;
	}

	private int getGenreId(String genre, Connection connection) throws SQLException {

		try (PreparedStatement statement = connection.prepareStatement(GET_GENRE_ID)) {
			statement.setString(1, genre);
			statement.executeQuery();
			connection.commit();

			ResultSet rs = statement.getResultSet();
			if (rs.next()) {
				return rs.getInt("id");
			}
		}

		return 0;
	}

	private Actor saveActor(Actor actor, Connection connection) throws SQLException {

		try (PreparedStatement statement = connection.prepareStatement(SAVE_ACTOR, Statement.RETURN_GENERATED_KEYS)) {

			statement.setString(1, actor.getFirst_name());
			statement.setString(2, actor.getLast_name());

			statement.executeUpdate();

			connection.commit();

			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				return new Actor(rs.getInt("GENERATED_KEY"), actor.getFirst_name(), actor.getLast_name());
			}

		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}
		return null;

	}

	//// SELECTERS

	@Override
	public List<Movie> getMovies() {

		ArrayList<Movie> resultList = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "mysql");
				PreparedStatement statement = connection
						.prepareStatement("SELECT * FROM Movies JOIN Genre ON Genre.id = Movies.Genre_Id")) {

			ResultSet result = statement.executeQuery();

			while (result.next()) {
				resultList.add(extractMovie(result));
			}
			return resultList;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Movie extractMovie(ResultSet result) throws SQLException {

		int id = result.getInt(1);
		String title = result.getString(2);
		String productionYear = result.getString(3);
		String genre = result.getString(6);

		return new Movie(id, title, productionYear, genre);

	}

	@Override
	public List<Actor> getActors() {
		ArrayList<Actor> resultList = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "mysql");
				Statement statement = connection.createStatement()) {

			ResultSet result = statement.executeQuery("SELECT * FROM Actors");

			while (result.next()) {

				int id = result.getInt(1);
				String firstName = result.getString(2);
				String lastName = result.getString(3);

				resultList.add(new Actor(id, firstName, lastName));
			}
			return resultList;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Genre> getGenre() {
		ArrayList<Genre> resultList = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "mysql");
				Statement statement = connection.createStatement()) {

			ResultSet result = statement.executeQuery("SELECT * FROM Genre");

			while (result.next()) {

				int id = result.getInt(1);
				String genre = result.getString(2);

				resultList.add(new Genre(id, genre));
			}
			return resultList;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Review> getReviews() {
		ArrayList<Review> resultList = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "mysql");
				Statement statement = connection.createStatement()) {

			ResultSet result = statement.executeQuery("SELECT * FROM Review");

			while (result.next()) {

				int id = result.getInt(1);
				int movieId = result.getInt(2);
				String review = result.getString(3);
				int revireUserId = result.getInt(4);

				resultList.add(new Review(id, movieId, review, revireUserId));
			}
			return resultList;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ReviewUser> getReviewUsers() {
		ArrayList<ReviewUser> resultList = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "mysql");
				Statement statement = connection.createStatement()) {

			ResultSet result = statement.executeQuery("SELECT * FROM ReviewUser");

			while (result.next()) {

				int id = result.getInt(1);
				String username = result.getString(2);

				resultList.add(new ReviewUser(id, username));
			}
			return resultList;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static final String REMOVE_A_MOVIE = "DELETE FROM Movies WHERE id = ?";
	private static final String REMOVE_AN_ACTOR = "DELETE FROM Actors WHERE id = ?";
	private static final String REMOVE_A_GENRE = "DELETE FROM Genre WHERE id = ?";
	private static final String REMOVE_A_REVIEW = "DELETE FROM Review WHERE id = ?";
	private static final String REMOVE_A_REVIEWUSER = "DELETE FROM ReviewUser WHERE id = ?";

	@Override
	public int deleteObject(Object obj) {

		if (obj instanceof Movie) {
			int result = remove(((Movie) obj).getId(), REMOVE_A_MOVIE);
			if (result > 0) {
				return result;
			}
		} else if (obj instanceof Actor) {
			int result = remove(((Actor) obj).getId(), REMOVE_AN_ACTOR);
			if (result > 0) {
				return result;
			}
		} else if (obj instanceof Genre) {
			int result = remove(((Genre) obj).getId(), REMOVE_A_GENRE);
			if (result > 0) {
				return result;
			}
		} else if (obj instanceof Review) {
			int result = remove(((Genre) obj).getId(), REMOVE_A_REVIEW);
			if (result > 0) {
				return result;
			}
		} else if (obj instanceof ReviewUser) {
			int result = remove(((Genre) obj).getId(), REMOVE_A_REVIEWUSER);
			if (result > 0) {
				return result;
			}
		}

		throw new MovieRepositoryException("Couldn't delete the object from database");

	}

	private int remove(int id, String query) {
		try (Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "mysql")) {

			connection.setAutoCommit(false);

			try (PreparedStatement statement = connection.prepareStatement(query)) {

				statement.setInt(1, id);

				int effectedRows = statement.executeUpdate();

				connection.commit();

				return effectedRows;

			} catch (SQLException e) {
				connection.rollback();
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
