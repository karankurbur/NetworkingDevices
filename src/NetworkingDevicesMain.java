
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

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
 * An application that queries the operating system and runs several diagnostic
 * tools on the network.
 *
 * @author Raisa Meneses
 * @author Denis Yakovlev
 * @author Karan Kurbur
 * @author Richard Brun
 * @version 30 June 2017
 */
public class NetworkingDevicesMain {

	static String FQDN = "";
	static String ip = "";

	/**
	 * Driver method.
	 * 
	 * @param args is command line args.
	 */
	public static void main(String[] args) throws Exception {

		part2();

		// queryIP("www.ipv6tf.org");
		// // host machine's IPv4 and IPv6 addresses and subnet information
//		String[] part1Data = part1();
//		// check host machine's IPv4 and IPv6 addresses and subnet information
//		for (int i = 0; i < part1Data.length; i++) {
//			System.out.println(part1Data[i]);
//		}
	}

	public static void part2() {
		System.out.println("Enter 1 for FQDN, 2 for IPv4, 3 for IPv6");
		Scanner scanner = new Scanner(System.in);
		int i = Integer.parseInt(scanner.nextLine());
		if (i == 1) {
			System.out.println("Enter FQDN:");
			String FQDN = scanner.nextLine();
			queryIP(FQDN);
		} else if (i == 2) {
			System.out.println("Enter IPv4:");
			String ip = scanner.nextLine();
			findFQDNIPv4(ip);
		} else if (i == 3) {
			System.out.println("Enter IPv6:");
			String ip = scanner.nextLine();
			findFQDNIPv6(ip);
		}
	}

	public static void queryIP(String FQDN) {
		try {
			InetAddress address = InetAddress.getByName(FQDN);
			String output = address.toString();
			String parse[] = output.split("/");
			System.out.println("Your IP is " + parse[1]);
			ip = parse[1];
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public static void findFQDNIPv4(String ip) {
		InetAddress ia;
		try {
			ia = InetAddress.getByName(ip);
			System.out.println("FQDN for this IP is : " + ia.getCanonicalHostName());
			FQDN = ia.getCanonicalHostName().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Doesnt work
	public static void findFQDNIPv6(String ip) {
//		InetAddress ia;
//		try {
//			ia = InetAddress.getByName(ip);
//			System.out.println("FQDN for this IP is : " + ia.getCanonicalHostName());
//			System.out.println("FQDN for this IP is : " + ia.getHostName());
//
//			FQDN = ia.getCanonicalHostName().toString();
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * Method determines the host machine's IPv4 and IPv6 addresses and subnet
	 * information.
	 * 
	 * @return an array with the host machine's IPv4 and IPv6 addresses and subnet
	 *         information.
	 * @throws UnknownHostException
	 * @throws SocketException
	 */
	public static String[] part1() throws UnknownHostException, SocketException {

		// resulting array with the host machine's IPv4 and IPv6 addresses and
		// subnet information.
		String arrayIPv4_Ipv6_SubnetMask[] = new String[3];
		String iPv4 = "";
		String iPv6 = "";
		String subnetMaskInString = "";

		// determines local host and its name
		InetAddress localHost = InetAddress.getLocalHost();
		String localHostName = localHost.getHostName();

		// determines IPv4 address
		iPv4 = localHost.getHostAddress();

		// determines all addresses by local host name
		InetAddress[] addressesArray = InetAddress.getAllByName(localHostName);
		// determines IPv6 address
		Inet6Address iPv6Address = getIPv6Addresses(addressesArray);
		iPv6 = iPv6Address.getHostAddress();

		// determines the subnet mask
		short subnetMask = getSubnetMask(localHost);
		subnetMaskInString = "" + subnetMask;

		// filled out the resulting array
		if (iPv4 != null) {
			arrayIPv4_Ipv6_SubnetMask[0] = iPv4;
		} else {
			arrayIPv4_Ipv6_SubnetMask[0] = "cannot define IPv4 address";
		}

		if (iPv6 != null) {
			arrayIPv4_Ipv6_SubnetMask[1] = iPv6;
		} else {
			arrayIPv4_Ipv6_SubnetMask[1] = "cannot define IPv6 address";
		}

		arrayIPv4_Ipv6_SubnetMask[2] = subnetMaskInString;

		return arrayIPv4_Ipv6_SubnetMask;
	}

	/**
	 * Helper method to determine IPv6 address.
	 * 
	 * @param addresses is all host addresses.
	 * @return - the IPv6 address.
	 */
	public static Inet6Address getIPv6Addresses(InetAddress[] addresses) {

		for (InetAddress address : addresses) {
			if (address instanceof Inet6Address) {
				return (Inet6Address) address;
			}
		}

		return null;
	}

	/**
	 * Helper method to determines subnet mask.
	 * 
	 * @param localHost is the local host.
	 * @return a number of the highest ones in subnet mask.
	 * @throws SocketException
	 */
	public static short getSubnetMask(InetAddress localHost) throws SocketException {

		NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
		for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
			if (address instanceof InterfaceAddress) {
				return address.getNetworkPrefixLength();
			}
		}

		return 0;
	}

	/**
	 * 
	 * @param String containing ipAddress
	 * @throws IOException Broadcasts host's accessibility
	 */
	public static void ping(String ipString) throws IOException {
		try {

			InetAddress ip = InetAddress.getByName(ipString);
			if (ip.isReachable(6000)) {
				System.out.println(ipString + " was successfully pinged");
			} else {
				System.err.println("Request timeout");
			}
		} catch (UnknownHostException e) {
			System.err.println("Unknown host. Please check if the IP address is correct. ");
		}

	}

	// based on
	// https://plateofcode.blogspot.com/2016/04/how-trace-root-server-ip-in-java.html
	public static String traceRoute(String a) throws UnknownHostException {
		InetAddress address = InetAddress.getByName(a);
		final String os = System.getProperty("os.name").toLowerCase();
		String route = "";
		try {
			Process traceRt;
			if (os.contains("win"))
				traceRt = Runtime.getRuntime().exec("tracert " + address.getHostAddress());
			else
				traceRt = Runtime.getRuntime().exec("traceroute " + address.getHostAddress());

			route = convertStreamToString(traceRt.getInputStream());

			// String errors = convertStreamToString(traceRt.getErrorStream());

		} catch (IOException e) {

		}

		return route;
	}

	private static String convertStreamToString(InputStream inputStream) {
		BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
		String line = "";
		try {
			while ((line = bf.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}