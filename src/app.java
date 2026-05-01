import java.util.Scanner;
public class app {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        Collage college = new Collage("My College");

        // Predefined departments
        college.addDepartment(new Department("CSE"));
        college.addDepartment(new Department("ECE"));
        college.addDepartment(new Department("ME"));
        
        while (true) {
            System.out.println("\n1. New Admission");
            System.out.println("2. Check Students by Branch");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Branch: ");
                    String branch = sc.nextLine();

                    System.out.print("Enter Semester: ");
                    int sem = sc.nextInt();

                    Student s = new Student(id, name, branch, sem);

                    Department dept = college.getDepartment(branch);

                    if (dept != null) {
                        dept.addStudent(s);
                        System.out.println("Admission Successful!");
                    } else {
                        System.out.println("Department not found!");
                    }
                    break;

                case 2:
                    sc.nextLine();
                    System.out.print("Enter Branch: ");
                    String b = sc.nextLine();

                    Department d = college.getDepartment(b);

                    if (d != null) {
                        d.displayStudents();
                    } else {
                        System.out.println("Department not found!");
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice");

                
            }   

        }
    }
    
}
