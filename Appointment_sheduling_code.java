import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

class User {
    private String username;
    private String email;
    private List<Appointment> appointments;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.appointments = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }
}

class Appointment {
    private String serviceProvider;
    private Date date;

    public Appointment(String serviceProvider, Date date) {
        this.serviceProvider = serviceProvider;
        this.date = date;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public Date getDate() {
        return date;
    }
}

class EmailService {
    public static void sendConfirmationEmail(User user, Appointment appointment) {
        String to = user.getEmail();
        String subject = "Appointment Confirmation";
        String body = "Dear " + user.getUsername() + ",\n\nYour appointment with " +
                appointment.getServiceProvider() + " on " + appointment.getDate() +
                " has been confirmed.\n\nThank you for choosing our service.";

        sendEmail(to, subject, body);
    }

    private static void sendEmail(String to, String subject, String body) {
        // Simulate sending an email; in a real system, use JavaMail API or an email service
        System.out.println("Email sent to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body:\n" + body);
    }
}

public class AppointmentSchedulingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a list of service providers (for demonstration purposes)
        List<String> serviceProviders = Arrays.asList("Doctor", "Dentist", "Hair Stylist");

        // Create a list of available time slots (for demonstration purposes)
        List<Date> availableTimeSlots = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1); // Add 1 day
        for (int i = 0; i < 5; i++) {
            availableTimeSlots.add(calendar.getTime());
            calendar.add(Calendar.HOUR_OF_DAY, 2); // Add 2 hours
        }

        // Create a list of registered users (for demonstration purposes)
        List<User> users = new ArrayList<>();

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Register User");
            System.out.println("2. Book Appointment");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    User newUser = new User(username, email);
                    users.add(newUser);
                    System.out.println("User registered successfully.");
                    break;

                case 2:
                    if (users.isEmpty()) {
                        System.out.println("No registered users. Please register first.");
                        break;
                    }

                    System.out.println("Service Providers:");
                    for (int i = 0; i < serviceProviders.size(); i++) {
                        System.out.println(i + 1 + ". " + serviceProviders.get(i));
                    }
                    System.out.print("Select a service provider (1-" + serviceProviders.size() + "): ");
                    int serviceProviderChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    if (serviceProviderChoice < 1 || serviceProviderChoice > serviceProviders.size()) {
                        System.out.println("Invalid choice.");
                        break;
                    }

                    System.out.println("Available Time Slots:");
                    for (int i = 0; i < availableTimeSlots.size(); i++) {
                        System.out.println(i + 1 + ". " + availableTimeSlots.get(i));
                    }
                    System.out.print("Select a time slot (1-" + availableTimeSlots.size() + "): ");
                    int timeSlotChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    if (timeSlotChoice < 1 || timeSlotChoice > availableTimeSlots.size()) {
                        System.out.println("Invalid choice.");
                        break;
                    }

                    User selectedUser = null;
                    while (selectedUser == null) {
                        System.out.print("Enter your username: ");
                        String inputUsername = scanner.nextLine();
                        for (User user : users) {
                            if (user.getUsername().equals(inputUsername)) {
                                selectedUser = user;
                                break;
                            }
                        }
                        if (selectedUser == null) {
                            System.out.println("User not found.");
                        }
                    }

                    Date selectedTimeSlot = availableTimeSlots.get(timeSlotChoice - 1);
                    String selectedServiceProvider = serviceProviders.get(serviceProviderChoice - 1);
                    Appointment appointment = new Appointment(selectedServiceProvider, selectedTimeSlot);
                    selectedUser.addAppointment(appointment);

                    // Send confirmation email
                    EmailService.sendConfirmationEmail(selectedUser, appointment);

                    System.out.println("Appointment booked successfully.");
                    break;

                case 3:
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
