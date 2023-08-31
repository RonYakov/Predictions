package option2.ActionDTO;

public class ProximityDTO extends ActionDTO{

    private String depthOfEnvironment;
    private String numOfActionsForMeetsBetweenEntities;

    public ProximityDTO(String name, String mainEntityName, String secondaryEntityName, String depthOfEnvironment, String numOfActionsForMeetsBetweenEntities) {
        super(name, mainEntityName, secondaryEntityName);
        this.depthOfEnvironment = depthOfEnvironment;
        this.numOfActionsForMeetsBetweenEntities = numOfActionsForMeetsBetweenEntities;
    }


    public String getDepthOfEnvironment() {
        return depthOfEnvironment;
    }

    public String getNumOfActionsForMeetsBetweenEntities() {
        return numOfActionsForMeetsBetweenEntities;
    }

}
