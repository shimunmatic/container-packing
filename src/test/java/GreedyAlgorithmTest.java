import hr.fer.shimun.packing.ContainerPackingAlgorithm;
import hr.fer.shimun.packing.ContainerPackingSpringBootApplication;
import hr.fer.shimun.packing.io.IOUtil;
import hr.fer.shimun.packing.io.InputBasket;
import hr.fer.shimun.packing.model.Container;
import hr.fer.shimun.packing.model.Packet;
import hr.fer.shimun.packing.model.PackingResult;
import hr.fer.shimun.packing.printer.ConsolePrinter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
@ContextConfiguration(classes = ContainerPackingSpringBootApplication.class)
public class GreedyAlgorithmTest {
    @Autowired
    private ContainerPackingAlgorithm packingAlgorithm;

    @Test
    public void testGettingEmptySpace() throws IOException {
        File f = IOUtilTest.getTestFile("1");
        List<InputBasket> ib = IOUtil.loadInputData(f);
        Container container = ib.get(0).getContainer();
        List<Packet> packets = ib.get(0).getPacketList();
        PackingResult pr = packingAlgorithm.pack(container, packets);
        System.out.println(new ConsolePrinter().print(pr.getContainerHolder()));
    }
}
