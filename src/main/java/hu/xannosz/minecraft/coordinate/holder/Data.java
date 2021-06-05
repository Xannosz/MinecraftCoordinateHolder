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
            JsonElement dataObject = JsonParser.parseString(FileUtils.readFileToString(DATA_PATH.toFile(), "UTF-8"));
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
            FileUtils.writeStringToFile(DATA_PATH.toFile(), new Gson().toJson(INSTANCE), "UTF-8");
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
        OVER_WORD(0, "Daedalus%20-%20overworld/Daedalus%20day"),
        NETHER(1, "Daedalus%20Nether%20-%20nether/Daedalus%20nether%20light"),
        END(2, "Daedalus%20End%20-%20end/Daedalus%20End"),
        CERBERUS_OVER_WORD(3, "Cerberus%20-%20overworld/Cerberus%20day"),
        CERBERUS_NETHER(4, "Cerberus%20Nether%20-%20nether/Cerberus%20nether%20light"),
        CERBERUS_END(5, "Cerberus%20End%20-%20end/Cerberus%20End");

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
