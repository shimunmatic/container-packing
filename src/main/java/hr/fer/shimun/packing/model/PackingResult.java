package hr.fer.shimun.packing.model;

import hr.fer.shimun.packing.implementation.model.ContainerHolder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class PackingResult {
    private ContainerHolder containerHolder;
    private List<Packet> unpackedPacketList;
}
