package markdown.markdown.view;

import markdown.markdown.Markdown;
import markdown.markdown.socket.index;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javax.swing.SwingUtilities;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebPanel extends JFXPanel {

	  private index index;
	  private WebView webView;
	  private ContentsPanel contentsPanel;

	  public WebPanel(index text, ContentsPanel contentsPanel) {
	    this.contentsPanel = contentsPanel;
	    this.index = text;
	    this.index.setWebPanel(this);
	    this.setSceneLater();
	  }

	  private void setSceneLater() {
	    final WebPanel panel = this;
	    Platform.runLater(new Runnable() {
	      public void run() {
	        webView = new WebView();
	        panel.setScene(new Scene(webView));
	        ((Markdown) SwingUtilities.getRoot(panel)).pack();
	      }
	    });
	  }

	  public void updateContent(String content) {
	    Parser parser = Parser.builder().build();
	    Node document = parser.parse(content);
	    HtmlRenderer renderer = HtmlRenderer.builder().build();
	    final String html = renderer.render(document);
	    Platform.runLater(new Runnable() {
	      public void run() {
	        webView.getEngine().loadContent(html);
	      }
	    });

	    getTableOfContent(html);
	  }

	  private void getTableOfContent(String html) {
	    Document document = Jsoup.parse(html);
	    Element body = document.body();
	    Elements headers = body.select("h1, h2, h3, h4, h5, h6");
	    contentsPanel.updateContent(headers.outerHtml());
	  }
	}