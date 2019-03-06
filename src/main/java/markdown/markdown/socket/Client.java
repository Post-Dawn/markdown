package markdown.markdown.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class Client {

  private index index;
  private DataOutputStream output;
  private DataInputStream input;
  private Socket socket;
  private boolean isConnect;
  private Thread thread;

  public Client() {
    this.isConnect = false;
    this.output = null;
    this.input = null;
    this.socket = null;
    this.thread = null;
  }

  public void setText(index index) {
    this.index = index;
  }
  
  private class TextThread implements Runnable {
	    public void run() {
	      try {
	        while (isConnect) {
	          String mes = input.readUTF();
	          index.textUpdated(mes);
	        }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
	  }

  public void send(String mes) {
	    try {
	    	if (isConnect) 
	    	{
	    		output.writeUTF(mes);
	    		output.flush();
	    	} 
	    	}catch (Exception exception) {
	        exception.printStackTrace();
	      }
  }
  
  public void connect(String host, int port) throws IOException {
    try {
      isConnect = true;
      socket = new Socket(host, port);
      output = new DataOutputStream(socket.getOutputStream());
      input = new DataInputStream(socket.getInputStream());
      thread = new Thread(new TextThread());
      thread.start();
    } catch (IOException e) {
      isConnect = false;
      throw e;
    }
  }

  public void disconnect() {
    if (isConnect) {
      isConnect = false;
      try {
        output.close();
        input.close();
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
