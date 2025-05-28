package l2code.demo;

// import com.example.demo.model.Book;
// import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.io.FileOutputStream;
import java.io.File;
import java.lang.IllegalStateException;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class pacoteController {
	 @Autowired
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
			// empacotar.getCaixasProdutos();
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

		@PostMapping(path = "/pedidos", consumes = { MediaType.APPLICATION_JSON_VALUE })
		@ResponseBody
		public String processPedidos(@RequestParam(required=false) String file, @RequestPart MultipartFile document) {
			// employeeService.save(employee);

			JsonService json = new JsonService();

			File doc = null;
			try{
				doc = convertMultiPartToFile(document);
				ListaPedidos obj = json.getListaPedidos(doc);
				System.out.println(obj.getPedidos().size());
			}catch(IOException e){}

			return "employee/success";
		}

	    // @GetMapping("/findall")
	    // public List<Book> findAllBooks() {
	    //     // return bookService.findAllBooks();
	    // }

	    // @DeleteMapping("/delete")
	    // public String deleteAllBooks() {
	    //     // bookService.deleteAllBooks();
	    //     return "All books have been deleted.";
	    // }

	private File convertMultiPartToFile(MultipartFile file ) throws IOException {
		File convFile = new File( file.getOriginalFilename() );
		FileOutputStream fos = new FileOutputStream( convFile );
		fos.write( file.getBytes() );
		fos.close();
		return convFile;
	}

}