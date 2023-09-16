import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Movie {
    private int id;
    private String title;
    private String genre;
    private double rating;

    public Movie(int id, String title, String genre) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.rating = 0.0; // Initialize with no rating
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}

class User {
    private String username;
    private Map<Integer, Double> ratings;

    public User(String username) {
        this.username = username;
        this.ratings = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public Map<Integer, Double> getRatings() {
        return ratings;
    }

    public void rateMovie(int movieId, double rating) {
        ratings.put(movieId, rating);
    }
}

class MovieRecommendationSystem {
    private List<Movie> movies;
    private List<User> users;

    public MovieRecommendationSystem() {
        movies = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<Movie> recommendMovies(User user) {
        List<Movie> recommendedMovies = new ArrayList<>();
        Map<Integer, Double> userRatings = user.getRatings();

        for (Movie movie : movies) {
            if (!userRatings.containsKey(movie.getId())) {
                recommendedMovies.add(movie);
            }
        }

        // Sort recommendedMovies by a recommendation algorithm (not implemented in this example)

        return recommendedMovies;
    }

    public Movie getMovieById(int movieId) {
        for (Movie movie : movies) {
            if (movie.getId() == movieId) {
                return movie;
            }
        }
        return null;
    }
}

public class MovieRecommendationApp {
    public static void main(String[] args) {
        MovieRecommendationSystem recommendationSystem = new MovieRecommendationSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add Movie");
            System.out.println("2. Register User");
            System.out.println("3. Rate Movie");
            System.out.println("4. Recommend Movies");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter movie title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter movie genre: ");
                    String genre = scanner.nextLine();
                    Movie movie = new Movie(recommendationSystem.movies.size() + 1, title, genre);
                    recommendationSystem.addMovie(movie);
                    System.out.println("Movie added successfully.");
                    break;

                case 2:
                    System.out.print("Enter your username: ");
                    String username = scanner.nextLine();
                    User newUser = new User(username);
                    recommendationSystem.addUser(newUser);
                    System.out.println("User registered successfully.");
                    break;

                case 3:
                    System.out.print("Enter your username: ");
                    String ratingUsername = scanner.nextLine();
                    User ratingUser = null;
                    for (User user : recommendationSystem.users) {
                        if (user.getUsername().equals(ratingUsername)) {
                            ratingUser = user;
                            break;
                        }
                    }
                    if (ratingUser != null) {
                        System.out.print("Enter movie ID to rate: ");
                        int movieIdToRate = scanner.nextInt();
                        System.out.print("Enter your rating (1-5): ");
                        double rating = scanner.nextDouble();
                        ratingUser.rateMovie(movieIdToRate, rating);
                        System.out.println("Rating added successfully.");
                    } else {
                        System.out.println("User not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter your username to get movie recommendations: ");
                    String recommendUsername = scanner.nextLine();
                    User recommendUser = null;
                    for (User user : recommendationSystem.users) {
                        if (user.getUsername().equals(recommendUsername)) {
                            recommendUser = user;
                            break;
                        }
                    }
                    if (recommendUser != null) {
                        List<Movie> recommendedMovies = recommendationSystem.recommendMovies(recommendUser);
                        System.out.println("Recommended Movies:");
                        for (Movie recommendedMovie : recommendedMovies) {
                            System.out.println(recommendedMovie.getId() + ". " + recommendedMovie.getTitle() +
                                    " (Genre: " + recommendedMovie.getGenre() + ")");
                        }
                    } else {
                        System.out.println("User not found.");
                    }
                    break;

                case 5:
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
