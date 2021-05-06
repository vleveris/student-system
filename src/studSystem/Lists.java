package studSystem;

import java.time.LocalDate;
import java.util.ArrayList;

public class Lists {
    public static final int maxNameSize = 50;
    private static final ArrayList<Student> students = new ArrayList<>();
    private static final ArrayList<Group> groups = new ArrayList<>();
    private static final ArrayList<String> dates = new ArrayList<>();

    public static void manageImport(ArrayList<String> input) {
        if (input.size() < 2)
            return;
        String firstName = input.get(0);
        String lastName = input.get(1);
        String group = "Undefined";
        if (firstName.length() > maxNameSize)
            firstName = firstName.substring(0, maxNameSize - 1);
        if (lastName.length() > maxNameSize)
            lastName = lastName.substring(0, maxNameSize - 1);
        if (input.size() > 2) {
            group = input.get(2);
            if (group.length() > maxNameSize)
                group = group.substring(0, maxNameSize - 1);
        }
        boolean found = false;
        int groupIndex = -1;
        for (int i = 0; i < groups.size(); i++)
            if (groups.get(i).getTitle().equals(group)) {
                found = true;
                groupIndex = i;
                break;
            }
        if (!found) {
            groups.add(new Group(group));
            groupIndex = groups.size() - 1;
        }
        Student student = new Student(firstName, lastName, group);
        if (!studentExists(student)) {
            groups.get(groupIndex).addStudent(student);
            students.add(student);
            if (input.size() > 3) {
                int index = 3;
                do {
                    try {
                        LocalDate.parse(input.get(index));
                        student.markDate(input.get(index));
                        String dateString = input.get(index);
                        if (!dates.contains(dateString))
                            dates.add(dateString);
                    } catch (Exception ignored) {
                    }
                    index++;
                }
                while (index != input.size());
            }
        }
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    public static ArrayList<Group> getGroups() {
        return groups;
    }

    public static boolean groupExists(String groupName) {
        for (Group group : groups)
            if (group.getTitle().equals(groupName))
                return true;
        return false;
    }

    public static boolean studentExists(Student newStudent) {
        for (Student student : students)
            if (student.getFirstName().equals(newStudent.getFirstName()) && student.getLastName().equals(newStudent.getLastName()) && student.getGroup().equals(newStudent.getGroup()))
                return true;
        return false;
    }

    public static void addStudent(Student newStudent) {
        students.add(newStudent);
        groups.get(findGroupByTitle(newStudent.getGroup())).addStudent(newStudent);
    }

    public static int findGroupByTitle(String title) {
        int index = -1;
        for (int i = 0; i < groups.size(); i++)
            if (title.equals(groups.get(i).getTitle())) {
                index = i;
                break;
            }
        return index;
    }

    public static void removeStudent(Student old) {
        students.remove(findStudentByName(old));
        groups.get(findGroupByTitle(old.getGroup())).removeStudent(old);
    }

    public static int findStudentByName(Student lookingFor) {
        int index = -1;
        for (int i = 0; i < students.size(); i++)
            if (students.get(i).equals(lookingFor)) {
                index = i;
                break;
            }
        return index;
    }

    public static void removeGroup(int groupIndex) {
        for (int i = 0; i < groups.get(groupIndex).getStudents().size(); i++)
            removeStudent(groups.get(groupIndex).getStudents().get(i));
        groups.remove(groupIndex);
    }

    public static void addDate(String value) {
        if (!dates.contains(value))
            dates.add(value);
    }

    public static ArrayList<String> getDates() {
        return dates;
    }
}