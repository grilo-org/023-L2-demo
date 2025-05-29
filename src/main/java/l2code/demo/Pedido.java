package l2code.demo;

import java.util.List;

public record Pedido (
	int pedido_id,
	List<Produto> produtos
){

}