package studSystem;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckBoxList extends JList {
    protected static Border noFocusBorder =
            new EmptyBorder(1, 1, 1, 1);

    public CheckBoxList() {
        setCellRenderer(new CellRenderer());

        addMouseListener(new MouseAdapter() {
                             public void mousePressed(MouseEvent e) {
                                 int index = locationToIndex(e.getPoint());

                                 if (index != -1) {
                                     JCheckBox checkbox = (JCheckBox)
                                             getModel().getElementAt(index);
                                     checkbox.setSelected(
                                             !checkbox.isSelected());
                                     repaint();
                                 }
                             }
                         }
        );
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int index = getSelectedIndex();
                if (index != -1 && e.getKeyCode() == KeyEvent.VK_SPACE) {
                    boolean newVal = !((JCheckBox) (getModel()
                            .getElementAt(index))).isSelected();
                    for (int i : getSelectedIndices()) {
                        JCheckBox checkbox = (JCheckBox) getModel()
                                .getElementAt(i);
                        checkbox.setSelected(newVal);
                        repaint();
                    }
                }
            }
        });
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    protected class CellRenderer implements ListCellRenderer {
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            JCheckBox checkbox = (JCheckBox) value;
            checkbox.setBackground(isSelected ?
                    getSelectionBackground() : getBackground());
            checkbox.setForeground(isSelected ?
                    getSelectionForeground() : getForeground());
            checkbox.setEnabled(isEnabled());
            checkbox.setFont(getFont());
            checkbox.setFocusPainted(false);
            checkbox.setBorderPainted(true);
            checkbox.setBorder(isSelected ?
                    UIManager.getBorder(
                            "List.focusCellHighlightBorder") : noFocusBorder);
            return checkbox;
        }
    }
}