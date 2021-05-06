package studSystem;

import javax.swing.*;
import java.util.ArrayList;

public class CreateGroup extends JDialog {
    public CreateGroup(JFrame owner, String title, boolean modal) {
        super(owner, title, modal);
        showDialog();
    }

    public void showDialog() {
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel content = new JPanel();
        getContentPane().add(content);
        JLabel label = new JLabel("Enter title (max. " + Lists.maxNameSize + " symbols):");
        JTextField title = new JTextField();
        title.setDocument(new MaxLengthLimit());
        title.setText("New group");
        title.selectAll();
        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(e -> {
            if (title.getText().length() == 0)
                JOptionPane.showMessageDialog(CreateGroup.this, "Title field cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            else {
                ArrayList<Group> groups = Lists.getGroups();
                if (Lists.groupExists(title.getText())) {
                    JOptionPane.showMessageDialog(CreateGroup.this, "This group is already in the list", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                groups.add(new Group(title.getText()));
                dispose();
            }
        });
        content.add(label);
        content.add(title);
        content.add(confirm);
        setVisible(true);
    }
}