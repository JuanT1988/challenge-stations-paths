package org.example.challengestationspaths.data;

import org.example.challengestationspaths.model.Path;
import org.example.challengestationspaths.model.Station;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataContainer {

    private final List<Station> stations = new ArrayList<>();
    private final List<Path> paths = new ArrayList<>();
    private final Map<Long, String> stationMap = new HashMap<>();

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
        boolean insert = true;
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getStationId() == stationId && !stations.get(i).getName().equals(name)) {
                stations.get(i).setName(name);
                insert = false;
            }
        }
        stationMap.put(stationId, name);
        if (insert) {
            stations.add(new Station(stationId, name));
        }
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
