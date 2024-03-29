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

    public static File getTestFile(String fileNumber) {
        return new File("src/test/resources/3D BPP/thpack" + fileNumber + ".txt");
    }
}
