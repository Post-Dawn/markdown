package markdown.markdown.view;

import markdown.markdown.socket.Server;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServerPanel extends JPanel {

  public ServerPanel() {
    GridLayout layout = new GridLayout(1, 0);
    this.setLayout(layout);

    JLabel label1 = new JLabel("服务端", JLabel.CENTER);
    JLabel label2 = new JLabel("");
    JLabel label3 = new JLabel("端口号 : ", JLabel.RIGHT);
    final JTextField portField = new JTextField();
    
    this.add(label1);
    this.add(label2);
    this.add(label3);
    this.add(portField);

    this.add(new JButton("建立本地服务器") {{
      this.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            final int port = Integer.parseInt(portField.getText());
            (new Thread() {
              @Override
              public void run() {
                Server server = new Server();
                server.start(port);
              }
            }).start();
            JOptionPane.showMessageDialog(null, "服务器建立成功");
          } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "错误的端口");
          }
        }
      });
    }});

  }
}
