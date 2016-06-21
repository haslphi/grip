package at.jku.se.grip.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.commons.lang3.StringUtils;

import at.jku.se.grip.beans.Robot;

/**
 * Class for {@link Robot} utility and convenience methods. 
 * 
 * @author grip
 */
public class RobotUtilities {
	public static final int SOCKET_TIMOUT = 5000;
	
	/**
	 * 
	 * 
	 * @param bean
	 * @return true if connection was successful, else false
	 */
	public static final boolean testConnection(Robot bean) {
		boolean connected = false;
		
		try (Socket clientSocket = new Socket()) {
			String host = bean.getHost();
			Integer port = bean.getPort();
			clientSocket.connect(new InetSocketAddress(host, port == null ? 80 : port), SOCKET_TIMOUT);
			clientSocket.setSoTimeout(SOCKET_TIMOUT);
			
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outToServer.writeBytes("{message:\"Hello\"}");
			String response = reader.readLine();
			//				while (!response.contains(".")) {
			//					response = reader.readLine();
			//					System.out.println(response);
			//				}
			reader.close();
			connected = StringUtils.isNotBlank(response);
			LogUtilities.log().debug("Socket Response: " + response);
		} catch (SocketTimeoutException ex) {
			System.out.println("Timeout exeption!");
		} catch (IOException ex) {
			System.out.println("IOException occured!");
		} catch (Exception ex) {
			System.out.println("Server Connection failed!");
		}
		return connected;
	}

}
