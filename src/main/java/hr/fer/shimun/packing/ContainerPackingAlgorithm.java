package hr.fer.shimun.packing;

import hr.fer.shimun.packing.model.Container;
import hr.fer.shimun.packing.model.Packet;
import hr.fer.shimun.packing.model.PackingResult;

import java.util.List;

public interface ContainerPackingAlgorithm {
    PackingResult pack(Container container, List<Packet> packetList);
}
