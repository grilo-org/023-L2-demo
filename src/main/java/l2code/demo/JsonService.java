package l2code.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// import com.br.entity.Informacoes;
import com.google.gson.Gson;

import java.io.File;
import org.springframework.web.multipart.MultipartFile;

public class JsonService {

	public ListaPedidos getListaPedidos(String jsonFile) {
		Gson gson = new Gson();
		try {

			BufferedReader br = new BufferedReader(new FileReader(jsonFile));

			//Converte String JSON para objeto Java
			ListaPedidos obj = gson.fromJson(br, ListaPedidos.class);

			// System.out.println(obj.pedidos().size());
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

			//Converte String JSON para objeto Java
			ListaPedidos obj = gson.fromJson(br, ListaPedidos.class);

			// System.out.println(obj.pedidos().size());
            return obj;

		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}

}