package hr.fer.shimun.packing.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Packet extends ThreeDObject {
    private int boxTypeId;
    private int packetCount;

    @Builder
    public Packet(int height, int width, int length, int boxTypeId, int packetCount) {
        super(height, width, length);
        this.boxTypeId = boxTypeId;
        this.packetCount = packetCount;
    }
}


