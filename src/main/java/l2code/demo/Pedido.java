package l2code.demo;

// import java.util.ArrayList;
import java.util.List;

public class Pedido {

	private int pedidoId;
	private List<Produto> produtos;

    /* *** */

	public int getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(int pedidoId) {
		this.pedidoId = pedidoId;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
}