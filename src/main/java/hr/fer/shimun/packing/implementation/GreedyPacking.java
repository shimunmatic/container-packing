package hr.fer.shimun.packing.implementation;

import hr.fer.shimun.packing.ContainerPackingAlgorithm;
import hr.fer.shimun.packing.implementation.model.ContainerHolder;
import hr.fer.shimun.packing.model.*;
import hr.fer.shimun.packing.util.Vector;

import java.util.List;

public class GreedyPacking implements ContainerPackingAlgorithm {
    @Override
    public PackingResult pack(Container container, List<Packet> packetList) {

        ContainerHolder containerHolder =
                new ContainerHolder(container.getHeight(), container.getWidth(), container.getLength());

        for (Packet packet : packetList) {
            Vector<Integer, Integer, Integer> vector =
                    new Vector<>(packet.getWidth(), packet.getLength(), packet.getHeight());

            for (int i = 0; i < packet.getPacketCount(); i++) {
                List<Point> points = containerHolder.getAvailableStartPositions(vector);

                if (points.isEmpty()) {
                    System.out.println("Error, no space ");
                    return null;
                }
                containerHolder.addPacketToPoint(packet, points.get(0));
            }
        }
        new ConsolePrinter().print(containerHolder);
        return new PackingResult(container, null);
    }
}
