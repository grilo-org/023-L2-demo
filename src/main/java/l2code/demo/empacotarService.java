package l2code.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Collections;
import java.util.stream.Collectors;


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

	public List<Pacote> processEntrada(MultipartFile file){
		JsonService json = new JsonService();
		List<Pacote> pacotes = new ArrayList<>();
		HashMap<Integer, String> nomeCaixas = new HashMap<>();
		nomeCaixas.put(1, "Caixa 1");
		nomeCaixas.put(2, "Caixa 2");
		nomeCaixas.put(3, "Caixa 3");

		File doc = null;
		try{
			doc = convertMultiPartToFile(file);
			ListaPedidos obj = json.getListaPedidos(doc);

			for(Pedido pedido : obj.pedidos()){
				HashMap<Integer, List<String>> pacote = getCaixasProdutos(pedido.produtos());

				List<Caixa> caixas = new ArrayList<>();
				for (var entry : pacote.entrySet()) {
					if(!entry.getValue().isEmpty()){
						int numCaixa = entry.getKey();
						List<String> produtos = entry.getValue();
						caixas.add(new Caixa(nomeCaixas.get(numCaixa), produtos));
					}
				}
				pacotes.add(new Pacote(caixas, pedido.pedido_id()));
			}
		}catch(IOException e){}

		return pacotes;
	}

	private File convertMultiPartToFile(MultipartFile file ) throws IOException {
		File convFile = new File( file.getOriginalFilename() );
		FileOutputStream fos = new FileOutputStream( convFile );
		fos.write( file.getBytes() );
		fos.close();
		return convFile;
	}

}