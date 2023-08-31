package option2.ActionDTO;

public class ReplaceDTO  extends ActionDTO{

    private String mode;

    public ReplaceDTO(String name, String mainEntityName, String secondaryEntityName, String mode) {
        super(name, mainEntityName, secondaryEntityName);

        this.mode = mode;
    }


    public String getMode() {
        return mode;
    }
}
