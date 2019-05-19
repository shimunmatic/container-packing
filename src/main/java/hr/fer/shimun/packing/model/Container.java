package hr.fer.shimun.packing.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Container extends ThreeDObject {

    @Builder
    public Container(int height, int width, int length) {
        super(height, width, length);
    }
}

