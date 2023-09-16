import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Book {
    private int id;
    private String title;
    private String author;
    private String category;
    private double price;
    private int stock;
    private double averageRating;
    private List<String> reviews;

    public Book(int id, String title, String author, String category, double price, int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.averageRating = 0.0;
        this.reviews = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void addReview(String review) {
        reviews.add(review);
        recalculateAverageRating();
    }

    private void recalculateAverageRating() {
        if (reviews.size() > 0) {
            double totalRating = 0.0;
            for (String review : reviews) {
                totalRating += Double.parseDouble(review.split(":")[1]);
            }
            averageRating = totalRating / reviews.size();
        }
    }
}

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class OnlineBookstore {
    private Map<Integer, Book> books;
    private List<User> users;
    private List<Integer> shoppingCart;
    private int userIdCounter = 1;

    public OnlineBookstore() {
        books = new HashMap<>();
        users = new ArrayList<>();
        shoppingCart = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    public void registerUser(String username, String password) {
        users.add(new User(username, password));
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void addToCart(int bookId) {
        shoppingCart.add(bookId);
    }

    public void removeFromCart(int bookId) {
        shoppingCart.remove(Integer.valueOf(bookId));
    }

    public void checkout(User user) {
        // Implement order management and payment processing here
        System.out.println("Checkout completed. Thank you for your purchase!");
    }

    public Book getBookDetails(int bookId) {
        return books.get(bookId);
    }

    public void rateAndReview(User user, int bookId, String review) {
        Book book = books.get(bookId);
        if (book != null) {
            book.addReview(user.getUsername() + ":" + review);
        }
    }

    public List<Integer> getShoppingCart() {
        return shoppingCart;
    }
}

public class OnlineBookstoreApp {
    public static void main(String[] args) {
        OnlineBookstore bookstore = new OnlineBookstore();

        // Add sample books to the bookstore
        Book book1 = new Book(1, "Book Title 1", "Author 1", "Category 1", 19.99, 10);
        Book book2 = new Book(2, "Book Title 2", "Author 2", "Category 2", 24.99, 8);
        bookstore.addBook(book1);
        bookstore.addBook(book2);

        // Register users
        bookstore.registerUser("user1", "password1");
        bookstore.registerUser("user2", "password2");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Login");
            System.out.println("2. Browse Books");
            System.out.println("3. Add to Cart");
            System.out.println("4. Remove from Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Checkout");
            System.out.println("7. View Book Details");
            System.out.println("8. Rate and Review");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    User loggedInUser = bookstore.login(username, password);
                    if (loggedInUser != null) {
                        System.out.println("Logged in as " + loggedInUser.getUsername());
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;

                case 2:
                    System.out.println("Available Books:");
                    for (Map.Entry<Integer, Book> entry : bookstore.getBooks().entrySet()) {
                        Book book = entry.getValue();
                        System.out.println(book.getId() + ". " + book.getTitle() + " by " + book.getAuthor() +
                                " - Price: $" + book.getPrice() + " - Stock: " + book.getStock());
                    }
                    break;

                case 3:
                    System.out.print("Enter the book ID to add to your cart: ");
                    int bookIdToAdd = scanner.nextInt();
                    bookstore.addToCart(bookIdToAdd);
                    System.out.println("Book added to cart.");
                    break;

                case 4:
                    System.out.print("Enter the book ID to remove from your cart: ");
                    int bookIdToRemove = scanner.nextInt();
                    bookstore.removeFromCart(bookIdToRemove);
                    System.out.println("Book removed from cart.");
                    break;

                case 5:
                    System.out.println("Shopping Cart:");
                    List<Integer> cart = bookstore.getShoppingCart();
                    for (Integer bookId : cart) {
                        Book cartBook = bookstore.getBookDetails(bookId);
                        System.out.println(cartBook.getId() + ". " + cartBook.getTitle() + " by " + cartBook.getAuthor() +
                                " - Price: $" + cartBook.getPrice());
                    }
                    break;

                case 6:
                    if (loggedInUser != null) {
                        bookstore.checkout(loggedInUser);
                        System.out.println("Checkout completed.");
                    } else {
                        System.out.println("You need to log in first.");
                    }
                    break;

                case 7:
                    System.out.print("Enter the book ID to view details: ");
                    int bookIdToView = scanner
