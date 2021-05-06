package studSystem;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.stream.Stream;

import static javax.swing.JOptionPane.showMessageDialog;

public class ShowAttendance extends JFrame implements ActionListener, ItemListener {
    private DefaultTableModel model;
    private final JTable table = new JTable();
    private final JRadioButton single = new JRadioButton("Single date");
    private final JRadioButton interval = new JRadioButton("Date interval");
    private final JLabel singleLabel = new JLabel("Enter date (YYYY-MM-DD format)");
    private final JTextField singleDate = new JTextField(10);
    private final JLabel fromLabel = new JLabel("From:");
    private final JTextField fromDate = new JTextField(10);
    private final JLabel toLabel = new JLabel("To:");
    private final JTextField toDate = new JTextField(10);
    private final JButton generate = new JButton("Generate table");
    private final JButton close = new JButton("Close");
    private final JButton save = new JButton("Save to PDF");

    public ShowAttendance() {
        super();
        showAttendanceFrame();
    }

    private void showAttendanceFrame() {
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("View attendance");
        JPanel content = new JPanel();
        getContentPane().add(content);
        JLabel tableTitle = new JLabel("Filtered students:");
        model = (DefaultTableModel) table.getModel();
        model.addColumn("First name");
        model.addColumn("Last name");
        model.addColumn("Group");
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setViewportView(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setBounds(20, 20, 200, 200);
        scroll.setBounds(20, 20, 200, 200);
        ButtonGroup group = new ButtonGroup();
        group.add(single);
        group.add(interval);
        single.addItemListener(this);
        interval.addItemListener(this);
        single.setSelected(true);
        single.setActionCommand("single");
        interval.setActionCommand("interval");
        fromLabel.setVisible(false);
        fromDate.setVisible(false);
        toLabel.setVisible(false);
        toDate.setVisible(false);
        generate.addActionListener(this);
        close.addActionListener(this);
        save.addActionListener(this);
        content.add(tableTitle);
        content.add(scroll);
        content.add(single);
        content.add(interval);
        content.add(singleLabel);
        content.add(singleDate);
        content.add(fromLabel);
        content.add(fromDate);
        content.add(toLabel);
        content.add(toDate);
        content.add(generate);
        content.add(save);
        content.add(close);
        setVisible(true);
    }

    private boolean checkDate(String date) {
        try {
            LocalDate.parse(date);
        } catch (Exception notValid) {
            showMessageDialog(this, "Date is not valid", "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == interval) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                singleLabel.setVisible(false);
                singleDate.setVisible(false);
                fromLabel.setVisible(true);
                fromDate.setVisible(true);
                toLabel.setVisible(true);
                toDate.setVisible(true);
            }
        } else if (e.getStateChange() == ItemEvent.SELECTED) {
            fromLabel.setVisible(false);
            fromDate.setVisible(false);
            toLabel.setVisible(false);
            toDate.setVisible(false);
            singleLabel.setVisible(true);
            singleDate.setVisible(true);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generate) {
            if (single.isSelected()) {
                if (checkDate(singleDate.getText())) {
                }
                else {
                    for (int i = 0; i < model.getRowCount(); i++)
                        model.removeRow(i);
                    for (int i = 0; i < Lists.getStudents().size(); i++)
                        for (int j = 0; j < Lists.getStudents().get(i).getDates().size(); j++)
                            if (singleDate.getText().equals(Lists.getStudents().get(i).getDates().get(j))) {
                                String[] row = new String[3];
                                row[0] = Lists.getStudents().get(i).getFirstName();
                                row[1] = Lists.getStudents().get(i).getLastName();
                                row[2] = Lists.getStudents().get(i).getGroup();
                                model.addRow(row);
                                break;
                            }
                }
            } else {
                if (checkDate(fromDate.getText()))
                    return;
                if (checkDate(toDate.getText()))
                    return;
                LocalDate from = LocalDate.parse(fromDate.getText());
                LocalDate to = LocalDate.parse(toDate.getText());
                for (int i = 0; i < model.getRowCount(); i++)
                    model.removeRow(i);
                for (int i = 0; i < Lists.getStudents().size(); i++)
                    for (int j = 0; j < Lists.getStudents().get(i).getDates().size(); j++) {
                        LocalDate checking = LocalDate.parse(Lists.getStudents().get(i).getDates().get(j));
                        if (from.equals(checking) || to.equals(checking) || (from.isBefore(checking) && to.isAfter(checking))) {
                            String[] row = new String[3];
                            row[0] = Lists.getStudents().get(i).getFirstName();
                            row[1] = Lists.getStudents().get(i).getLastName();
                            row[2] = Lists.getStudents().get(i).getGroup();
                            model.addRow(row);
                            break;
                        }
                    }
            }
        } else if (e.getSource() == close)
            dispose();
        else {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));
                document.open();
                Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);
                String message = "This is a list of students filtered by attendance\n";
                if (single.isSelected())
                    message = message + "at " + singleDate.getText();
                else
                    message = message + "from " + fromDate.getText() + " up to " + toDate.getText();
                Chunk chunk = new Chunk(message, font);
                System.out.println(message);
                document.add(chunk);
                PdfPTable table = new PdfPTable(3);
                Stream.of("First name", "Last name", "Group")
                        .forEach(columnTitle -> {
                            PdfPCell header = new PdfPCell();
                            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            header.setBorderWidth(2);
                            header.setPhrase(new Phrase(columnTitle));
                            table.addCell(header);
                        });
                for (int i = 0; i < model.getRowCount(); i++)
                    for (int j = 0; j < 3; j++)
                        table.addCell(model.getValueAt(i, j).toString());
                document.add(table);
                document.close();
                showMessageDialog(this, "The file output.pdf has been created successfully.", "Information", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception eError) {
                showMessageDialog(this, "An error occured while saving PDF file", "Saving error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
