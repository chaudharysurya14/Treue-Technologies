import java.util.*;

class User {
    private String username;
    private String password;
    private boolean hasVoted;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.hasVoted = false;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public void markAsVoted() {
        hasVoted = true;
    }
}

class Candidate {
    private String name;
    private int votes;

    public Candidate(String name) {
        this.name = name;
        this.votes = 0;
    }

    public String getName() {
        return name;
    }

    public int getVotes() {
        return votes;
    }

    public void incrementVotes() {
        votes++;
    }
}

class Election {
    private String title;
    private List<Candidate> candidates;
    private List<User> voters;

    public Election(String title) {
        this.title = title;
        this.candidates = new ArrayList<>();
        this.voters = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public List<User> getVoters() {
        return voters;
    }

    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
    }

    public void addUser(User user) {
        voters.add(user);
    }

    public Candidate findCandidateByName(String name) {
        for (Candidate candidate : candidates) {
            if (candidate.getName().equalsIgnoreCase(name)) {
                return candidate;
            }
        }
        return null;
    }
}

public class OnlineVotingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Election> elections = new ArrayList<>();
        List<User> users = new ArrayList<>();

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Create Election");
            System.out.println("2. Register User");
            System.out.println("3. List Elections");
            System.out.println("4. Vote");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter the title of the election: ");
                    String electionTitle = scanner.nextLine();
                    Election newElection = new Election(electionTitle);
                    elections.add(newElection);
                    System.out.println("Election created successfully.");
                    break;

                case 2:
                    System.out.print("Enter your username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();
                    User newUser = new User(username, password);
                    users.add(newUser);
                    System.out.println("User registered successfully.");
                    break;

                case 3:
                    System.out.println("Available Elections:");
                    for (int i = 0; i < elections.size(); i++) {
                        System.out.println(i + 1 + ". " + elections.get(i).getTitle());
                    }
                    break;

                case 4:
                    System.out.print("Enter your username: ");
                    String voterUsername = scanner.nextLine();
                    User voter = null;
                    for (User user : users) {
                        if (user.getUsername().equalsIgnoreCase(voterUsername)) {
                            voter = user;
                            break;
                        }
                    }
                    if (voter == null) {
                        System.out.println("User not found.");
                        break;
                    }

                    System.out.print("Select an election (1-" + elections.size() + "): ");
                    int electionChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    if (electionChoice < 1 || electionChoice > elections.size()) {
                        System.out.println("Invalid election choice.");
                        break;
                    }
                    Election selectedElection = elections.get(electionChoice - 1);

                    if (voter.hasVoted()) {
                        System.out.println("You have already voted in this election.");
                        break;
                    }

                    System.out.println("Candidates:");
                    for (int i = 0; i < selectedElection.getCandidates().size(); i++) {
                        System.out.println(i + 1 + ". " + selectedElection.getCandidates().get(i).getName());
                    }

                    System.out.print("Select a candidate (1-" + selectedElection.getCandidates().size() + "): ");
                    int candidateChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    if (candidateChoice < 1 || candidateChoice > selectedElection.getCandidates().size()) {
                        System.out.println("Invalid candidate choice.");
                        break;
                    }
                    Candidate selectedCandidate = selectedElection.getCandidates().get(candidateChoice - 1);

                    selectedCandidate.incrementVotes();
                    voter.markAsVoted();
                    System.out.println("Vote cast successfully for " + selectedCandidate.getName());
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
