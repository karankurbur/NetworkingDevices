
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

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

	/**
	 * Driver method.
	 * 
	 * @param args
	 *            is command line args.
	 */
	public static void main(String[] args) throws Exception {

		// host machine's IPv4 and IPv6 addresses and subnet information
		String[] part1Data = part1();
		// check host machine's IPv4 and IPv6 addresses and subnet information
		for (int i = 0; i < part1Data.length; i++) {
			System.out.println(part1Data[i]);
		}

	}

	/**
	 * Method determines the host machine's IPv4 and IPv6 addresses and subnet
	 * information.
	 * 
	 * @return an array with the host machine's IPv4 and IPv6 addresses and
	 *         subnet information.
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
	 * @param addresses
	 *            is all host addresses.
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
	 * @param localHost
	 *            is the local host.
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
}