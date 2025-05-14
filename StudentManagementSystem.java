import java.io.*;
import java.util.*;

class Student {
    private String name;
    private int rollNumber;
    private String grade;
    private String email;
    private String phoneNumber;

    public Student(String name, int rollNumber, String grade, String email, String phoneNumber) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getName() { return name; }
    public int getRollNumber() { return rollNumber; }
    public String getGrade() { return grade; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }

    public void setName(String name) { this.name = name; }
    public void setGrade(String grade) { this.grade = grade; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade + ", Email: " + email + ", Phone: " + phoneNumber;
    }
}

public class StudentManagementSystem {
    private List<Student> students;
    private final String fileName = "students.txt";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadStudents();
    }

    public void addStudent(Student student) {
        students.add(student);
        saveStudents();
    }

    public void removeStudent(int rollNumber) {
        boolean removed = students.removeIf(s -> s.getRollNumber() == rollNumber);
        if (removed) {
            System.out.println("Student removed successfully.");
            saveStudents();
        } else {
            System.out.println("Student not found!");
        }
    }

    public Student searchStudent(int rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber() == rollNumber) {
                return s;
            }
        }
        return null;
    }

    public void editStudent(int rollNumber, Scanner scanner) {
        Student student = searchStudent(rollNumber);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("Editing student: " + student.getName());

        System.out.print("Enter new name (leave blank to keep unchanged): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) student.setName(name);

        System.out.print("Enter new grade (leave blank to keep unchanged): ");
        String grade = scanner.nextLine();
        if (!grade.isEmpty()) student.setGrade(grade);

        System.out.print("Enter new email (leave blank to keep unchanged): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            if (isValidEmail(email)) {
                student.setEmail(email);
            } else {
                System.out.println("Invalid email format. Email not changed.");
            }
        }

        System.out.print("Enter new phone number (leave blank to keep unchanged): ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) {
            if (isValidPhone(phone)) {
                student.setPhoneNumber(phone);
            } else {
                System.out.println("Invalid phone number format. Phone not changed.");
            }
        }

        saveStudents();
        System.out.println("Student updated successfully!");
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
        } else {
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    private void saveStudents() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Student s : students) {
                writer.write(s.getRollNumber() + "," + s.getName() + "," + s.getGrade() + "," + s.getEmail() + "," + s.getPhoneNumber());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    private void loadStudents() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int roll = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String grade = parts[2].trim();
                    String email = parts[3].trim();
                    String phone = parts[4].trim();
                    students.add(new Student(name, roll, grade, email, phone));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }

    private static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();
        int choice;

        do {
            System.out.println("\n---- Student Management System ----");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Edit Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter roll number: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid roll number! Enter a valid number:");
                        scanner.next();
                    }
                    int roll = scanner.nextInt();
                    scanner.nextLine(); 

                    System.out.print("Enter grade: ");
                    String grade = scanner.nextLine();

                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter phone number: ");
                    String phone = scanner.nextLine();

                    if (name.isEmpty() || grade.isEmpty() || !isValidEmail(email) || !isValidPhone(phone)) {
                        System.out.println("Invalid input! Please try again.");
                    } else {
                        Student student = new Student(name, roll, grade, email, phone);
                        sms.addStudent(student);
                        System.out.println("Student added successfully!");
                    }
                    break;

                case 2:
                    System.out.print("Enter roll number to remove: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input! Enter a number.");
                        scanner.next();
                    }
                    int removeRoll = scanner.nextInt();
                    scanner.nextLine();
                    sms.removeStudent(removeRoll);
                    break;

                case 3:
                    System.out.print("Enter roll number to search: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input! Enter a number.");
                        scanner.next();
                    }
                    int searchRoll = scanner.nextInt();
                    scanner.nextLine();
                    Student foundStudent = sms.searchStudent(searchRoll);
                    if (foundStudent != null) {
                        System.out.println(foundStudent);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 4:
                    sms.displayAllStudents();
                    break;

                case 5:
                    System.out.print("Enter roll number to edit: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input! Enter a number.");
                        scanner.next();
                    }
                    int editRoll = scanner.nextInt();
                    scanner.nextLine(); 
                    sms.editStudent(editRoll, scanner);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice! Please select again.");
            }
        } while (choice != 6);

        scanner.close();
    }
}
