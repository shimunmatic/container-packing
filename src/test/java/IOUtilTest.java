import hr.fer.shimun.packing.ContainerPackingAlgorithm;
import hr.fer.shimun.packing.implementation.GreedyPacking;
import hr.fer.shimun.packing.io.IOUtil;
import hr.fer.shimun.packing.io.InputBasket;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class IOUtilTest {

    @Test
    public void testLoadingData() throws IOException {
        File f = getTestFile("1");
        List<InputBasket> ib = IOUtil.loadInputData(f);

        Assert.assertNotNull(ib);
        Assert.assertEquals(100, ib.size());
    }

    @Test
    public void testGettingEmptySpace() throws IOException {
        File f = getTestFile("1");
        List<InputBasket> ib = IOUtil.loadInputData(f);

        ContainerPackingAlgorithm packingAlgorithm = new GreedyPacking();

        packingAlgorithm.pack(ib.get(0).getContainer(), ib.get(0).getPacketList());
    }

    private File getTestFile(String fileNumber) {
        return new File("src/test/resources/3D BPP/thpack" + fileNumber + ".txt");
    }
}
