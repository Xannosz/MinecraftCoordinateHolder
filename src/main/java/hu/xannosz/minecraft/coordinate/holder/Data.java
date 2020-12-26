package hu.xannosz.minecraft.coordinate.holder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Data {

    private final static Path DATA_PATH = Paths.get("data/data.json");

    public static Data INSTANCE = new Data();

    @Getter
    private final Set<Coordinate> coordinates = new HashSet<>();

    @Getter
    @Setter
    private String mapPrefix = "/map";

    @Getter
    @Setter
    private String mainPage = "http://google.com";

    public void addCoordinate(String name, int x, int y, int z, Dimension dimension) {
        coordinates.add(new Coordinate(name, x, y, z, dimension));
    }

    private Data() {

    }

    public static void readData() {
        try {
            JsonElement dataObject = JsonParser.parseString(FileUtils.readFileToString(DATA_PATH.toFile()));
            INSTANCE = new Gson().fromJson(dataObject, Data.class);
        } catch (Exception e) {
            e.printStackTrace();
            INSTANCE = new Data();
        }
    }

    public static void writeData() {
        try {
            DATA_PATH.toFile().getParentFile().mkdirs();
            DATA_PATH.toFile().createNewFile();
            FileUtils.writeStringToFile(DATA_PATH.toFile(), new Gson().toJson(INSTANCE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Coordinate {
        private final String name;
        private final int x;
        private final int y;
        private final int z;
        private final Dimension dimension;
    }

    public static class CoordinateComparator implements Comparator<Coordinate> {
        @Override
        public int compare(Coordinate coord1, Coordinate coord2) {
            if (coord2.getDimension().getI() < coord1.getDimension().getI()) {
                return 1;
            } else if (coord2.getDimension().getI() > coord1.getDimension().getI()) {
                return -1;
            } else {
                if (coord1.getX() > coord2.getX()) {
                    return 1;
                } else if (coord1.getX() < coord2.getX()) {
                    return -1;
                } else {
                    return Integer.compare(coord1.getZ(), coord2.getZ());
                }
            }
        }
    }

    public enum Dimension {
        OVER_WORD(0, "minecraft%20-%20overworld/day"), NETHER(1, "minecraft%20-%20nether/nether"), END(2, "minecraft%20-%20end/end");

        @Getter
        private final int i;

        @Getter
        private final String link;

        Dimension(int i, String link) {
            this.i = i;
            this.link = link;
        }
    }
}
