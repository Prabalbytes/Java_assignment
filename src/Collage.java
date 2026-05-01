import java.util.ArrayList;

public class Collage {
    private String name ;
    private ArrayList<Department> departments;

    public Collage(String name) {
        this.name = name;
        this.departments = new ArrayList<>();
    }

    public void addDepartment(Department d) {
        departments.add(d);
    }

    public Department getDepartment(String name) {
        for (Department d : departments) {
            if (d.getName().equalsIgnoreCase(name)) {
                return d;
            }
        }
        return null;
    }

    public void showDepartments() {
        for (Department d : departments) {
            System.out.println("- " + d.getName());
        }
    }
}