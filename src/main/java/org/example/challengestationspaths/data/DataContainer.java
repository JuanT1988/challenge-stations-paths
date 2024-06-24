package org.example.challengestationspaths.data;

import jakarta.annotation.PostConstruct;
import org.example.challengestationspaths.dto.request.PathDTO;
import org.example.challengestationspaths.model.Path;
import org.example.challengestationspaths.model.Station;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataContainer {

    private final List<Station> stations = new ArrayList<>();
    private final List<Path> paths = new ArrayList<>();
    private final Map<Long, String> stationMap = new HashMap<>();

    @PostConstruct
    public void initialize() {
        //*
        addStation(10, "Barcelona");
        addStation(11, "Paris");
        addStation(12, "Berlin");
        addStation(13, "Roma");
        //*/
        //*
        addStation(14, "Moscu");
        addStation(15, "Madrid");
        //*/
        //*
        addStation(16, "Lisboa");
        addStation(17, "Londres");
        addStation(18, "Ginebra");
        addStation(19, "El Cairo");
        addStation(20, "Johanesburgo");
        //*/
        //*
        addPath(new Path(1L, new PathDTO(10L, 11L, 50)));
        addPath(new Path(2L, new PathDTO(10L, 12L, 100)));
        addPath(new Path(3L, new PathDTO(10L, 13L, 60)));
        addPath(new Path(4L, new PathDTO(13L, 12L, 20)));
        //*/
        //*
        addPath(new Path(5L, new PathDTO(16L, 13L, 10)));
        addPath(new Path(6L, new PathDTO(11L, 16L, 5)));
        addPath(new Path(7L, new PathDTO(14L, 12L, 30)));
        addPath(new Path(8L, new PathDTO(14L, 13L, 40)));
        addPath(new Path(9L, new PathDTO(15L, 13L, 120)));
        //*/
        //*
        addPath(new Path(10L, new PathDTO(16L, 17L, 5)));
        addPath(new Path(11L, new PathDTO(17L, 18L, 5)));
        addPath(new Path(12L, new PathDTO(18L, 19L, 5)));
        addPath(new Path(13L, new PathDTO(19L, 20L, 5)));
        addPath(new Path(14L, new PathDTO(20L, 16L, 200)));
        addPath(new Path(15L, new PathDTO(20L, 17L, 10)));
        //*/
    }

    public List<Station> getStations() {
        return stations;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public Map<Long, String> getStationMap() {
        return stationMap;
    }

    public void addStation(long stationId, String name) {
        stationMap.put(stationId, name);
        stations.add(new Station(stationId, name));
    }

    public void addPath(Path path) {
        boolean insert = true;
        for (Path p : paths) {
            if (p.equals(path)) {
                paths.set(paths.indexOf(p), path);
                insert = false;
            }
        }
        if (insert) {
            paths.add(path);
        }
    }

    public Path getPathByStationIds(long sourceId, long destinationId) {
        for (Path path : paths) {
            if ((path.getSourceId() == sourceId && path.getDestinationId() == destinationId)
                    || (path.getSourceId() == destinationId && path.getDestinationId() == sourceId)) {
                return path;
            }
        }
        return null;
    }

    public List<Long> getStationsByPaths(long sourceStationId) {
        List<Long> stationIds = new ArrayList<>();
        stationIds.add(sourceStationId);
        paths.forEach(p -> {
            if (!stationIds.contains(p.getDestinationId())) {
                stationIds.add(p.getDestinationId());
            }
            if (!stationIds.contains(p.getSourceId())) {
                stationIds.add(p.getSourceId());
            }
        });
        return stationIds;
    }

}
