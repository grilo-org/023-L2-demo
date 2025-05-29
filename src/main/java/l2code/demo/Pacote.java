package l2code.demo;

import java.util.List;

public record Pacote (
    String caixa_id,
    List<String> produtos,
    int pedidoId
) {

}