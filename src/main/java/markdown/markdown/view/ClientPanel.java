package markdown.markdown.view;

import markdown.markdown.socket.index;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientPanel extends JPanel {

  public ClientPanel(final index index) {
    this.setLayout(new GridLayout(1, 0));

    JLabel label = new JLabel("客户端", JLabel.CENTER);
    final JTextField ipInput = new JTextField();
    final JTextField portInput = new JTextField();
    
    this.add(label);
    this.add(ipInput);
    this.add(portInput);

    this.add(new JButton("连接") {{
      this.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          try 
          {
            String ip = ipInput.getText();
            int port = Integer.parseInt(portInput.getText());
            try 
            {
              index.client.connect(ip, port);
            } 
            catch (Exception e) {
              JOptionPane.showMessageDialog(null, "连接失败");
              return;
            }
            JOptionPane.showMessageDialog(null, "连接成功");
          } 
          catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "错误的端口号");
          }
        }
      });
    }});

    this.add(new JButton("断开连接") {{
      this.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          index.client.disconnect();
        }
      });
    }});
  }
}
