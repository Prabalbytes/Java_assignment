public class Student {
    private int id;
    private String name;
    private String branch;
    private int semester;

    public Student(int id, String name, String branch, int semester) {
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.semester = semester;
    }

    public int getId() {
        return id;
    }

    public String getBranch() {
        return branch;
    }

    public void display() {
        System.out.println("ID: " + id + ", Name: " + name +
                ", Branch: " + branch + ", Semester: " + semester);
    }
}
