package l2code.demo;

// import com.example.demo.model.Book;
// import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
			empacotar.getCaixasProdutos();
	        return "Empacotar!";
	    }

	    // @GetMapping("/findbyid/{id}")
	    // public Book findBookById(@PathVariable int id) {
	    //     // return bookService.findBookById(id);
	    // }

	    // @GetMapping("/findall")
	    // public List<Book> findAllBooks() {
	    //     // return bookService.findAllBooks();
	    // }

	    // @DeleteMapping("/delete")
	    // public String deleteAllBooks() {
	    //     // bookService.deleteAllBooks();
	    //     return "All books have been deleted.";
	    // }

}