package markdown.markdown.view;

import markdown.markdown.socket.index;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JMenuBar {

  public Menu(final index index, final inputPanel inputpanel) {
    JMenu menu = new JMenu("File");
    menu.add(
        new JMenuItem("打开(open)") {
          {
            this.addActionListener(
                new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                    JFileChooser Chooser = new JFileChooser();
                    Chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    
                    if (Chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                    {
                        String charset = "utf-8";
                        File file=Chooser.getSelectedFile();
                        long fileByteLength = file.length();
                        byte[] content = new byte[(int)fileByteLength];
                        FileInputStream fileInputStream = null;
                        try {
                            fileInputStream = new FileInputStream(file);
                            fileInputStream.read(content);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } finally {
                            try {
                                fileInputStream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        String str = null;
                		try {
                			str = new String(content,charset);
                			index.setContent(str);
                  	  		inputPanel.setTextArea(str);
                		} catch (UnsupportedEncodingException e1) {
                			e1.printStackTrace();
                		}
                    	
                    }
                  }
                });
          }
        });
    menu.add(
        new JMenuItem("保存(save)") {
          {
            this.addActionListener(
                new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                    JFileChooser Chooser = new JFileChooser();
                    if (Chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                      File file = Chooser.getSelectedFile();
                      String content = index.content;
                      try {
                    	  PrintWriter output = new PrintWriter(file.getAbsolutePath());
                          output.print(content);
                          output.close();
                      } catch (IOException exception) {
                        exception.printStackTrace();
                      }
                    }
                  }
                });
          }
        });
    menu.add(
        new JMenuItem("导出html") {
          {
            this.addActionListener(
                new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                    JFileChooser Chooser = new JFileChooser();
                    if (Chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                      File file = Chooser.getSelectedFile();
                      String content = index.transferHtml();
                      try {
                    	  PrintWriter output = new PrintWriter(file.getAbsolutePath());
                          output.print(content);
                          output.close();
                      } catch (IOException exception) {
                        exception.printStackTrace();
                      }
                    }
                  }
                });
          }
        });
    this.add(menu);
  }
}
