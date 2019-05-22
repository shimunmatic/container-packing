package hr.fer.shimun.packing.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Block extends ThreeDObject {
    private int positionX;
    private int positionY;

    @Builder
    public Block(int height, int width, int length, int positionX, int positionY) {
        super(height, width, length);
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getEndPositionX() {
        return positionX + width - 1;
    }

    public int getEndPositionY() {
        return positionY + length - 1;
    }
}
