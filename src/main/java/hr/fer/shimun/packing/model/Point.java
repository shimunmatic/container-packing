package hr.fer.shimun.packing.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class Point extends ThreeDObject {
    private int positionX;
    private int positionY;
    private int positionZ;

    @Builder
    public Point(int height, int width, int length, int positionX, int positionY, int positionZ) {
        super(height, width, length);
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;
    }

    public int getEndPositionX() {
        return positionX + width - 1;
    }

    public int getEndPositionY() {
        return positionY + length - 1;
    }

    public int getEndPositionZ() {
        return positionZ + height - 1;
    }
}
