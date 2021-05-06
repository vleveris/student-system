package studSystem;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;

import static javax.swing.JOptionPane.showMessageDialog;

public class AddDates extends JDialog implements WindowFocusListener, ActionListener, ItemListener {
    private final DefaultListModel listModel = new DefaultListModel();
    private final JList<String> list = new JList<String>(listModel);
    private final JRadioButton single = new JRadioButton("Single date");
    private final JRadioButton interval = new JRadioButton("Date interval");
    private final JLabel singleLabel = new JLabel("Enter date (YYYY-MM-DD format)");
    private final JTextField singleDate = new JTextField(10);
    private final JLabel fromLabel = new JLabel("From:");
    private final JTextField fromDate = new JTextField(10);
    private final JLabel toLabel = new JLabel("To:");
    private final JTextField toDate = new JTextField(10);
    private final JButton set = new JButton("Set");
    private final JButton close = new JButton("Close");

    public AddDates() {
        super();
        showDatesFrame();
    }

    private void showDatesFrame() {
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Add new dates");
        JPanel content = new JPanel();
        getContentPane().add(content);
        JLabel listTitle = new JLabel("Dates:");
        setList();
        JScrollPane scroll = new JScrollPane(list);
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
        set.addActionListener(this);
        close.addActionListener(this);
        content.add(listTitle);
        content.add(scroll);
        content.add(single);
        content.add(interval);
        content.add(singleLabel);
        content.add(singleDate);
        content.add(fromLabel);
        content.add(fromDate);
        content.add(toLabel);
        content.add(toDate);
        content.add(set);
        content.add(close);
        setVisible(true);
    }

    private void setList() {
        listModel.clear();
        for (int i = 0; i < Lists.getDates().size(); i++)
            listModel.addElement(Lists.getDates().get(i));
    }

    public void windowGainedFocus(WindowEvent e) {
        setList();
    }

    public void windowLostFocus(WindowEvent e) {
    }

    private boolean checkDate(String date) {
        try {
            LocalDate.parse(date);
        } catch (Exception notValid) {
            showMessageDialog(this, "Date is not valid", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
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
        if (e.getSource() == set) {
            if (single.isSelected()) {
                if (!checkDate(singleDate.getText()))
                    return;
                else
                    Lists.addDate(singleDate.getText());
                setList();
            } else {
                if (!checkDate(fromDate.getText()))
                    return;
                if (!checkDate(toDate.getText()))
                    return;
                String from = fromDate.getText();
                String to = toDate.getText();
                LocalDate increasingDate = LocalDate.parse(from);
                do {
                    from = increasingDate.toString();
                    Lists.addDate(from);
                    increasingDate = increasingDate.plusDays(1);
                }
                while (!from.equals(to));
                setList();
            }
        } else if (e.getSource() == close)
            dispose();
    }
}
