import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentService service = new StudentService();

        // Load data from MySQL at startup
        service.loadFromDatabase();

        while (true) {

            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Delete Student");
            System.out.println("5. Update Student");
            System.out.println("6. Sort by Marks");
            System.out.println("7. Search Student by Name");
            System.out.println("8. Sort Students by Name");
            System.out.println("9. Find Topper");
            System.out.println("10. Find Average Marks");
            System.out.println("11. Find Highest & Lowest Marks");
            System.out.println("12. Filter by Marks");
            System.out.println("13. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Marks: ");
                    double marks = sc.nextDouble();

                    service.addStudent(new Student(id, name, marks));
                    service.loadFromDatabase();
                    break;

                case 2:
                    service.displayStudents();
                    break;

                case 3:
                    System.out.print("Enter ID to search: ");
                    service.searchStudent(sc.nextInt());
                    break;

                case 4:
                    System.out.print("Enter ID to delete: ");
                    service.deleteStudent(sc.nextInt());
                    service.loadFromDatabase();
                    break;

                case 5:
                    System.out.print("Enter ID to update: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter new name: ");
                    String newName = sc.nextLine();

                    System.out.print("Enter new marks: ");
                    double newMarks = sc.nextDouble();

                    service.updateStudent(updateId, newName, newMarks);
                    service.loadFromDatabase();
                    break;

                case 6:
                    service.sortStudentsByMarks();
                    break;

                case 7:
                    sc.nextLine();
                    System.out.print("Enter name to search: ");
                    service.searchByName(sc.nextLine());
                    break;

                case 8:
                    service.sortStudentByName();
                    break;

                case 9:
                    service.findTopper();
                    break;

                case 10:
                    service.averageMarks();
                    break;

                case 11:
                    service.findHighestAndLowest();
                    break;

                case 12:
                    System.out.print("Enter minimum marks: ");
                    service.filterByMarks(sc.nextDouble());
                    break;

                case 13:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}