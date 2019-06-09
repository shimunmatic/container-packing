package hr.fer.shimun.packing.model;

import hr.fer.shimun.packing.util.Vector;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class ThreeDObject {
    protected int height;
    protected int width;
    protected int length;

    public void setDimensionsFromVector(Vector<Integer, Integer, Integer> vector) {
        this.width = vector.getX();
        this.length = vector.getY();
        this.height = vector.getZ();
    }

    public int getVolume() {
        return height * width * length;
    }
}
