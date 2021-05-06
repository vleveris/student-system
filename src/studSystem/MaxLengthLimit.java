package studSystem;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MaxLengthLimit extends PlainDocument {
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null)
            return;
        if ((getLength() + str.length()) <= Lists.maxNameSize) {
            super.insertString(offset, str, attr);
        }
    }
}