package hr.fer.shimun.packing.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class PackingResult {
    private Container container;
    private List<Packet> unpackedPacketList;
}
