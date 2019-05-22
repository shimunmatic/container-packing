package hr.fer.shimun.packing.util;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Vector<X, Y, Z> {
    private X x;
    private Y Y;
    private Z z;
}
