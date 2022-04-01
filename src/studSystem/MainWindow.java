package studSystem;

import javax.swing.*;
import java.io.File;

public class MainWindow extends JFrame {
    private final JFileChooser fc = new JFileChooser();

    public MainWindow() {
        showWindow();
    }

    private void showWindow() {
        fc.setCurrentDirectory(new File("../"));
        fc.setAcceptAllFileFilterUsed(false);
        setTitle("Students Registration System");
        setResizable(false);
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel content = new JPanel();
        getContentPane().add(content);
        JPanel importExport = new JPanel();
        JButton importBtn = new JButton("Import students...");
        importBtn.addActionListener(e -> importFromFile()
        );
        JButton exportBtn = new JButton("Export students...");
        exportBtn.addActionListener(e -> exportToFile()
        );
        importExport.add(importBtn);
        importExport.add(exportBtn);
        JPanel manage = new JPanel();
        JButton groupsBtn = new JButton("Manage groups...");
        JButton studentsBtn = new JButton("Manage Students...");
        groupsBtn.addActionListener(e -> new ManageGroups()
        );
        studentsBtn.addActionListener(e -> new ManageStudents()
        );
        manage.add(groupsBtn);
        manage.add(studentsBtn);
        JPanel attendance = new JPanel();
        JButton datesBtn = new JButton("Add/remove dates...");
        datesBtn.addActionListener(e -> new AddDates()
        );
        JButton viewBtn = new JButton("View attendance...");
        viewBtn.addActionListener(e -> new ShowAttendance()
        );
        attendance.add(datesBtn);
        attendance.add(viewBtn);
        content.add(importExport);
        content.add(manage);
        content.add(attendance);
        setVisible(true);
        show();
    }

    private void importFromFile() {
        fc.setDialogTitle("Choose student file:");
        int val = fc.showOpenDialog(this);
        if (val == JFileChooser.APPROVE_OPTION) {
            String fileName = fc.getSelectedFile().toString();
            String extension = fileName.substring(fileName.indexOf('.') + 1).toLowerCase();
            switch (extension) {
                case "csv" -> new CSV(fileName).importFromCSV();
                case "xlsx" -> new Excel(fileName).importFromXLSX();
                case "xls" -> new Excel(fileName).importFromXLS();
                default -> JOptionPane.showMessageDialog(this, "File format is not acceptable", "Import error", JOptionPane.ERROR_MESSAGE);
            }
        } else
            JOptionPane.showMessageDialog(this, "File was not chosen", "Import error", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportToFile() {
        fc.setDialogTitle("Choose file to save students:");
        int val = fc.showSaveDialog(this);
        if (val == JFileChooser.APPROVE_OPTION) {
            String fileName = fc.getSelectedFile().toString();
            String extension = fileName.substring(fileName.indexOf('.') + 1).toLowerCase();
            switch (extension) {
                case "csv" -> new CSV(fileName).export2CSV();
                case "xlsx" -> new Excel(fileName).export2XLSX();
                case "xls" -> new Excel(fileName).export2XLS();
                default -> JOptionPane.showMessageDialog(this, "File format is not acceptable", "Export error", JOptionPane.ERROR_MESSAGE);
            }
        } else
            JOptionPane.showMessageDialog(this, "File was not chosen", "Export error", JOptionPane.INFORMATION_MESSAGE);
    }
}