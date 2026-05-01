import java.util.ArrayList;

public class Department {
    private String name;
    private ArrayList<Student> students;

    public Department(String name) {
        this.name = name;
        this.students = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students in this department.");
            return;
        }

        for (Student s : students) {
            s.display();
        }
    }
}