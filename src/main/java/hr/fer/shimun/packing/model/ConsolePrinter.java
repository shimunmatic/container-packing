package hr.fer.shimun.packing.model;

import hr.fer.shimun.packing.implementation.model.ContainerHolder;

public class ConsolePrinter implements ContainerPrinter {
    @Override
    public void print(ContainerHolder containerHolder) {

        printMatrix(containerHolder);
    }

    private void printMatrix(ContainerHolder containerHolder) {
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
        System.out.println(sb.toString());
        System.out.println(String.format("%s Summary %s", "=".repeat(20), "=".repeat(20)));
        System.out.println("Total volume of container: " + containerHolder.getVolume());
        System.out.println("Total volume of packets: " + containerHolder.getPacketsVolume());
        System.out.println("Total volume of inserted packets: " + (total - volumeOfEmptyPackets));
        System.out.println("Percentage of inserted volume: " +
                           ((double) (total - volumeOfEmptyPackets) / containerHolder.getPacketsVolume()));
        System.out.println("Packed unpacked: " + containerHolder.getUnPackedPackets().size());
    }
}
