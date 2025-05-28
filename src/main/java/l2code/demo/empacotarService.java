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
	private List<Produto> produtos = new ArrayList<>();

	public void setCaixa(int altura, int largura, int comprimento){
		caixas.add(Arrays.asList(new Integer[]{altura, largura, comprimento}));
	}

	public void setCaixa(String altura, String largura, String comprimento){
		caixas.add(Arrays.asList(new Integer[]{Integer.parseInt(altura), Integer.parseInt(largura), Integer.parseInt(comprimento)}));
	}

	public void addProdutos(Produto produto){
		produtos.add(produto);
	}

	/**\/ fins de testes; */
	public void testeAddCaixas(){
		setCaixa(30, 40, 80);
		setCaixa(80, 50, 40);
		setCaixa(50, 80, 60);
	}

	/**\/ fins de testes; */
	public void testeAddProdutos(){
		produtos.add(new Produto("Webcam", 7, 10, 5));
		produtos.add(new Produto("Microfone", 25, 10, 10));
		produtos.add(new Produto("Monitor", 50, 60, 20));
		produtos.add(new Produto("Notebook", 2, 35, 25));
	}

	private void sortMins(List<Produto> produtos){
		produtos.sort(java.util.Comparator.comparing(x -> x.altura()));
		produtos.sort(java.util.Comparator.comparing(x -> x.largura()));
		produtos.sort(java.util.Comparator.comparing(x -> x.comprimento()));
	}

	public HashMap<Integer, List<String>> getCaixasProdutos(){

		testeAddCaixas();
		testeAddProdutos();

		HashMap<Integer, List<String>> res = new HashMap<>();
		sortMins(produtos);
		for(int i =0; i<caixas.size(); i++){
			List<Integer> caixa = caixas.get(i);
			int altura = caixa.get(0);
			int largura = caixa.get(1);
			int comprimento = caixa.get(2);

			int s_altura = 0;
			int s_largura = 0;
			int s_comprimento = 0;

			for(Produto produto: produtos){
				if(
					produto.altura() <= altura && s_altura <= altura &&
					produto.largura() <= largura && s_largura <= largura &&
					produto.comprimento() <= comprimento && s_comprimento <= comprimento
				){
					s_altura += produto.altura();
					s_largura += produto.largura();
					s_comprimento += produto.comprimento();

					List<String> innerlist = res.get(i+1);
					if(innerlist == null) innerlist = new ArrayList<>();
					innerlist.add(produto.nome());
					res.put((i+1), innerlist );
				}
			}
		}

		List<Integer> caixasNum = new ArrayList<>(res.keySet());
		Collections.sort(caixasNum, Collections.reverseOrder());

		/** \/ obter diferenças das caixas preenchidas; */
		int ind_max = 0;
		int s_con = res.get(caixasNum.get(0)).size();
		for(int i = 0; i<caixasNum.size(); i++) {
			var caixa = caixasNum.get(i);
			// System.out.println(caixa + " =>" + res.get(caixa));
			if((i-1) > 0){
				final int caixa_ant = caixasNum.get(i-1);
				List<String> differences = res.get(caixa_ant).stream()
				.filter(element -> !res.get(caixa).contains(element))
				.collect(Collectors.toList());
				
				res.put(caixa_ant, differences);
				s_con += differences.size();
			}
			if(ind_max == 0 && s_con == produtos.size()){
				ind_max = i;
			}
			if(ind_max > 0 && i >= ind_max){
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