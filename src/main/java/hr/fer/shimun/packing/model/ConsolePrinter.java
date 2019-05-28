package hr.fer.shimun.packing.model;

import hr.fer.shimun.packing.implementation.model.ContainerHolder;

import java.util.Map;

public class ConsolePrinter implements ContainerPrinter {
    @Override
    public void print(ContainerHolder containerHolder) {
        int[][] matrix = new int[containerHolder.width][containerHolder.length];

        for (Map.Entry<Point, Packet> entry : containerHolder.getPackedPackets().entrySet()) {
            addValuesToPoint(matrix, entry.getKey(), entry.getValue());
        }

        printMatrix(matrix, containerHolder.width, containerHolder.length);
    }

    private void printMatrix(int[][] matrix, int width, int length) {
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
        System.out.println(total);
    }

    private void addValuesToPoint(int[][] matrix, Point point, Packet value) {
        for (int i = 0; i < value.width; i++) {
            for (int j = 0; j < value.length; j++) {
                matrix[point.getPositionX() + i][point.getPositionY() + j] =
                        matrix[point.getPositionX() + i][point.getPositionY() + j] >
                        point.getPositionZ() + value.getHeight() ? matrix[point.getPositionX() + i][
                                point.getPositionY() + j] : point.getPositionZ() + value.getHeight();
            }
        }
    }
}
