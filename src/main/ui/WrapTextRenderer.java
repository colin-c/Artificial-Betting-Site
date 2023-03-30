package ui;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class WrapTextRenderer extends JTextArea implements TableCellRenderer {
    public WrapTextRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        setText(value.toString());
        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
        int rowHeight = table.getRowHeight(row);
        int cellHeight = getPreferredSize().height;
        if (cellHeight > rowHeight) {
            table.setRowHeight(row, cellHeight);
        }
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
        return this;
    }
}
