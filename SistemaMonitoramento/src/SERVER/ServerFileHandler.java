/*
 * AUTOR CLEITON ALVES E SILVA JÚNIOR
 */
package SERVER;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerFileHandler {

	private static final String FILE_PATH = "servers.txt";

	public static void saveServers(List<Server> servers) {
		String absoluteFilePath = new File(FILE_PATH).getAbsolutePath();
		System.out.println("Caminho absoluto do arquivo: " + absoluteFilePath);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
			for (Server server : servers) {
				writer.write(server.getHostname() + "," + server.getIp());
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Server> loadServers() {
		String absoluteFilePath = new File(FILE_PATH).getAbsolutePath();
		System.out.println("Caminho absoluto do arquivo: " + absoluteFilePath);

		List<Server> servers = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length == 2) {
					servers.add(new Server(parts[0], parts[1]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return servers;
	}
}
