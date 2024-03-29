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
import java.util.stream.IntStream;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContainerHolder extends ThreeDObject {
    private Set<Point> freePoints;
    private Map<Point, Packet> packedPackets;
    private List<Packet> unPackedPackets;
    private List<Packet> packetCategories;
    private int packetsVolume = 0;
    private int volumeOfEmptyPackets = 0;
    private int[][] matrix;
    private int count = 0;
    private int minimumEdge = 0;

    @Builder
    public ContainerHolder(int height, int width, int length) {
        super(height, width, length);
        freePoints = new HashSet<>();
        freePoints.add(new Point(height, width, length, 0, 0, 0));

        packetCategories = new ArrayList<>();
        unPackedPackets = new ArrayList<>();
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
        if (point.getPositionZ() < height) {
            return false;
        }
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
        count++;
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
        System.out.println("Inserted category: " + packet.getBoxTypeId());
    }

    private void fillMatrix(Point point, Packet packet) {
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

    public boolean scanForNewFreePoints() {
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (i == 0) {
                    if (j == 0 || matrix[i][j] != matrix[i][j - 1]) {
                        points.add(Point.builder().positionX(i).positionY(j).positionZ(matrix[i][j]).build());
                    }
                } else {
                    if ((j == 0 && matrix[i - 1][j] != matrix[i][j]) ||
                        (j > 0 && matrix[i][j] != matrix[i][j - 1] && matrix[i - 1][j] != matrix[i][j])) {
                        points.add(Point.builder().positionX(i).positionY(j).positionZ(matrix[i][j]).build());
                    }
                }
            }
        }

        freePoints.clear();
        freePoints.addAll(points);
        return !points.isEmpty();
    }

    /**
     * Insert empty blocks to lowest height possible in attempt to create bigger flat surfaces
     *
     * @return {@code true} if empty blocks were inserted, {@code false} otherwise
     */
    public boolean insertEmptyBlocks() {
        // calculating unique heights
        Set<Integer> heights = getUniqueHeights();
        System.out.println("Height in matrix: " + heights);

        if (heights.size() <= 1) { return false; }

        int minHeight = heights.stream().flatMapToInt(IntStream::of).min().orElse(-1);
        int nextHeight = heights.stream().filter(h -> h != minHeight).flatMapToInt(IntStream::of).min().orElse(-1);

        if (minHeight == -1 || nextHeight == -1) {
            return false;
        }

        if (nextHeight + minimumEdge > height) { return false; }
        int diff = nextHeight - minHeight;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (matrix[i][j] == minHeight) {
                    matrix[i][j] = nextHeight;
                    volumeOfEmptyPackets += diff;
                }
            }
        }
        System.out.println(String.format("Increased height of level %d to level %d.", minHeight, nextHeight));
        scanForNewFreePoints();
        return true;
    }

    public Set<Integer> getUniqueHeights() {
        Set<Integer> heights = new HashSet<>();
        Arrays.stream(matrix).parallel()
              .forEach(i -> heights.addAll(Arrays.stream(i).boxed().collect(Collectors.toSet())));
        return heights;
    }

    public void addPacketCategory(Packet category) {
        packetCategories.add(category);
        int minEdge =
                IntStream.builder().add(category.getLength()).add(category.getHeight()).add(category.getWidth()).build()
                         .min().orElse(-1);
        if (minimumEdge == 0 || (minEdge != -1 && minEdge < minimumEdge)) {
            minimumEdge = minEdge;
        }
    }
}
