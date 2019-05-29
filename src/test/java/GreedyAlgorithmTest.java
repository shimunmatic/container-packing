import hr.fer.shimun.packing.ContainerPackingAlgorithm;
import hr.fer.shimun.packing.implementation.GreedyPacking;
import hr.fer.shimun.packing.io.IOUtil;
import hr.fer.shimun.packing.io.InputBasket;
import hr.fer.shimun.packing.model.ConsolePrinter;
import hr.fer.shimun.packing.model.PackingResult;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GreedyAlgorithmTest {

    @Test
    public void testGettingEmptySpace() throws IOException {
        File f = IOUtilTest.getTestFile("1");
        List<InputBasket> ib = IOUtil.loadInputData(f);

        ContainerPackingAlgorithm packingAlgorithm = new GreedyPacking();

        PackingResult pr = packingAlgorithm.pack(ib.get(0).getContainer(), ib.get(0).getPacketList());
        new ConsolePrinter().print(pr.getContainerHolder());
    }
}
