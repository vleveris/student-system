package studSystem;

import java.util.ArrayList;

public class Group {
    private String title;
    private int studentNumber = 0;
    private final ArrayList<Student> students = new ArrayList<>();

    public Group(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addStudent(Student student) {
        students.add(student);
        studentNumber++;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void removeStudent(Student old) {
        studentNumber--;
        for (int i = 0; i < students.size(); i++)
            if (old.equals(students.get(i)))
                students.remove(i);
    }

}