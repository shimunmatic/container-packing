package hr.fer.shimun.packing.implementation;

import hr.fer.shimun.packing.ContainerPackingAlgorithm;
import hr.fer.shimun.packing.implementation.model.ContainerHolder;
import hr.fer.shimun.packing.model.Container;
import hr.fer.shimun.packing.model.Packet;
import hr.fer.shimun.packing.model.PackingResult;
import hr.fer.shimun.packing.model.Point;
import hr.fer.shimun.packing.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GreedyPacking implements ContainerPackingAlgorithm {

    private static final int NUMBER_OF_TRIES = 1000;

    @Override
    public PackingResult pack(Container container, final List<Packet> entryPacketList) {
        int count = 0;
        ContainerHolder maxResult = null;
        ContainerHolder containerHolder = null;
        while (count < NUMBER_OF_TRIES) {
            count++;
            if (containerHolder != null && containerHolder.count > maxResult.count) {
                maxResult = containerHolder;
            }
            containerHolder = new ContainerHolder(container.getHeight(), container.getWidth(), container.getLength());
            List<Packet> packetList = getPacketList(entryPacketList);
            if (maxResult == null) { maxResult = containerHolder; }
            try {
                List<Packet> unpacked = new ArrayList<>();
                int unpackedIndex;
                // pack each item
                for (int i = 0; i < packetList.size(); i++) {
                    Packet packet = packetList.get(i);
                    boolean secondTry = false;
                    // try 6 possible orientations
                    for (int j = 0; j < 6; j++) {
                        Vector<Integer, Integer, Integer> vector = getVectorFromPacketAndOrientation(packet, j);
                        List<Point> points = containerHolder.getAvailableStartPositions(vector);
                        if (points.isEmpty()) {
                            System.out.println("Changing orientation");
                            if (j == 5) {
//                                if (secondTry) {
//                                    if (containerHolder.insertEmptyBlocks()) {
//                                        j = -1;
//                                        continue;
//                                    }
//                                }
                                if (!secondTry && containerHolder.scanForNewFreePoints()) {
                                    j = -1;
                                    secondTry = true;
                                    continue;
                                }
                                unpacked.add(packet);
                                unpackedIndex = i + 1;
                                for (int k = unpackedIndex; k < packetList.size(); k++) {
                                    unpacked.add(packetList.get(k));
                                }
                                containerHolder.setUnPackedPackets(unpacked);
                                throw new Exception("");
                            }
                        } else {
                            packet.setDimensionsFromVector(vector);
                            Collections.shuffle(points);
                            containerHolder.addPacketToPoint(packet, points.get(0));
                            break;
                        }
                    }
                }
                return new PackingResult(containerHolder, null);
            } catch (Exception e) {
            }
        }
        return new PackingResult(maxResult, null);
    }

    private List<Packet> getPacketList(List<Packet> packetList) {
        List<Packet> pList = new ArrayList<>();
        packetList.sort(Comparator.comparingInt(value -> value.getHeight() * value.getWidth() * value.getLength()));
        Collections.reverse(packetList);
        for (Packet packet : packetList) {
            for (int i = 0; i < packet.getPacketCount(); i++) {
                pList.add(new Packet(packet.getHeight(), packet.getWidth(), packet.getLength(), packet.getBoxTypeId(),
                        packet.getPacketCount()));
            }
        }
        return pList;
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
