package hr.fer.shimun.packing.implementation;

import hr.fer.shimun.packing.ContainerPackingAlgorithm;
import hr.fer.shimun.packing.implementation.model.ContainerHolder;
import hr.fer.shimun.packing.model.Container;
import hr.fer.shimun.packing.model.Packet;
import hr.fer.shimun.packing.model.PackingResult;
import hr.fer.shimun.packing.model.Point;
import hr.fer.shimun.packing.util.Vector;

import java.util.List;

public class GreedyPacking implements ContainerPackingAlgorithm {
    @Override
    public PackingResult pack(Container container, List<Packet> packetList) {

        ContainerHolder containerHolder =
                new ContainerHolder(container.getHeight(), container.getWidth(), container.getLength());

        for (Packet packet : packetList) {
            for (int i = 0; i < packet.getPacketCount(); i++) {
                Vector<Integer, Integer, Integer> vector = getVectorFromPacketAndOrientation(packet, 0);
                for (int j = 1; j < 6; j++) {
                    List<Point> points = containerHolder.getAvailableStartPositions(vector);

                    if (points.isEmpty()) {
                        vector = getVectorFromPacketAndOrientation(packet, j);
                    } else {
                        packet.setDimensionsFromVector(vector);
                        containerHolder.addPacketToPoint(packet, points.get(0));
                        break;
                    }
                }

            }
        }
        return new PackingResult(containerHolder, null);
    }

    private Vector<Integer, Integer, Integer> getVectorFromPacketAndOrientation(Packet packet, int orientation) {
        switch (orientation) {
            case 0:
                return new Vector<>(packet.getWidth(), packet.getLength(), packet.getHeight());
            case 1:
                return new Vector<>(packet.getWidth(), packet.getHeight(), packet.getLength());
            case 2:
                return new Vector<>(packet.getHeight(), packet.getLength(), packet.getWidth());
            case 3:
                return new Vector<>(packet.getHeight(), packet.getWidth(), packet.getLength());
            case 4:
                return new Vector<>(packet.getLength(), packet.getWidth(), packet.getHeight());
            case 5:
                return new Vector<>(packet.getLength(), packet.getHeight(), packet.getWidth());
        }
        return new Vector<>(packet.getWidth(), packet.getLength(), packet.getHeight());
    }
}
