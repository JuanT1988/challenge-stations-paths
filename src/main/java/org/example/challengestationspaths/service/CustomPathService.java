package org.example.challengestationspaths.service;

import org.example.challengestationspaths.data.DataContainer;
import org.example.challengestationspaths.dto.request.PathDTO;
import org.example.challengestationspaths.dto.response.PathResponseDTO;
import org.example.challengestationspaths.exception.BadRequestException;
import org.example.challengestationspaths.exception.NotFoundException;
import org.example.challengestationspaths.model.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("pathService")
public class CustomPathService implements PathService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomPathService.class);

    @Autowired
    private DataContainer dataContainer;

    /**
     * Carga de paths nuevos y modificacion del costo del existente, si esta bien cargado.
     *
     * @param pathId
     * @param request
     * @throws BadRequestException
     */
    @Override
    public void addPath(Long pathId, PathDTO request) throws BadRequestException {
        validateSourceAndDestination(request.getSourceId(), request.getDestinationId());
        Path path = new Path(pathId, request);
        boolean sourceAndDestinationExist = dataContainer.getPaths().stream().anyMatch(p -> p.equals(path));
        boolean pathIdExist = dataContainer.getPaths().stream().anyMatch(p -> p.getPathId() == pathId);
        if (sourceAndDestinationExist && !pathIdExist) {
            LOGGER.warn("Source {} and destination {} paths already exist", request.getSourceId(), request.getDestinationId());
            throw new BadRequestException("Path with Source " + request.getSourceId() + " and Destination " + request.getDestinationId() + " already exists");
        }
        dataContainer.addPath(path);
    }

    /**
     * Consulta del camino optimo.
     *
     * @param sourceId
     * @param destinationId
     * @return
     * @throws BadRequestException
     * @throws NotFoundException
     */
    @Override
    public PathResponseDTO getPaths(long sourceId, long destinationId) throws BadRequestException, NotFoundException {
        validateSourceAndDestination(sourceId, destinationId);
        List<Long> stations = dataContainer.getStationsByPaths(sourceId);
        if (stations.isEmpty() || stations.size() < 2) {
            LOGGER.warn("No stations found for path {}->{}", sourceId, destinationId);
            throw new NotFoundException("No stations found");
        }
        long[] stationsIds = stations.stream().mapToLong(s -> s).toArray();
        double[][] graph = prepareGraph(stationsIds);
        printGraph(graph, stationsIds);
        return getOptimalRoute(graph, stationsIds, sourceId, destinationId);
    }

    /**
     * A modo de pruebas, para consultar todo lo que existe en memoria de los Path
     *
     * @return
     */
    @Override
    public List<Path> getAllPaths() {
        return dataContainer.getPaths();
    }

    /**
     * Valida que el source_id y destination_id no sean iguales.
     * Que existan ambos como id de Station.
     *
     * @param sourceId
     * @param destinationId
     */
    private void validateSourceAndDestination(long sourceId, long destinationId) {
        if (sourceId == destinationId) {
            LOGGER.warn("Source {} and destination {} cannot both be the same", sourceId, destinationId);
            throw new BadRequestException("Source " + sourceId + " and Destination " + destinationId + " cannot both be the same");
        } else if (!dataContainer.getStationMap().containsKey(sourceId)) {
            LOGGER.warn("Source {} does not exist", sourceId);
            throw new BadRequestException("Source " + sourceId + " does not exist");
        } else if (!dataContainer.getStationMap().containsKey(destinationId)) {
            LOGGER.warn("Destination {} does not exist", destinationId);
            throw new BadRequestException("Destination " + destinationId + " does not exist");       
        }
    }

    /**
     * Prepara los datos en un grafo para poder validar el camino optimo.
     *
     * @param stationsIds
     * @return
     */
    private double[][] prepareGraph(long[] stationsIds) {
        double[][] graph = new double[stationsIds.length][stationsIds.length];
        for (int i = 0; i < stationsIds.length; i++) {
            for (int j = 0; j < stationsIds.length; j++) {
                Path path = dataContainer.getPathByStationIds(stationsIds[i], stationsIds[j]);
                graph[i][j] = path == null ? Double.MAX_VALUE : path.getCost();
            }
        }
        return graph;
    }

    /**
     * Con la iteracion del grafo se obtienen los costos minimos asociados a cada Path.
     *
     * @param graph
     * @param stationsIds
     * @param sourceId
     * @return
     */
    private List<PathResponseDTO> calculateOptimalRoutes(double[][] graph, long[] stationsIds, long sourceId) {
        double[] costs = new double[stationsIds.length];
        boolean[] visited = new boolean[stationsIds.length];
        for (int i = 0; i < stationsIds.length; i++) {
            costs[i] = Double.MAX_VALUE;
            visited[i] = false;
        }
        costs[0] = 0;
        List<PathResponseDTO> possiblesPaths = new ArrayList<>();

        for (int i = 0; i < stationsIds.length; i++) {
            int u = getMinIndexCost(costs, visited);
            visited[u] = true;
            for (int j = 0; j < stationsIds.length; j++) {
                if (!visited[j]
                        && graph[u][j] > 0
                        && costs[u] != Double.MAX_VALUE
                        && costs[u] + graph[u][j] < costs[j]) {
                    costs[j] = costs[u] + graph[u][j];
                    addOrUpdatePossiblePathResponse(possiblesPaths, stationsIds[u], stationsIds[j], costs[j], sourceId);
                }
            }
        }
        return possiblesPaths;
    }

    /**
     * Se toman los datos calculados para ir generando las posibles rutas con los valores minimos, usando el source_id como origen.
     *
     * @param possiblesPaths
     * @param lastStation
     * @param nextStation
     * @param cost
     * @param sourceId
     */
    private void addOrUpdatePossiblePathResponse(List<PathResponseDTO> possiblesPaths, long lastStation, long nextStation, double cost, long sourceId) {
        PathResponseDTO response = new PathResponseDTO();
        int index = -1;
        boolean addOrUpdate = false;
        if (lastStation == sourceId) {
            response.getPath().add(lastStation);
            addOrUpdate = true;
        } else {
            for (PathResponseDTO resp : possiblesPaths) {
                index++;
                if (resp.getPath().get(resp.getPath().size() - 1) == lastStation) {
                    response = resp;
                    addOrUpdate = true;
                    break;
                }
            }
            if (!addOrUpdate && possiblesPaths.stream().anyMatch(p -> p.getPath().contains(lastStation))) {
                List<Long> paths = new ArrayList<>();
                possiblesPaths.stream().filter(p -> p.getPath().contains(lastStation)).findFirst().ifPresent(p -> {
                    paths.addAll(p.getPath().subList(0, p.getPath().indexOf(lastStation) + 1));});
                response.setPath(paths);
                addOrUpdate = true;
                index = -1;
            }
        }
        if (addOrUpdate) {
            response.setCost(cost);
            response.getPath().add(nextStation);
            if (index != -1) {
                possiblesPaths.set(index, response);
            } else {
                possiblesPaths.add(response);
            }
        }
    }

    /**
     * De todas las posibles rutas se obtiene la que coincide con el destino y se busca que tenga el menor costo.
     *
     * @param graph
     * @param stationsIds
     * @param sourceId
     * @param destinationId
     * @return
     */
    private PathResponseDTO getOptimalRoute(double[][] graph, long[] stationsIds, long sourceId, long destinationId) {
        List<PathResponseDTO> possiblesResponses = calculateOptimalRoutes(graph, stationsIds, sourceId);
        PathResponseDTO response = null;
        double minCost = Double.MAX_VALUE;
        for (PathResponseDTO resp : possiblesResponses) {
            if (resp.getPath().get(resp.getPath().size() - 1) == destinationId && resp.getCost() < minCost) {
                minCost = resp.getCost();
                response = resp;
            }
        }
        return response;
    }

    /**
     * Se obtiene el indice del vector con menor costo.
     *
     * @param costs
     * @param vertexAlreadyProcessed
     * @return
     */
    private int getMinIndexCost(double[] costs, boolean[] vertexAlreadyProcessed) {
        double min = Double.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < costs.length; i++) {
            if (!vertexAlreadyProcessed[i] && costs[i] < min) {
                min = costs[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private void printGraph(double[][] graph, long[] stationsIds) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ \n");
        for (int i = 0; i < stationsIds.length; i++) {
            sb.append("[ ");
            for (int j = 0; j < stationsIds.length; j++) {
                sb.append("{ ")
                        .append(stationsIds[j])
                        .append(" -> ")
                        .append(stationsIds[i])
                        .append(" : ")
                        .append(graph[i][j] == Double.MAX_VALUE ? 0 : graph[i][j])
                        .append(" }, ");
            }
            sb.replace(sb.lastIndexOf(" }, "), sb.length(), " } ], \n");
        }
        sb.replace(sb.lastIndexOf(" } ], "), sb.length(), " } ]\n }");
        LOGGER.debug(sb.toString());
    }

}
