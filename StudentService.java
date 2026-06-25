import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.Iterator;

public class StudentService {

    ArrayList<Student> students = new ArrayList<>();

    // Add student
    public void addStudent(Student student) {


            // ID validation
            if (student.getId() <= 0) {
                System.out.println("ID must be positive.");
                return;
            }

            // Duplicate ID check
            for (Student s : students) {
                if (s.getId() == student.getId()) {
                    System.out.println("Student with this ID already exists.");
                    return;
                }
            }

            // Name validation
            if (student.getName() == null || student.getName().trim().isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }

            // Marks validation
            if (student.getMarks() < 0 || student.getMarks() > 100) {
                System.out.println("Marks must be between 0 and 100.");
                return;
            }

            students.add(student);
            System.out.println("Student added successfully!");
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

    // Search student
    public void searchStudent(int id) {

        boolean found = false;

        if (id <= 0) {
            System.out.println("Invalid ID.");
            return;
        }

        for (Student s : students) {

            if (s.getId() == id) {

                System.out.println("Student Found:");
                System.out.println(s);

                found = true;

                break;
            }
        }

        if (!found) {
            System.out.println("Student not found.");
        }
    }

    // Delete student
    public void deleteStudent(int id)   {

        Iterator<Student> it = students.iterator();

        if (id <= 0) {
            System.out.println("Invalid ID.");
            return;
        }

        while(it.hasNext()) {

            Student s = it.next();

            if(s.getId() == id) {
                it.remove();
                System.out.println("Student deleted successfully.");
                return;
            }
        }

        System.out.println("Student not found.");

    }

    // Update student
    public void updateStudent(int id, String newName, double newMarks) {

        if (id <= 0) {
            System.out.println("Invalid ID.");
            return;
        }

        if (newName == null || newName.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        if (newMarks < 0 || newMarks > 100) {
            System.out.println("Marks must be between 0 and 100.");
            return;
        }

        boolean found = false;

        for (Student s : students) {

            if (s.getId() == id) {
                s.setName(newName);
                s.setMarks(newMarks);

                found = true;

                System.out.println("Student updated successfully");

                break;
            }
        }
        if (!found) {
            System.out.println("Student not found");
        }
    }

    // Sort students by marks
    public void sortStudentsByMarks() {

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        students.sort((s1, s2) ->
                Double.compare(s1.getMarks(), s2.getMarks())
        );

        System.out.println("Students sorted successfully.");

        displayStudents();
    }

    // Save data in text file
    public void saveToFile() {

        try {

            FileWriter writer = new FileWriter("students.txt");

            for (Student s : students) {

                writer.write(
                        s.getId() + "," +
                                s.getName() + "," +
                                s.getMarks() + "\n"
                );
            }

            writer.close();

            System.out.println("Data saved successfully!");

        } catch (IOException e) {

            System.out.println("Error while saving file.");
        }
    }

    // Load from Text file to ArrayList
    public void loadFromFile() {

        try {

            File file = new File("students.txt");

            // If file does not exist
            if (!file.exists()) {
                return;
            }

            Scanner fileReader = new Scanner(file);

            students.clear();

            while (fileReader.hasNextLine()) {

                String line = fileReader.nextLine();

                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                double marks = Double.parseDouble(data[2]);

                Student student =
                        new Student(id, name, marks);

                students.add(student);
            }

            fileReader.close();

            System.out.println("Data loaded successfully!");

        } catch (Exception e) {

            System.out.println("Error loading file.");
        }
    }

    //Search by name
    public void searchByName(String name){

        boolean found = false;

        for(Student s : students){

            if (s.getName().equalsIgnoreCase(name)) {

                System.out.println(
                    "ID: " + s.getId() + "," +
                    " Name: " + s.getName() + "," +
                    " Marks: " + s.getMarks());

                found = true;
                break;
            }

        }
        if(!found){
            System.out.println("Student not found");
        }
    }

    // Sort students by name
    public void sortStudentByName(){

        if(students.isEmpty()){
            System.out.println("No student found");
            return;
        }

        Collections.sort(students, new Comparator<Student>() {

            @Override
            public int compare(Student s1, Student s2) {

                return s1.getName()
                        .compareToIgnoreCase(
                                s2.getName()
                        );
            }
        });

        System.out.println("Students sorted successfully");

        displayStudents();

    }

    //Topper student
    public void findTopper() {

        if(students.isEmpty()) {

            System.out.println("No students found");
            return;
        }

        Student topper = students.stream()
                .max((s1, s2) -> Double.compare(s1.getMarks(), s2.getMarks()))
                .get();

        System.out.println("Topper Student:");

        System.out.println(topper);
    }

    //Average marks
    public void averageMarks(){

        double totalMarks = 0;

        if(students.isEmpty()){

            System.out.println("No student found");
            return;
        }

        for(Student s : students){

            totalMarks += s.getMarks();

        }

        double avgMarks = totalMarks / students.size();

        System.out.println("Average marks of all the students is: \n" + avgMarks);

    }

    // Find highest and Lowest
    public void findHighestAndLowest(){

        if(students.isEmpty()){

            System.out.println("No student found");

            return;
        }

        Student highest = students.get(0);
        Student lowest = students.get(0);

        for(Student s : students){

            if(s.getMarks() > highest.getMarks()){

                highest = s;

            }

            if(s.getMarks() < lowest.getMarks()){

                lowest = s;

            }
        }

        System.out.println("Student with highest marks is: " + highest);
        System.out.println("Student with lowest marks is: " + lowest);

    }

    // Filter my marks
    public void filterByMarks(double minMark){

        boolean found = false;

        for(Student s : students){

            if(s.getMarks() >= minMark){

                System.out.println(s);

                found = true;

            }

        }

        if(!found){
            System.out.println("No student found");
        }

    }

}

