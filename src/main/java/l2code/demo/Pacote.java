package l2code.demo;

import java.util.List;

public record Pacote (
    List<Caixa> caixas,
    int pedido_id
) {

}