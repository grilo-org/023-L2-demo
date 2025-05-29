package l2code.demo;

import java.util.List;

public record Caixa (
    String caixa_id,
    List<String> produtos,
    String observacao
) {

}