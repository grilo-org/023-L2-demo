package l2code.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;

import java.io.File;
import org.springframework.web.multipart.MultipartFile;

public class JsonService {

	public ListaPedidos getListaPedidos(String jsonFile) {
		Gson gson = new Gson();
		try {
			BufferedReader br = new BufferedReader(new FileReader(jsonFile));
			ListaPedidos obj = gson.fromJson(br, ListaPedidos.class);
            return obj;

		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}

    public ListaPedidos getListaPedidos(File jsonFile) {
		Gson gson = new Gson();
		try {

			BufferedReader br = new BufferedReader(new FileReader(jsonFile));
			ListaPedidos obj = gson.fromJson(br, ListaPedidos.class);
            return obj;

		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}

}