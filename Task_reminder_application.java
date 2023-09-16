import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Task {
    private String title;
    private Date dueDate;
    private Priority priority;
    private TaskStatus status;

    public Task(String title, Date dueDate, Priority priority) {
        this.title = title;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = TaskStatus.NOT_COMPLETED;
    }

    public String getTitle() {
        return title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void markAsCompleted() {
        status = TaskStatus.COMPLETED;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "Title: " + title + "\nDue Date: " + dateFormat.format(dueDate) + "\nPriority: " + priority +
                "\nStatus: " + status + "\n";
    }
}

enum Priority {
    HIGH, MEDIUM, LOW
}

enum TaskStatus {
    NOT_COMPLETED, COMPLETED
}

public class TaskReminderApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Create Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter task title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter due date (yyyy-MM-dd): ");
                    String dueDateString = scanner.nextLine();
                    Date dueDate = null;
                    try {
                        dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDateString);
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Task creation failed.");
                        break;
                    }
                    System.out.print("Enter priority (HIGH, MEDIUM, LOW): ");
                    String priorityString = scanner.nextLine();
                    Priority priority;
                    try {
                        priority = Priority.valueOf(priorityString.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid priority. Task creation failed.");
                        break;
                    }
                    Task newTask = new Task(title, dueDate, priority);
                    tasks.add(newTask);
                    System.out.println("Task created successfully.");
                    break;

                case 2:
                    System.out.println("Tasks:");
                    for (int i = 0; i < tasks.size(); i++) {
                        Task task = tasks.get(i);
                        System.out.println("Task " + (i + 1) + ":\n" + task);
                    }
                    break;

                case 3:
                    System.out.print("Enter the task number to mark as completed: ");
                    int taskNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    if (taskNumber >= 1 && taskNumber <= tasks.size()) {
                        Task taskToComplete = tasks.get(taskNumber - 1);
                        taskToComplete.markAsCompleted();
                        System.out.println("Task marked as completed.");
                    } else {
                        System.out.println("Invalid task number.");
                    }
                    break;

                case 4:
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
