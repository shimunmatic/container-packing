package hr.fer.shimun.packing.implementation;

import hr.fer.shimun.packing.ContainerPackingAlgorithm;
import hr.fer.shimun.packing.function.HeuristicFunction;
import hr.fer.shimun.packing.implementation.model.ContainerHolder;
import hr.fer.shimun.packing.model.*;
import hr.fer.shimun.packing.util.Vector;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GreedyPacking implements ContainerPackingAlgorithm {
    private HeuristicFunction heuristicFunction;

    public GreedyPacking(HeuristicFunction heuristicFunction) {
        this.heuristicFunction = heuristicFunction;
    }

    @Override
    public PackingResult pack(Container container, final List<Packet> entryPacketList) {
        ContainerHolder containerHolder;
        containerHolder = new ContainerHolder(container.getHeight(), container.getWidth(), container.getLength());
        List<Packet> packetList = getPacketList(entryPacketList, containerHolder);
        List<Packet> unpacked = new ArrayList<>();
        List<Packet> currentList = new ArrayList<>(packetList);
        while (true) {
            // pack each item
            for (Packet packet : currentList) {
                // try 6 possible orientations
                List<Vector<Point, Double, Integer>> listOfPossiblePositions = new ArrayList<>();
                containerHolder.scanForNewFreePoints();
                for (int j = 0; j < 6; j++) {
                    Vector<Integer, Integer, Integer> vector = getVectorFromPacketAndOrientation(packet, j);
                    List<Point> points = containerHolder.getAvailableStartPositions(vector);
                    Map<Point, Double> gradedPoints = heuristicFunction.gradePoints(vector, points, containerHolder);
                    final int vectorId = j;
                    gradedPoints.forEach(
                            (point, grade) -> listOfPossiblePositions.add(new Vector<>(point, grade, vectorId)));
                }
                if (listOfPossiblePositions.isEmpty()) {
                    System.out.println("No place");
                    unpacked.add(packet);
                } else {
                    // sort by grades
                    listOfPossiblePositions.sort((o1, o2) -> {
                        int result = Double.compare(o2.getY(), o1.getY());
                        return result == 0 ? Integer.compare(o1.getZ(), o2.getZ()) : result;
                    });
                    Vector<Point, Double, Integer> bestOptionVector = listOfPossiblePositions.get(0);

                    packet.setDimensionsFromVector(getVectorFromPacketAndOrientation(packet, bestOptionVector.getZ()));
                    containerHolder.addPacketToPoint(packet, bestOptionVector.getX());
                }
            }
            if (unpacked.size() == 0) { break; }
            if (!(unpacked.size() != currentList.size() || containerHolder.insertEmptyBlocks())) { break; }
            currentList.clear();
            currentList.addAll(unpacked);
            unpacked.clear();
        }
        containerHolder.setUnPackedPackets(unpacked);
        return new PackingResult(containerHolder, unpacked);
    }

    private List<Packet> getPacketList(List<Packet> packetList, ContainerHolder containerHolder) {
        List<Packet> pList = new ArrayList<>();
        packetList.sort(Comparator.comparingInt(ThreeDObject::getVolume));
        Collections.reverse(packetList);
        int volume = 0;
        for (Packet packet : packetList) {
            volume += packet.getVolume() * packet.getPacketCount();
            containerHolder.addPacketCategory(packet);
            for (int i = 0; i < packet.getPacketCount(); i++) {
                pList.add(new Packet(packet.getHeight(), packet.getWidth(), packet.getLength(), packet.getBoxTypeId(),
                        packet.getPacketCount()));
            }
        }
        containerHolder.setPacketsVolume(volume);
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