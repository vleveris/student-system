package studSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class ManageStudents extends JFrame implements WindowFocusListener, ActionListener {
    private final DefaultListModel listModel = new DefaultListModel();
    private final JList list = new JList<String>(listModel);
    private final JButton add = new JButton("Add new student...");
    private final JButton remove = new JButton("Remove selected student");
    private final JButton change = new JButton("Change");
    private final JButton close = new JButton("Close");

    public ManageStudents() {
        super();
        showFrame();
    }

    private void showFrame() {
        setTitle("Manage students");
        setResizable(false);
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel content = new JPanel();
        getContentPane().add(content);
        JLabel listTitle = new JLabel("Students:");
        JScrollPane scroll = new JScrollPane(list);
        setList();
        add.addActionListener(this);
        remove.addActionListener(this);
        change.addActionListener(this);
        close.addActionListener(this);
        addWindowFocusListener(this);
        content.add(listTitle);
        content.add(scroll);
        content.add(add);
        content.add(remove);
        content.add(change);
        content.add(close);
        setVisible(true);
        show();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add)
            new AddStudent(this);
        else if (e.getSource() == close)
            dispose();
        else if (e.getSource() == remove) {
            if (list.getSelectedIndex() < 0)
                showMessageDialog(this, "No student selected", "Selection error", JOptionPane.WARNING_MESSAGE);
            else {
                Lists.removeStudent(Lists.getStudents().get(list.getSelectedIndex()));
                setList();
            }
        } else if (list.getSelectedIndex() < 0)
            showMessageDialog(this, "No student selected", "Selection error", JOptionPane.WARNING_MESSAGE);
        else
            new EditStudent(this, Lists.getStudents().get(list.getSelectedIndex()));
    }

    public void windowGainedFocus(WindowEvent e) {
        setList();
    }

    public void windowLostFocus(WindowEvent e) {
    }

    private void setList() {
        listModel.clear();
        for (int i = 0; i < Lists.getStudents().size(); i++)
            listModel.addElement(Lists.getStudents().get(i).getFirstName() + " " + Lists.getStudents().get(i).getLastName());
    }
}