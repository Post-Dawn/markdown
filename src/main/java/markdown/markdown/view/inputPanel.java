package markdown.markdown.view;

import markdown.markdown.Config;
import markdown.markdown.socket.index;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class inputPanel extends JPanel {

  private index index;
  private static JTextArea textArea;
  private int caretPosition;

  public void setText(String text) {
    this.textArea.setText(text);
    try {
      this.textArea.setCaretPosition(caretPosition);
    } catch (IllegalArgumentException e) {
      this.textArea.setCaretPosition(textArea.getDocument().getLength());
    }
  }
  
  public static void setTextArea(String text)
  {
	  textArea.setText(text);
  }

  private void textUpdated() {
    index.setContent(textArea.getText());
  }

  public inputPanel(index index) {
    this.caretPosition = 0;
    this.index = index;
    this.index.setTextAreaPanel(this);

    textArea = new JTextArea(Config.TEXT_AREA_ROWS, Config.TEXT_AREA_COLUMNS);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.addCaretListener(
        new CaretListener() {
          public void caretUpdate(CaretEvent e) {
            caretPosition = e.getDot();
          }
        }
    );
    textArea.getDocument().addDocumentListener(
        new DocumentListener() {
          public void removeUpdate(DocumentEvent e) {
            textUpdated();
          }

          public void insertUpdate(DocumentEvent e) {
            textUpdated();
          }

          public void changedUpdate(DocumentEvent e) {
            textUpdated();
          }
        }
    );
    this.add(new JScrollPane(textArea));
  }
}
