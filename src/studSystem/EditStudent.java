package studSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

import static javax.swing.JOptionPane.showMessageDialog;

public class EditStudent extends JDialog implements ActionListener {
    private final Student student;
    private final JButton save = new JButton("Save");
    private final JButton cancel = new JButton("Cancel");
    private final JTextField firstNameField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private final ArrayList<String> dates;
    private final JCheckBox[] dateItems;
    private final JComboBox groups = new JComboBox();

    public EditStudent(JFrame owner, Student student) {
        super(owner, true);
        this.student = student;
        dates = Lists.getDates();
        dateItems = new JCheckBox[dates.size()];
        showModalDialog();
    }

    private void showModalDialog() {
        setTitle("Edit student");
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel content = new JPanel();
        getContentPane().add(content);
        JLabel firstNameLabel = new JLabel("First name:");
        firstNameLabel.setLabelFor(firstNameField);
        firstNameField.setDocument(new MaxLengthLimit());
        firstNameField.setText(student.getFirstName());
        JLabel lastNameLabel = new JLabel("Last name:");
        lastNameLabel.setLabelFor(lastNameField);
        lastNameField.setDocument(new MaxLengthLimit());
        lastNameField.setText(student.getLastName());
        JLabel groupLabel = new JLabel("Group:");
        groupLabel.setLabelFor(groups);
        for (int i = 0; i < Lists.getGroups().size(); i++)
            groups.addItem(Lists.getGroups().get(i).getTitle());
        groups.setSelectedItem(student.getGroup());
        save.addActionListener(this);
        JLabel datesTitle = new JLabel("Student dates: ");
        for (int i = 0; i < dates.size(); i++) {
            dateItems[i] = new JCheckBox(dates.get(i));
            for (int j = 0; j < student.getDates().size(); j++)
                if (student.getDates().get(j).contains(dates.get(i))) {
                    dateItems[i].setSelected(true);
                }
        }
        CheckBoxList dateList = new CheckBoxList();
        dateList.setListData(dateItems);
        JScrollPane datesScroll = new JScrollPane(dateList);
        cancel.addActionListener(this);
        content.add(firstNameLabel);
        content.add(firstNameField);
        content.add(lastNameLabel);
        content.add(lastNameField);
        content.add(groupLabel);
        content.add(groups);
        content.add(datesTitle);
        content.add(datesScroll);
        content.add(save);
        content.add(cancel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            if (checkField(firstNameField)) return;
            if (checkField(lastNameField)) return;
            if (Objects.requireNonNull(groups.getSelectedItem()).toString().length() == 0) {
                showMessageDialog(this, "No group selected", "Selection error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String newFirstName = firstNameField.getText();
            String newLastName = lastNameField.getText();
            String newGroup = groups.getSelectedItem().toString();
            if (student.getFirstName().equals(newFirstName) && student.getLastName().equals(newLastName) && student.getGroup().equals(newGroup)) {
            } else {
                Student changed = new Student(newFirstName, newLastName, newGroup);
                if (Lists.studentExists(changed)) {
                    showMessageDialog(this, "This name is set for another student", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!student.getGroup().equals(newGroup)) {
                    Lists.getGroups().get(Lists.findGroupByTitle(student.getGroup())).removeStudent(student);
                    Lists.getGroups().get(Lists.findGroupByTitle(newGroup)).addStudent(student);
                    student.setGroup(newGroup);
                }
                Lists.getStudents().get(Lists.findStudentByName(student)).setFirstName(newFirstName);
                Lists.getStudents().get(Lists.findStudentByName(student)).setLastName(newLastName);
            }
            for (int i = 0; i < dates.size(); i++)
                if (dateItems[i].isSelected())
                    student.markDate(dateItems[i].getText());
                else
                    student.unmarkDate(dateItems[i].getText());
            dispose();
        } else
            dispose();
    }

    private boolean checkField(JTextField input) {
        if (input.getText().length() == 0) {
            showMessageDialog(this, "Field cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }
}