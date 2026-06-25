import java.sql.Connection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StudentService service = new StudentService();

        service.loadFromFile();

        while (true) {

            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Update Student");
            System.out.println("6. Sort by marks");
            System.out.println("7. Save to file");
            System.out.println("8. Search student by name");
            System.out.println("9: Sort students by name");
            System.out.println("10. Find Topper");
            System.out.println("11. Find average marks");
            System.out.println("12. Find student with highest and lowest marks");
            System.out.println("13. Filter by marks");
            System.out.println("14. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch(choice) {

                case 1:

                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Marks: ");
                    double marks = sc.nextDouble();

                    Student student = new Student(id, name, marks);

                    service.addStudent(student);
                    service.loadFromFile();

                    break;

                case 2:

                    service.displayStudents();

                    break;

                case 3:

                    System.out.print("Enter ID to search: ");
                    int searchId = sc.nextInt();

                    service.searchStudent(searchId);

                    break;

                case 4:

                    System.out.print("Enter ID to delete: ");

                    int deleteId = sc.nextInt();

                    service.deleteStudent(deleteId);
                    service.loadFromFile();

                    break;

                case 5:

                    System.out.println("Enter ID to update: ");

                    int updateId = sc.nextInt();

                    sc.nextLine();

                    System.out.println("Enter new name: ");

                    String newStudentName = sc.nextLine();

                    System.out.println("Enter new marks: ");

                    double newStudentMarks = sc.nextDouble();

                    service.updateStudent(updateId, newStudentName, newStudentMarks);
                    service.loadFromFile();

                    break;

                case 6:

                    service.sortStudentsByMarks();

                    break;

                case 7:

                    service.saveToFile();
                    break;

                case 8:

                    sc.nextLine();

                    System.out.println("Enter name of student: ");

                    String searchName = sc.nextLine();

                    service.searchByName(searchName);
                    break;

                case 9:

                    service.sortStudentByName();

                    break;

                case 10:

                    service.findTopper();

                    break;

                case 11:

                    service.averageMarks();

                    break;

                case 12:

                    service.findHighestAndLowest();

                    break;

                case 13:

                    System.out.print("Enter minimum marks: ");

                    double minMarks = sc.nextDouble();

                    service.filterByMarks(minMarks);

                    break;

                case 14:

                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:

                    System.out.println("Option not implemented yet");
            }
        }
    }
}