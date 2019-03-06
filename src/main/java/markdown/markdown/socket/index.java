package markdown.markdown.socket;

import markdown.markdown.view.inputPanel;
import markdown.markdown.view.WebPanel;
import java.util.concurrent.Semaphore;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class index {
	  private WebPanel webPanel;
	  private inputPanel textAreaPanel;
	  public Client client;
	  public String content;
	  private Semaphore exclusive;
	  private boolean locked;

	  public index() {//初始化
	    this.content = "";
	    this.client = new Client();
	    this.client.setText(this);
	    this.exclusive = new Semaphore(1);
	    this.locked = false;
	  }
	  public void setContent(String content) {//设置内容
	    if (!this.locked) {
	      try {
	        this.exclusive.acquire(1);
	        this.content = content;
	        this.client.send(this.content);
	        this.webPanel.updateContent(content);
	      } catch (InterruptedException e) {
	      } finally {
	        this.exclusive.release(1);
	      }
	    }
	  }
	  public String transferHtml()//转换成html
	  {
	    Parser parser = Parser.builder().build();
	    Node document = parser.parse(content);
	    HtmlRenderer renderer = HtmlRenderer.builder().build();
	    String html = renderer.render(document);
	    return html;
	  }
	  public void setTextAreaPanel(inputPanel textAreaPanel) {
	    this.textAreaPanel = textAreaPanel;
	  }
	  public void setWebPanel(WebPanel webPanel) {
	    this.webPanel = webPanel;
	  }
	  public void textUpdated(String content) {//更新文本内容
	    try {
	      this.locked = true;
	      this.exclusive.acquire(1);
	      this.content = content;
	      this.textAreaPanel.setText(content);
	      this.webPanel.updateContent(content);
	    } catch (InterruptedException e) {
	    } finally {
	      this.exclusive.release(1);
	      this.locked = false;
	    }
	  }
}
