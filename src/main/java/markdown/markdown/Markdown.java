package markdown.markdown;

import markdown.markdown.socket.TextFile;
import markdown.markdown.view.ClientPanel;
import markdown.markdown.view.ContentsPanel;
import markdown.markdown.view.Menu;
import markdown.markdown.view.ServerPanel;
import markdown.markdown.view.inputPanel;
import markdown.markdown.view.WebPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import markdown.markdown.socket.index;

public class Markdown extends JFrame{
	private ClientPanel clientPanel;
	private ServerPanel serverPanel;
	private inputPanel inputPanel;
	private Menu menu;
	private WebPanel webPanel;
	private ContentsPanel contentsPanel;
	//private FilePanel filePanel;
	
	  public Markdown() {
		    super("Markdown Editor");
		    index index = new index();
		    TextFile textFile = new TextFile(index);
		    
		    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    this.setVisible(true);
		    this.setResizable(true);
		    this.setResizable(false);

		    Container content = this.getContentPane();
		    content.setLayout(new BorderLayout());

		    // add server panel
		    this.serverPanel = new ServerPanel();
		    // add client panel
		    this.clientPanel = new ClientPanel(index);

		    JPanel roof = new JPanel();
		    roof.setLayout(new BorderLayout());
		    roof.add(this.clientPanel, BorderLayout.NORTH);
		    roof.add(this.serverPanel, BorderLayout.SOUTH);
		    content.add(roof, BorderLayout.NORTH);
		    this.pack();

		    // add text area panel
		    this.inputPanel = new inputPanel(index);
		    content.add(this.inputPanel, BorderLayout.CENTER);
		    this.pack();
		    final Dimension textSize = this.inputPanel.getSize();
		    this.contentsPanel = new ContentsPanel();
		    this.contentsPanel.setPreferredSize(new Dimension(100, textSize.height));
		    this.webPanel = new WebPanel(index, this.contentsPanel);
		    this.webPanel.setPreferredSize(textSize);
		    
		    // add web preview panel and catelog panel
		    content.add(contentsPanel,BorderLayout.WEST);
		    content.add(webPanel,BorderLayout.EAST);
		    this.pack();

		    // menu bar
		    this.menu = new Menu(index, inputPanel);
		    this.setJMenuBar(menu);
		    this.pack();

		    //this.revalidate();
		    this.repaint();
		  }
	
	
  public static void main(String[] args) {
    Markdown markDown = new Markdown();
  }
}
