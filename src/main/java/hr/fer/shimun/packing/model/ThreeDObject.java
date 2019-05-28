package hr.fer.shimun.packing.model;

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
}
