package l2code.demo;

// import com.example.demo.model.Book;
// import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Collections;
import java.util.stream.Collectors;

// @RestController
// @RequestMapping("/api")
public class empacotarService {

	/*
	Caixa 1: 30 x 40 x 80
	Caixa 2: 80 x 50 x 40
	Caixa 3: 50 x 80 x 60
	*/
	private List<List<Integer>> caixas = new ArrayList<>();

	public empacotarService(){
		setCaixas();
	}

	public void setCaixa(int altura, int largura, int comprimento){
		caixas.add(Arrays.asList(new Integer[]{altura, largura, comprimento}));
	}

	/**\/ ; */
	public void setCaixas(){
		if(caixas.size() < 3){
			setCaixa(30, 40, 80);
			setCaixa(80, 50, 40);
			setCaixa(50, 80, 60);
		}
	}

	/**\/ fins de testes; */
	public void testeAddProdutos(List<Produto> produtos){
		produtos.add(new Produto("Webcam", new Dimensoes(7, 10, 5)));
		produtos.add(new Produto("Microfone", new Dimensoes(25, 10, 10)));
		produtos.add(new Produto("Monitor", new Dimensoes(50, 60, 20)));
		produtos.add(new Produto("Notebook", new Dimensoes(2, 35, 25)));
	}

	private void sortMins(List<Produto> produtos){
		produtos.sort(java.util.Comparator.comparing(x -> x.dimensoes().altura()));
		produtos.sort(java.util.Comparator.comparing(x -> x.dimensoes().largura()));
		produtos.sort(java.util.Comparator.comparing(x -> x.dimensoes().comprimento()));
	}

	public HashMap<Integer, List<String>> getCaixasProdutos(List<Produto> produtos){

		HashMap<Integer, List<String>> res = new HashMap<>();
		sortMins(produtos);
		for(int i = 0; i<caixas.size(); i++){
			List<Integer> caixa = caixas.get(i);
			int altura = caixa.get(0);
			int largura = caixa.get(1);
			int comprimento = caixa.get(2);

			int s_altura = 0;
			int s_largura = 0;
			int s_comprimento = 0;

			for(Produto produto: produtos){
				if(
					produto.dimensoes().altura() <= altura && s_altura <= altura &&
					produto.dimensoes().largura() <= largura && s_largura <= largura &&
					produto.dimensoes().comprimento() <= comprimento && s_comprimento <= comprimento
				){
					s_altura += produto.dimensoes().altura();
					s_largura += produto.dimensoes().largura();
					s_comprimento += produto.dimensoes().comprimento();

					List<String> innerlist = res.get(i+1);
					if(innerlist == null) innerlist = new ArrayList<>();
					innerlist.add(produto.produto_id());
					res.put((i+1), innerlist );
				}
			}
		}

		List<Integer> caixasNum = new ArrayList<>(res.keySet());
		Collections.sort(caixasNum, Collections.reverseOrder());

		/** \/ obter diferenças das caixas preenchidas; */
		if(caixasNum.size() > 0){
			int ind_max = 0;
			int s_con = res.get(caixasNum.get(0)).size();
			for(int i = 0; i<caixasNum.size(); i++) {
				var caixa_ant = (i > 0) ? (caixasNum.get(i-1)) : (0);
				var caixa = caixasNum.get(i);

				if(caixa_ant > 0){
					List<String> differences = res.get(caixa).stream()
					.filter(element -> !res.get(caixa_ant).contains(element))
					.collect(Collectors.toList());
					
					res.put(caixa, differences);
					s_con += differences.size();
				}
				if(ind_max == 0 && s_con == produtos.size()){
					ind_max = i;
				}
			}
			for(int i = ind_max+1; i<caixasNum.size(); i++) {
				res.remove(caixasNum.get(i));
			}
		}
		// showMap(res);
		return res;
	}

	public void showMap(HashMap<Integer, List<String>> map){
		for (var entry : map.entrySet()) {
    		System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

}