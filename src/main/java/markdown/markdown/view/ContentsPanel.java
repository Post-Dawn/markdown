package markdown.markdown.view;

import javafx.application.Platform;
import markdown.markdown.Markdown;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javax.swing.SwingUtilities;

public class ContentsPanel extends JFXPanel {

  private WebView webView;

  public ContentsPanel() {
	    final ContentsPanel now = this;
	    Platform.runLater(new Runnable() {
	      public void run() {
	        webView = new WebView();
	        now.setScene(new Scene(webView));
	        ((Markdown) SwingUtilities.getRoot(now)).pack();
	      }
	    });
  }

  public void updateContent(final String html) {
	  final StringBuffer style = new StringBuffer("<style>");
      style.append("body { background: antiquewhite; color: maroon; }");
      style.append("h1, h2, h3, h4, h5, h6 { margin: 0; padding: 0; line-height: 100%; }");
      style.append("h1 { font-size: 12pt; }");
      style.append("h2 { font-size: 10pt; }");
      style.append("h3 { font-size: 8pt; }");
      style.append("h4 { font-size: 8pt; }");
      style.append("h5 { font-size: 8pt; }");
      style.append( "h6 { font-size: 8pt; }");
      style.append("</style>");
    Platform.runLater(new Runnable() {
      public void run() {
        webView.getEngine().loadContent(style.toString() + html);
      }
    });
  }
}