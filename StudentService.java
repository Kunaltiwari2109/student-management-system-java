import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class StudentService {

    ArrayList<Student> students = new ArrayList<>();

    // Add student
    public void addStudent(Student student) {

        if (student.getName() == null || student.getName().isEmpty()) {
            System.out.println("Name cannot be empty");
            return;
        }

        if (student.getMarks() < 0 || student.getMarks() > 100) {
            System.out.println("Invalid marks");
            return;
        }

        String sql = "INSERT INTO students (id, name, marks) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, student.getId());
            ps.setString(2, student.getName());
            ps.setDouble(3, student.getMarks());

            ps.executeUpdate();

            //add to ArrayList ONLY if DB insert succeeds
            students.add(student);

            System.out.println("Student added successfully");

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {

            System.out.println("Student with this ID already exists!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Display students
    public void displayStudents() {

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student s : students) {
            System.out.println(s);
        }
    }

    // Search student by ID
    public void searchStudent(int id) {

        if (id <= 0) {
            System.out.println("Invalid ID.");
            return;
        }

        for (Student s : students) {
            if (s.getId() == id) {
                System.out.println("Student Found:");
                System.out.println(s);
                return;
            }
        }

        System.out.println("Student not found.");
    }

    // Delete student
    public void deleteStudent(int id) {

        if (id <= 0) {
            System.out.println("Invalid ID.");
            return;
        }

        Iterator<Student> it = students.iterator();

        while (it.hasNext()) {
            Student s = it.next();
            if (s.getId() == id) {
                it.remove();
                break;
            }
        }

        String sql = "DELETE FROM students WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Student deleted successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update student
    public void updateStudent(int id, String newName, double newMarks) {

        if (id <= 0 || newName == null || newName.isEmpty() || newMarks < 0 || newMarks > 100) {
            System.out.println("Invalid input");
            return;
        }

        for (Student s : students) {
            if (s.getId() == id) {
                s.setName(newName);
                s.setMarks(newMarks);
                break;
            }
        }

        String sql = "UPDATE students SET name=?, marks=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newName);
            ps.setDouble(2, newMarks);
            ps.setInt(3, id);

            ps.executeUpdate();
            System.out.println("Student updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load data from database
    public void loadFromDatabase() {

        students.clear();

        String sql = "SELECT * FROM students";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("marks")
                ));
            }

            System.out.println("Data loaded from database");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Search by name
    public void searchByName(String name) {

        boolean found = false;

        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                System.out.println(s);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Student not found");
        }
    }

    // Sort by name
    public void sortStudentByName() {

        if (students.isEmpty()) {
            System.out.println("No student found");
            return;
        }

        Collections.sort(students, Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
        displayStudents();
    }

    // Sort by marks
    public void sortStudentsByMarks() {

        students.sort(Comparator.comparingDouble(Student::getMarks));
        displayStudents();
    }

    // Find topper
    public void findTopper() {

        if (students.isEmpty()) {
            System.out.println("No students found");
            return;
        }

        Student topper = students.stream()
                .max(Comparator.comparingDouble(Student::getMarks))
                .get();

        System.out.println("Topper Student:");
        System.out.println(topper);
    }

    // Average marks
    public void averageMarks() {

        if (students.isEmpty()) {
            System.out.println("No students found");
            return;
        }

        double avg = students.stream()
                .mapToDouble(Student::getMarks)
                .average()
                .getAsDouble();

        System.out.println("Average marks: " + avg);
    }

    // Highest & Lowest
    public void findHighestAndLowest() {

        if (students.isEmpty()) {
            System.out.println("No students found");
            return;
        }

        Student highest = Collections.max(students, Comparator.comparingDouble(Student::getMarks));
        Student lowest = Collections.min(students, Comparator.comparingDouble(Student::getMarks));

        System.out.println("Highest: " + highest);
        System.out.println("Lowest: " + lowest);
    }

    // Filter by marks
    public void filterByMarks(double minMark) {

        boolean found = false;

        for (Student s : students) {
            if (s.getMarks() >= minMark) {
                System.out.println(s);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No student found");
        }
    }
}