package core.basesyntax.service.parser;

import core.basesyntax.entity.FruitTransaction;
import java.util.List;

public interface TransactionParser {

    List<FruitTransaction> parser(List<String> str,
                                  String fruits,
                                  boolean putDataBase);
}
