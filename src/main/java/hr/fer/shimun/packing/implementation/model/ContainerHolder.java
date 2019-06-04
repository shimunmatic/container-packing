package hr.fer.shimun.packing.implementation.model;

import hr.fer.shimun.packing.model.Packet;
import hr.fer.shimun.packing.model.Point;
import hr.fer.shimun.packing.model.ThreeDObject;
import hr.fer.shimun.packing.util.Vector;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContainerHolder extends ThreeDObject {
    private Set<Point> freePoints;
    private Map<Point, Packet> packedPackets;
    private List<Packet> unPackedPackets;
    private int[][] matrix;
    public int count = 0;

    @Builder
    public ContainerHolder(int height, int width, int length) {
        super(height, width, length);
        freePoints = new HashSet<>();
        freePoints.add(new Point(height, width, length, 0, 0, 0));

        packedPackets = new HashMap<>();
        matrix = new int[width][length];
    }

    public List<Point> getAvailableStartPositions(Vector<Integer, Integer, Integer> dimensions) {
        // return all freePoints that can be starting freePoints for
        return freePoints.stream().filter(point -> (point.getPositionX() + dimensions.getX() < getWidth() &&
                                                    point.getPositionY() + dimensions.getY() < getLength() &&
                                                    point.getPositionZ() + dimensions.getZ() < getHeight()) &&
                                                   !packedPackets.containsKey(point) && canBePacked(point, dimensions))
                         .sorted(Comparator.comparingInt(Point::getPositionZ)).collect(Collectors.toList());
    }

    private boolean canBePacked(Point point, Vector<Integer, Integer, Integer> dimensions) {
        int height = matrix[point.getPositionX()][point.getPositionY()];
        if (point.getPositionZ() < height) { return false; }
        for (int i = 0; i < dimensions.getX(); i++) {
            for (int j = 0; j < dimensions.getY(); j++) {
                if (matrix[point.getPositionX() + i][point.getPositionY() + j] != height ||
                    point.getPositionZ() + dimensions.getZ() > getHeight()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addPacketToPoint(Packet packet, Point point) {
        packedPackets.put(point, packet);
        fillMatrix(point, packet);
        freePoints.remove(point);
        if (point.getPositionX() + packet.getWidth() < width) {
            freePoints.add(Point.builder().positionX(point.getPositionX() + packet.getWidth())
                                .positionY(point.getPositionY()).positionZ(point.getPositionZ())
                                .height(packet.getHeight()).width(packet.getWidth()).length(packet.getLength())
                                .build());
        }
        if (point.getPositionY() + packet.getLength() < length) {

            freePoints.add(Point.builder().positionX(point.getPositionX())
                                .positionY(point.getPositionY() + packet.getLength()).positionZ(point.getPositionZ())
                                .height(packet.getHeight()).width(packet.getWidth()).length(packet.getLength())
                                .build());
        }
        if (point.getPositionZ() + packet.getHeight() < height) {

            freePoints.add(Point.builder().positionX(point.getPositionX()).positionY(point.getPositionY())
                                .positionZ(point.getPositionZ() + packet.getHeight()).height(packet.getHeight())
                                .width(packet.getWidth()).length(packet.getLength()).build());
        }
    }

    private void fillMatrix(Point point, Packet packet) {
        System.out.println(++count);
        for (int i = 0; i < packet.getWidth(); i++) {
            for (int j = 0; j < packet.getLength(); j++) {
                int newVal = matrix[point.getPositionX() + i][point.getPositionY() + j] + packet.getHeight();
                if (newVal > getHeight()) {
                    System.out.println("error");
                }
                matrix[point.getPositionX() + i][point.getPositionY() + j] += packet.getHeight();
            }
        }
        // remove points that were covered just now
        freePoints.removeIf(p -> (p.getPositionX() >= point.getPositionX() &&
                                  p.getPositionX() < point.getPositionX() + packet.getWidth()) &&
                                 (p.getPositionY() >= point.getPositionY() &&
                                  p.getPositionY() < point.getPositionY() + packet.getLength()));
    }

    public void scanForNewFreePoints() {


    }
}
