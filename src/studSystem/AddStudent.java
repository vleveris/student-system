package studSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class AddStudent extends JDialog implements ActionListener, WindowFocusListener {
    private String group;
    private final JTextField firstNameField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private boolean needsComboBox = true;
    private final JButton add = new JButton("Add");
    private final JButton cancel = new JButton("Cancel");
    private final DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
    private final JComboBox groupBox = new JComboBox();
    private final ArrayList<Group> groups = Lists.getGroups();

    public AddStudent(String group, JDialog parent) {
        super(parent, true);
        this.group = group;
        needsComboBox = false;
        showAddDialog();
    }

    public AddStudent(JFrame parent) {
        super(parent, true);
        showAddDialog();
    }

    private void showAddDialog() {
        setSize(500, 500);
        setResizable(false);
        addWindowFocusListener(this);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel content = new JPanel();
        getContentPane().add(content);
        JLabel nameLabel = new JLabel("First name:");
        firstNameField.setDocument(new MaxLengthLimit());
        JLabel sernameLabel = new JLabel("Last name:");
        lastNameField.setDocument(new MaxLengthLimit());
        JLabel chooseGroup = new JLabel("Choose group:");
        add.addActionListener(this);
        cancel.addActionListener(this);
        content.add(nameLabel);
        content.add(firstNameField);
        content.add(sernameLabel);
        content.add(lastNameField);
        if (needsComboBox) {
            content.add(chooseGroup);
            groupBox.setModel(comboBoxModel);
            content.add(groupBox);
        }
        content.add(add);
        content.add(cancel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            if (firstNameField.getText().length() == 0) {
                showMessageDialog(this, "First name field must be specified", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (lastNameField.getText().length() == 0) {
                showMessageDialog(this, "Last name field must be specified", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (needsComboBox && groupBox.getSelectedIndex() == -1) {
                showMessageDialog(this, "Group must be selected", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (needsComboBox)
                group = Lists.getGroups().get(groupBox.getSelectedIndex()).getTitle();
            Student student = new Student(firstNameField.getText(), lastNameField.getText(), group);
            if (Lists.studentExists(student)) {
                showMessageDialog(this, "This student already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Lists.addStudent(student);
        }
        dispose();
    }

    private void setList() {
        comboBoxModel.removeAllElements();
        for (Group value : groups) comboBoxModel.addElement(value.getTitle());
    }

    public void windowGainedFocus(WindowEvent e) {
        setList();
    }

    public void windowLostFocus(WindowEvent e) {
    }
}
