
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;

/*
 NetworkingDevicesMain.java

TCSS 430A - Summer 2018
Instructor: D.C. Grant
Students: Raisa Meneses, Denis Yakovlev, Karan Kurbur, Richard Brun
Assignment 02 - Networking Devices
The due date: 06/30/2018 (Saturday) by 11:59pm
*/

/*
The application queries the operating system and runs several 
diagnostic tools on the network.

1. Program determines the host machine's IPv4 and IPv6 addresses and subnet information.
2. Prompts a user for an IP address or a FQDN for a machine to test and accept IPv4 or IPv6.
3. The application queries DNS to determine the IP address for the FQDN, or the FQDN for the IP.
4. The application uses ICMPv4 or ICMPv6 (depending on the input) to ping and trace route to that target. 
5. It provides results in a user friendly format on the screen, and prompts to send the results to a printer.
*/

/**
 * Runs the application.
 *
 * @author Raisa Meneses
 * @author Denis Yakovlev
 * @author Karan Kurbur
 * @author Richard Brun
 * @version 30 June 2017
 */
public class NetworkingDevicesMain {
	
	/**
	 * Driver method.
	 * 
	 * @param args command line args.
	 */
	public static void main(String[] args) throws Exception  {
		
		part1();
		
		
		
	}	
	
	public static void part1() throws UnknownHostException {
		
		// local host name
				InetAddress localHost = InetAddress.getLocalHost();
				String localHostName = localHost.getHostName();
				System.out.println(localHostName);
				
				// ****************************************************
				System.out.println("");
				// ****************************************************
				
				// local host address
				System.out.println(localHost.getHostAddress());
				
				// ****************************************************
				System.out.println("");
				// ****************************************************
				
				// all addresses by local host name
				InetAddress[] addressesArray = InetAddress.getAllByName(localHostName);
				for (int i = 0; i < addressesArray.length; i++) {
					System.out.println(Arrays.toString(addressesArray[i].getAddress()));
				}
				
				// ****************************************************
				System.out.println("");		
				// ****************************************************
				
				// from: https://www.geeksforgeeks.org/java-program-find-ip-address-computer/
				
				// Find public IP address
		        String systemipaddress = "";
		        try
		        {
		            URL url_name = new URL("http://bot.whatismyipaddress.com");
		 
		            BufferedReader sc =
		            new BufferedReader(new InputStreamReader(url_name.openStream()));
		 
		            // reads system IPAddress
		            systemipaddress = sc.readLine().trim();
		        }
		        catch (Exception e)
		        {
		            systemipaddress = "Cannot Execute Properly";
		        }
		        System.out.println("Public IP Address: " + systemipaddress +"\n");
				
				
				
				// ****************************************************
				System.out.println("");
				// ****************************************************
				
				byte[] ipAddr = new byte[] { 127, 0, 0, 1 };
			    InetAddress addr = InetAddress.getByAddress(ipAddr);
			    System.out.print("test: ");
			    System.out.println(Arrays.toString(addr.getAddress()));
				// ****************************************************
		
	}
	
}