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

    @Builder
    public ContainerHolder(int height, int width, int length) {
        super(height, width, length);
        freePoints = new HashSet<>();
        freePoints.add(new Point(height, width, length, 0, 0, 0));

        packedPackets = new HashMap<>();
    }

    public List<Point> getAvailableStartPositions(Vector<Integer, Integer, Integer> dimensions) {
        // return all freePoints that can be starting freePoints for
        return freePoints.parallelStream().filter(point -> (point.getPositionX() + dimensions.getX() < width &&
                                                            point.getPositionY() + dimensions.getY() < length &&
                                                            point.getPositionZ() + dimensions.getZ() < height) &&
                                                           !packedPackets.containsKey(point) &&
                                                           packedPackets.keySet().parallelStream().noneMatch(
                                                                   p -> ((p.getPositionX() >= point.getPositionX() &&
                                                                          p.getEndPositionX() <=
                                                                          point.getPositionX() + dimensions.getX()) ||
                                                                         (p.getPositionY() >= point.getPositionY() &&
                                                                          p.getEndPositionY() <=
                                                                          point.getPositionY() + dimensions.getY())) &&
                                                                        (p.getEndPositionZ() >
                                                                         point.getEndPositionZ())))
                         .sorted(Comparator.comparingInt(Point::getPositionZ)).collect(Collectors.toList());
    }

    public void addPacketToPoint(Packet packet, Point point) {
        packedPackets.put(point, packet);

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

}
