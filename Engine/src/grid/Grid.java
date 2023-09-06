package grid;

import entity.instance.EntityInstance;
import entity.instance.EntityInstanceManager;

import java.util.*;

public class Grid {
    private Integer rows;
    private Integer cols;
    private EntityInstance[][] grid;
    private List<GridIndex> freeIndexes;

    public Grid(Integer rows, Integer cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new EntityInstance[rows][cols];
        freeIndexes = new LinkedList<>();

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                GridIndex index = new GridIndex(i,j);
                freeIndexes.add(index);
            }
        }
    }


    public void setGrid(List<EntityInstanceManager> entityInstanceManagers){
        Random random = new Random();
        int freeSpaceSize = freeIndexes.size();

        for (EntityInstanceManager entityInstanceManager : entityInstanceManagers) {
            for (EntityInstance entityInstance: entityInstanceManager.getEntityInstanceList()) {
                int curr = random.nextInt(freeSpaceSize);
                GridIndex gridIndex = freeIndexes.get(curr);
                freeIndexes.remove(curr);
                freeSpaceSize--;

                entityInstance.setGridIndex(gridIndex);
                grid[gridIndex.getRow()][gridIndex.getCol()] = entityInstance;
            }
        }
    }

    public void moveEntity(EntityInstance entityInstance){
        List<Directions> openDirections = new ArrayList<>();

        checkForEmptySpaces(entityInstance ,openDirections);

        Collections.shuffle(openDirections);
        if(openDirections.size() == 0){
            return;
        }
        GridIndex oldIndex = entityInstance.getGridIndex();
        grid[oldIndex.getRow()][oldIndex.getCol()] = null;
        freeIndexes.add(oldIndex);

        switch (openDirections.get(0)){
            case UP :
                oldIndex.setRow((oldIndex.getRow() - 1 + rows) % rows);
                break;
            case DOWN:
                oldIndex.setRow((oldIndex.getRow() + 1 + rows) % rows);
                break;
            case LEFT:
                oldIndex.setCol((oldIndex.getCol() - 1 + cols) % cols);
                break;
            case RIGHT:
                oldIndex.setCol((oldIndex.getCol() + 1 + cols) % cols);
                break;
        }

        freeIndexes.remove(indexOfGridIndex(oldIndex));
        grid[oldIndex.getRow()][oldIndex.getCol()] = entityInstance;
    }

    private int indexOfGridIndex(GridIndex oldIndex) {
        int i = 0 ;
        for (GridIndex gridIndex :freeIndexes) {
            if(oldIndex.equals(gridIndex)){
                break;
            }
            i++;
        }

        return i;
    }

    private void checkForEmptySpaces(EntityInstance entityInstance , List<Directions> openDirections) {
        GridIndex index = entityInstance.getGridIndex();

        if(grid[(index.getRow() - 1 + rows) % rows][index.getCol()] == null){
            openDirections.add(Directions.UP);
        }
        if(grid[(index.getRow() + 1 + rows) % rows][index.getCol()] == null){
            openDirections.add(Directions.DOWN);
        }
        if(grid[index.getRow()][(index.getCol() + 1 + cols) % cols] == null){
            openDirections.add(Directions.RIGHT);
        }
        if(grid[index.getRow()][(index.getCol() - 1 + cols) % cols] == null){
            openDirections.add(Directions.LEFT);
        }
    }

    public void replaceEntities(EntityInstance toPlace, GridIndex gridIndex){
        grid[gridIndex.getRow()][gridIndex.getRow()] = toPlace;
    }

    public Integer getRows() {
        return rows;
    }

    public Integer getCols() {
        return cols;
    }
}
