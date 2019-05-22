package hr.fer.shimun.packing.io;

import hr.fer.shimun.packing.model.Container;
import hr.fer.shimun.packing.model.Packet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {

    public static List<InputBasket> loadInputData(File f) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        String line;
        line = bufferedReader.readLine().trim();

        int numOfProblems = Integer.parseInt(line);
        List<InputBasket> baskets = new ArrayList<>(numOfProblems);

        for (int i = 0; i < numOfProblems; i++) {
            InputBasket.InputBasketBuilder ib = InputBasket.builder();
            // problem # and seed #
            line = bufferedReader.readLine().trim();
            ib.problemNumber(Integer.parseInt(line.split(" ")[0]));

            // container dimensions
            line = bufferedReader.readLine().trim();
            String[] dimensions = line.split(" ");
            ib.container(Container.builder().length(Integer.parseInt(dimensions[0].trim()))
                                  .width(Integer.parseInt(dimensions[1].trim()))
                                  .height(Integer.parseInt(dimensions[2].trim())).build());
            //number of box types
            int numOfBoxTypes = Integer.parseInt(bufferedReader.readLine().trim());

            ib.numberOfBoxTypes(numOfBoxTypes);
            List<Packet> packets = new ArrayList<>();
            bufferedReader.lines().limit(numOfBoxTypes).forEach(l -> {
                String[] packetDim = l.trim().split(" ");
                Packet p = Packet.builder().boxTypeId(Integer.parseInt(packetDim[0].trim()))
                                 .length(Integer.parseInt(packetDim[1].trim()))
                                 .width(Integer.parseInt(packetDim[3].trim()))
                                 .height(Integer.parseInt(packetDim[5].trim()))
                                 .packetCount(Integer.parseInt(packetDim[7].trim())).build();
                packets.add(p);
            });

            ib.packetList(packets);
            baskets.add(ib.build());
        }
        return baskets;
    }
}
