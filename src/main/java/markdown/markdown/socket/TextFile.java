package markdown.markdown.socket;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextFile {
  private index index;

  public TextFile(index index) {
    this.index = index;
  }

  public void open(File file) {
    try {
      byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
      String content = new String(encoded, StandardCharsets.UTF_8);
      index.textUpdated(content);
    } 
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
