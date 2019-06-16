package hr.fer.shimun.packing.printer;

import hr.fer.shimun.packing.implementation.model.ContainerHolder;
import hr.fer.shimun.packing.model.Packet;

public class ConsolePrinter implements ContainerPrinter {

    @Override
    public String print(ContainerHolder containerHolder) {
        return printMatrix(containerHolder);
    }

    private String printMatrix(ContainerHolder containerHolder) {
        int width = containerHolder.getWidth();
        int length = containerHolder.getLength();
        int[][] matrix = containerHolder.getMatrix();
        int volumeOfEmptyPackets = containerHolder.getVolumeOfEmptyPackets();
        StringBuilder sb = new StringBuilder();
        int total = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                sb.append(" ").append(matrix[i][j]).append(" ");
                total += matrix[i][j];
            }
            sb.append("\n");
        }

        sb.append(String.format("%s Summary %s", "=".repeat(20), "=".repeat(20))).append("\n");
        sb.append("Total volume of container: ").append(containerHolder.getVolume()).append("\n");
        sb.append("Total volume of packets: ").append(containerHolder.getPacketsVolume()).append("\n");
        sb.append("Total volume of inserted packets: ").append(total - volumeOfEmptyPackets).append("\n");
        sb.append("Percentage of inserted volume: ")
          .append((double) (total - volumeOfEmptyPackets) / containerHolder.getPacketsVolume()).append("\n");
        sb.append("Packets unpacked: ").append(containerHolder.getUnPackedPackets().size()).append("\n");
        sb.append("Unpacked packets volumme: ")
          .append(containerHolder.getUnPackedPackets().stream().mapToInt(Packet::getVolume).sum()).append("\n");
        sb.append("Volume of empty packets: ").append(volumeOfEmptyPackets).append("\n");
        return sb.toString();
    }
}
