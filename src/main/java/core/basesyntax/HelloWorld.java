package core.basesyntax;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.calculationservice.FruitService;
import core.basesyntax.service.calculationservice.FruitsServiceImpl;
import core.basesyntax.service.handler.BalanceOperation;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.PurchaseOperation;
import core.basesyntax.service.handler.ReturnOperation;
import core.basesyntax.service.handler.SupplyOperation;
import core.basesyntax.service.handlerservice.HandlerServiceImpl;
import core.basesyntax.service.nio.FileService;
import core.basesyntax.service.nio.FileServiceImpl;
import core.basesyntax.service.parser.TransactionParserImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorld {
    public static final String IN_FILE = "src/main/resources/fruit.csv";
    public static final String OUT_FILE = "src/main/resources/resultFruits.csv";

    public static void main(String[] args) {

        FileService fileService = new FileServiceImpl();
        List<String> stringList = fileService.read(IN_FILE);

        TransactionParserImpl transactionParser = new TransactionParserImpl();
        FruitsDao fruitsDao = new FruitsDaoImpl();

        List<FruitTransaction> banana1 = transactionParser.parse(stringList, "banana");
        List<FruitTransaction> apple1 = transactionParser.parse(stringList, "apple");

        banana1.forEach(banana -> fruitsDao.addFruitsStorage(banana));
        apple1.forEach(apple -> fruitsDao.addFruitsStorage(apple));

        Map<String, OperationHandler> handler = new HashMap<>();
        handler.put("b", new BalanceOperation());
        handler.put("s", new SupplyOperation());
        handler.put("r", new ReturnOperation());
        handler.put("p", new PurchaseOperation());
        FruitService serviceFruits = new FruitsServiceImpl(new FruitsDaoImpl(),
                new HandlerServiceImpl(handler));

        String banana = serviceFruits.calculationFruits("banana");
        String apple = serviceFruits.calculationFruits("apple");

        fileService.write(banana, OUT_FILE);
        fileService.write(apple, OUT_FILE);

    }
}
