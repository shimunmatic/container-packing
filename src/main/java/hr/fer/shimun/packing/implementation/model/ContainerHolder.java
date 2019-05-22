package hr.fer.shimun.packing.implementation.model;

import hr.fer.shimun.packing.model.Block;
import hr.fer.shimun.packing.model.Packet;
import hr.fer.shimun.packing.model.ThreeDObject;
import hr.fer.shimun.packing.util.Vector;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContainerHolder extends ThreeDObject {
    private List<Block> blocks;

    @Builder
    public ContainerHolder(int height, int width, int length) {
        super(height, width, length);
        blocks = new ArrayList<>();
        blocks.add(new Block(height, width, length, 0, 0));
    }

    public List<Block> getAvailableStartPositions(Vector<Integer, Integer, Integer> dimensions) {
        // return all blocks that can fit this item on top
        return blocks.parallelStream()
                     .filter(b -> b.getWidth() <= dimensions.getX() && b.getLength() <= dimensions.getY() &&
                                  (dimensions.getZ() + b.getHeight()) <= getHeight()).collect(Collectors.toList());
    }

    public void addPacketAtBlock(Packet packet, Block block) {

    }

}
