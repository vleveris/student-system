package studSystem;

import javax.swing.*;
import java.util.ArrayList;

public class EditGroup extends JDialog {
    private final ArrayList<Student> groupStudents;
    private final Group group;
    private final DefaultListModel listModel = new DefaultListModel();
    private final ArrayList<String> dates;
    private JCheckBox[] dateItems;

    public EditGroup(JFrame owner, String groupTitle) {
        super(owner, true);
        group = Lists.getGroups().get(Lists.findGroupByTitle(groupTitle));
        groupStudents = group.getStudents();
        dates = Lists.getDates();
        showEditDialog();
    }

    private void showEditDialog() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
        setTitle("Edit group");
        JPanel content = new JPanel();
        getContentPane().add(content);
        JLabel titleLabel = new JLabel("Title:");
        setList();
        JTextField titleField = new JTextField(20);
        titleLabel.setLabelFor(titleField);
        titleField.setDocument(new MaxLengthLimit());
        titleField.setText(group.getTitle());
        JButton save = new JButton("Save");
        save.addActionListener(saveE -> {
            if (titleField.getText().length() == 0)
                JOptionPane.showMessageDialog(this, "Title field cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            else {
                if (group.getTitle().equals(titleField.getText())) {
                } else {
                    if (Lists.groupExists(titleField.getText()))
                        JOptionPane.showMessageDialog(this, "This group already exists", "Error", JOptionPane.ERROR_MESSAGE);
                    else {
                        String oldTitle = group.getTitle();
                        group.setTitle(titleField.getText());
                        ArrayList<Student> students = Lists.getStudents();
                        for (Student student : students)
                            if (student.getGroup().equals(oldTitle))
                                student.setGroup(titleField.getText());
                    }
                }
                for (Student groupStudent : groupStudents)
                    for (int j = 0; j < dates.size(); j++)
                        if (dateItems[j].isSelected())
                            groupStudent.markDate(dateItems[j].getText());
                        else
                            groupStudent.unmarkDate(dateItems[j].getText());
            }
        });
        JLabel listTitle = new JLabel("Group students");
        JList<String> list = new JList<String>(listModel);
        listTitle.setLabelFor(list);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(list);
        JButton add = new JButton("Add new student...");
        add.addActionListener(addE -> {
                    new AddStudent(group.getTitle(), this);
                    setList();
                }
        );
        JButton remove = new JButton("Remove selected student");
        remove.addActionListener(removeE -> {
            if (list.getSelectedIndex() == -1)
                JOptionPane.showMessageDialog(this, "No student selected", "Error", JOptionPane.INFORMATION_MESSAGE);
            else {
                Lists.removeStudent(groupStudents.get(list.getSelectedIndex()));
                setList();
            }
        });
        JLabel datesTitle = new JLabel("Group dates:");
        dateItems = new JCheckBox[dates.size()];
        for (int i = 0; i < dates.size(); i++) {
            dateItems[i] = new JCheckBox(dates.get(i));
            for (Student groupStudent : groupStudents)
                if (groupStudent.getDates().contains(Lists.getDates().get(i))) {
                    dateItems[i].setSelected(true);
                    break;
                }
        }
        CheckBoxList dateList = new CheckBoxList();
        dateList.setListData(dateItems);
        JScrollPane datesScroll = new JScrollPane(dateList);
        JButton close = new JButton("Close");
        close.addActionListener(closeE -> dispose());
        content.add(titleLabel);
        content.add(titleField);
        content.add(listTitle);
        content.add(scroll);
        content.add(add);
        content.add(remove);
        content.add(datesTitle);
        content.add(datesScroll);
        content.add(save);
        content.add(close);
        setVisible(true);
    }

    private void setList() {
        listModel.clear();
        for (Student groupStudent : groupStudents)
            listModel.addElement(groupStudent.getFirstName() + " " + groupStudent.getLastName());
    }
}