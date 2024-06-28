/*
 * AUTOR CLEITON ALVES E SILVA JÚNIOR
 */
package SERVER;

public class Server {
	private String hostname;
	private String ip;

	public Server(String hostname, String ip) {
		this.hostname = hostname;
		this.ip = ip;
	}

	public String getHostname() {
		return hostname;
	}

	public String getIp() {
		return ip;
	}
}
