package l2code.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class pacoteController {

		private empacotarService empacotar = new empacotarService();
	//  @Autowired
	    // private BookService bookService;
		// private empacotarService empacotar;

		/** http://localhost:8080/api/ */
	    @GetMapping("/")
	    public String home() {
	        return "Welcome to the API!";
	    }

		@GetMapping("/empacotar")
	    public String empacotar() {

			empacotarService empacotar = new empacotarService();

			List<Produto> produtos = new ArrayList<>();
			produtos.add(new Produto("Webcam", new Dimensoes(7, 10, 5)));
			produtos.add(new Produto("Microfone", new Dimensoes(25, 10, 10)));
			produtos.add(new Produto("Monitor", new Dimensoes(50, 60, 20)));
			produtos.add(new Produto("Notebook", new Dimensoes(2, 35, 25)));

			// produtos.add(new Produto("PS5", new Dimensoes(40, 10, 25)));
			// produtos.add(new Produto("Volante", new Dimensoes(40, 30, 30)));

			// produtos.add(new Produto("Joystick", new Dimensoes(15, 20, 10)));
			// produtos.add(new Produto("Fifa 24", new Dimensoes(10, 30, 10)));
			// produtos.add(new Produto("Call of Duty", new Dimensoes(30, 15, 10)));

			empacotar.getCaixasProdutos(produtos);
	        return "Empacotar!";
	    }

	    @GetMapping("/pedidos")
	    public String findBookById() {
			JsonService json = new JsonService();
	        ListaPedidos obj = json.getListaPedidos("entrada.json");

			// empacotarService empacotar = new empacotarService();
			// empacotar.getCaixasProdutos(obj.getPedidos());

			return "Pedidos;";
	    }

		@PostMapping(path = "/pedidos", consumes = { "multipart/form-data" })
		@ResponseBody
		public List<Pacote> processPedidos(@RequestPart MultipartFile file) {
			List<Pacote> pacotes = empacotar.processEntrada(file);
			return pacotes;
		}

}