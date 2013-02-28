package javachallenge.server;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javachallenge.common.ClientMessage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PlayLog {
	ArrayList<ClientMessage[]> data = new ArrayList<ClientMessage[]>();

	public void add(ClientMessage[] msgs) {
		data.add(msgs);
	}
	
	public void save(String filename) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(this);
		
		OutputStreamWriter out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(filename));
			out.write(json);
		} finally {
			if (out != null)
				out.close();
		}
	}
	
	public static PlayLog load(String filename) throws IOException {
		BufferedReader f = null;
		StringBuilder sb = new StringBuilder();
		try {
			f = new BufferedReader(new FileReader(filename));
			String line = f.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = f.readLine();
			}
		} finally {
			if (f != null)
				f.close();
		}
		
		String json = sb.toString();
		Gson gson = new Gson();
		PlayLog obj = gson.fromJson(json, PlayLog.class);
		
		return obj;
	}
}
