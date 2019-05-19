package hr.fer.shimun.packing.io;

import hr.fer.shimun.packing.model.Container;
import hr.fer.shimun.packing.model.Packet;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class InputBasket {
    private int problemNumber;
    private int seed;
    private int numberOfBoxTypes;
    private Container container;
    private List<Packet> packetList;
}
