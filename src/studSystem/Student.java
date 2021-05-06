package studSystem;

import java.util.ArrayList;

public class Student {
    private String firstName, lastName, group;
    private final ArrayList<String> dates = new ArrayList<>();

    public Student(String firstName, String lastName, String group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public void markDate(String date) {
        if (!dates.contains(date))
            dates.add(date);
    }

    public void unmarkDate(String date) {
        for (int i = 0; i < dates.size(); i++)
            if (dates.get(i).equals(date))
                dates.remove(i);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public ArrayList<String> getDates() {
        return dates;
    }
}