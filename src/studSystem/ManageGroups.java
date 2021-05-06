package studSystem;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class ManageGroups extends JFrame implements WindowFocusListener {
    private final TableModel tableModel = new TableModel();
    private JTable table;

    public ManageGroups() {
        super();
        showWindow();
    }

    private void showWindow() {
        setTitle("Group manager");
        setResizable(false);
        addWindowFocusListener(this);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 500);
        JPanel content = new JPanel();
        getContentPane().add(content);
        JLabel listTitle = new JLabel("Groups: ");
        setTable(false);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setViewportView(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setBounds(20, 20, 180, 180);
        scroll.setBounds(20, 20, 180, 180);
        content.add(listTitle);
        content.add(scroll);
        JButton edit = new JButton("Edit selected group...");
        edit.addActionListener(e -> processSelection()
        );
        JButton create = new JButton("Create new group...");
        create.addActionListener(e -> new CreateGroup(ManageGroups.this, "Create new group", true)
        );
        JButton remove = new JButton("Remove selected group");
        remove.addActionListener(e -> processRemoval()
        );
        JButton ok = new JButton("Close");
        ok.addActionListener(e -> dispose()
        );
        content.add(create);
        content.add(edit);
        content.add(remove);
        content.add(ok);
        setVisible(true);
        show();
    }

    private void setTable(boolean sort) {
        if (!sort) {
            String[] cols = {"Group title", "Number of students"};
            tableModel.setColumnNames(cols);
        }
        String[][] data = new String[Lists.getGroups().size()][2];
        for (int i = 0; i < Lists.getGroups().size(); i++) {
            data[i][0] = Lists.getGroups().get(i).getTitle();
            data[i][1] = Integer.toString(Lists.getGroups().get(i).getStudentNumber());
        }
        tableModel.setData(data);
        if (sort)
            table.setAutoCreateRowSorter(true);
    }

    private void processSelection() {
        int[] selectedRow = table.getSelectedRows();
        if (selectedRow.length == 0) {
            JOptionPane.showMessageDialog(this, "No group selected", "Selection error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        selectedRow[0] = table.convertRowIndexToModel(selectedRow[0]);
        new EditGroup(this, Lists.getGroups().get(selectedRow[0]).getTitle());
    }

    private void processRemoval() {
        int[] selectedRow = table.getSelectedRows();
        if (selectedRow.length == 0) {
            JOptionPane.showMessageDialog(this, "No group selected", "Removal error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        selectedRow[0] = table.convertRowIndexToModel(selectedRow[0]);
        Lists.removeGroup(selectedRow[0]);
        setTable(true);
    }

    public void windowGainedFocus(WindowEvent e) {
        setTable(true);
    }

    public void windowLostFocus(WindowEvent e) {
    }
}