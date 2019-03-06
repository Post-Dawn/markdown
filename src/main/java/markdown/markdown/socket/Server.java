package markdown.markdown.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

  private ArrayList<TextThread> textThreads;

  public Server() {
    this.textThreads = new ArrayList<TextThread>();
  }

  public void start(int port) {
    try {
      ServerSocket server = new ServerSocket(port);
      while (true) {
        Socket socket = server.accept();
        TextThread thread = new TextThread(socket);
        textThreads.add(thread);
        new Thread(thread).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private class TextThread implements Runnable {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public TextThread(Socket socket) {
      super();
      this.socket = socket;
      this.input = null;
      this.output = null;
    }

    private void send(String message) {
      try {
        output.writeUTF(message);
        output.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void run() {
      try {
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        while (true) {
          String message = input.readUTF();
          for (TextThread thread : textThreads) {
            thread.send(message);
          }
        }
      } 
      catch (IOException e) 
      {
        try {
          socket.close();
          textThreads.remove(this);
        } 
        catch (IOException e1) {
          e1.printStackTrace();
        }
      } 
      finally {
        try {
          input.close();
          output.close();
          socket.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

  }
}

