package manager;

import entity.instance.EntityInstance;
import option1.XmlFullPathDTO;
import option2.*;
import option3.*;
import option4.AmountDTO;
import option4.InfoType;
import option4.PastSimulationInfoDTO;
import option4.SimulationDesiredInfoDTO;
import option4.histogram.*;
import option56.FilePathDTO;

import java.io.*;
import java.util.*;

public class UIManager {
    private PredictionManager predictionManager = new PredictionManager();

    public void predictionMenu() {
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean isXmlLoaded = false;
        boolean isSimulationRun = false;
        boolean isNewXmlFile;

        printPredictionWelcome();
        while (true) {
            printMenu();
            input = scanner.nextLine();
            System.out.println();

            if (input.matches("^[1-7]$")) {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        System.out.println("Load new XML file");
                        System.out.println("-----------------");
                        isNewXmlFile = loadXmlFile(scanner);
                        isXmlLoaded = isNewXmlFile || isXmlLoaded;
                        if(isNewXmlFile) {
                            isSimulationRun = false;
                        }
                        break;
                    case 2:
                        if(checkIfXmlLoaded(isXmlLoaded, "Show current simulation data")) {
                            System.out.println("Show current simulation data");
                            System.out.println("----------------------------");
                            showCurrentSimulationData();
                        }
                        break;
                    case 3:
                        if(checkIfXmlLoaded(isXmlLoaded, "Run new simulation")) {
                            System.out.println("Run new simulation");
                            System.out.println("----------------------");

                            isSimulationRun = runSimulation(scanner) || isSimulationRun;
                        }
                        break;
                    case 4:
                        if(checkIfSimulationRun(isSimulationRun, "View past simulation information")) {
                            System.out.println("View past simulation information");
                            System.out.println("--------------------------------");
                            viewPastSimulationInfo(scanner);
                        }
                        break;
                    case 5:
                        if(checkIfSimulationRun(isSimulationRun, "Store data to file")) {
                            System.out.println("Store data to file");
                            System.out.println("------------------");
                            storeDataToFile(scanner);
                        }
                        break;
                    case 6:
                        System.out.println("Load data from file");
                        System.out.println("-------------------");
                        if(loadDataFromFile(scanner)) {
                            isXmlLoaded = true;
                            isSimulationRun = true;
                        }
                        break;
                    case 7:
                        System.out.println("Exiting the menu");
                        System.out.println("----------------");
                        System.out.println("Please enter any key to continue");
                        scanner.nextLine();
                        scanner.close();
                        return;
                }
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
            }
        }
    }
    private void printPredictionWelcome() {
        System.out.println("==============================");
        System.out.println("   Welcome to Prediction Menu");
        System.out.println("==============================");
        System.out.println();
    }
    private void printMenu() {
        System.out.println();
        System.out.println("-Menu-");
        System.out.println("  1. Load new XML file");
        System.out.println("  2. Show current simulation data");
        System.out.println("  3. Run new simulation");
        System.out.println("  4. View past simulation information");
        System.out.println("  5. Store system data to file");
        System.out.println("  6. Load system data from file");
        System.out.println("  7. Exit");
        System.out.print("Enter your choice (1-7): ");
    }
    private boolean loadXmlFile(Scanner scanner) {
        String xmlFilePath;
        System.out.print("  Please enter full path to your xml file: ");
        xmlFilePath = scanner.nextLine();
        XmlFullPathDTO xmlFullPathDTO = new XmlFullPathDTO(xmlFilePath);

        try {
            predictionManager.loadXmlData(xmlFullPathDTO);
            System.out.println("  XML loaded successfully!");
            System.out.println();
            return true;
        }
        catch (Exception exception) {
            System.out.println("\nERROR: " + exception.getMessage() + "\n");
            return false;
        }
    }


    private void showCurrentSimulationData() {
        SimulationDefinitionDTO simulationDefinitionDTO = predictionManager.showCurrentSimulationData();
        printEntities(simulationDefinitionDTO.getEntityDefinitionDTOList());
        printRules(simulationDefinitionDTO.getRulesDTOList());
        printTermination(simulationDefinitionDTO.getTerminationDTO());
    }
    private void printEntities(List<EntityDefinitionDTO> entityDefinitionDTOList) {
        int counter = 1;

        System.out.println("  Entities:");
        for (EntityDefinitionDTO entityDefinitionDTO : entityDefinitionDTOList) {
            System.out.println("   - Entity number " + counter + ":");
            System.out.println("     1) Name: ------------- " + entityDefinitionDTO.getName());
            System.out.println("     2) Population: ------- " + entityDefinitionDTO.getPopulationNumber());
            printProperties(entityDefinitionDTO.getProperties());
            counter++;
        }
    }
    private void printProperties(List<PropertyDefinitionDTO> propertyDefinitionDTOList) {
        System.out.println("     3) Properties:");
        for (PropertyDefinitionDTO propertyDefinitionDTO : propertyDefinitionDTOList) {
            System.out.println("       * Name: ------------ " + propertyDefinitionDTO.getName());
            System.out.println("       * Type: ------------ " + propertyDefinitionDTO.getType());
            if(propertyDefinitionDTO.getRangeFrom() != null) {
                System.out.println("       * Range: ----------- from " + propertyDefinitionDTO.getRangeFrom() + " to " + propertyDefinitionDTO.getRangeTo());
            }
            System.out.println("       * Is Randomize: ---- " + propertyDefinitionDTO.getRandomize() + "\n");
        }
    }
    private void printRules(List<RulesDTO> rulesDTOList) {
        int counter = 1;

        System.out.println("  Rules:");
        for (RulesDTO rulesDTO : rulesDTOList) {
            System.out.println("   - Rule number " + counter + ":");
            System.out.println("     1) Name: ---------------- " + rulesDTO.getName());
            System.out.println("     2) Ticks: --------------- " + rulesDTO.getTicks());
            System.out.println("     3) Probability: --------- " + rulesDTO.getProbability());
            System.out.println("     4) Number of actions: --- " + rulesDTO.getActionCounter());
            System.out.print("     5) Action types: -------- ");

//            for (String str : rulesDTO.getActionTypes()) {
//                System.out.print(str + "  ");
//            }
//            System.out.println();
//            System.out.println();
//            counter++;
        }
    }
    private void printTermination(TerminationDTO terminationDTO) {
        System.out.println("  Terminations:");
        if(terminationDTO.getTicks() != null) {
            System.out.println("     * Ticks: --------------- " + terminationDTO.getTicks());
        }
        if(terminationDTO.getSeconds() != null) {
            System.out.println("     * Seconds: ------------- " + terminationDTO.getSeconds());
        }

        System.out.println();
    }


    private boolean runSimulation(Scanner scanner) {
        try {
            EnvironmentDefinitionListDTO environmentDefinitionListDTO = runSimulationHelper1();
            SimulationFinishDTO simulationFinishDTO = runSimulationHelper2(environmentDefinitionListDTO, scanner);

            System.out.println("  Simulation finished!");
            System.out.println("   - Simulation id: ----------- " + simulationFinishDTO.getSimulationID());
            System.out.println("   - Simulation stop cause: --- " + simulationFinishDTO.getSimulationStopCause());
            System.out.println();
            return true;
        }
        catch (Exception exception) {
            System.out.println("\nERROR: " + exception.getMessage() + "\n");
            return false;
        }
    }
    private EnvironmentDefinitionListDTO runSimulationHelper1() {
        return predictionManager.runSimulationStep1();
    }
    private SimulationFinishDTO runSimulationHelper2( EnvironmentDefinitionListDTO environmentDefinitionListDTO, Scanner scanner) {
        Integer environmentToInit = 1 , environmentLength = environmentDefinitionListDTO.getEnvironmentDefinitionDTOList().size();
        String input;

        List<EnvironmentInitDTO> environmentInitDTOList = new ArrayList<>();

        for (EnvironmentDefinitionDTO environmentDefinitionDTO : environmentDefinitionListDTO.getEnvironmentDefinitionDTOList()) {
            environmentInitDTOList.add(createEnvironmentInit(environmentDefinitionDTO,false ,scanner));
        }

        do {
            environmentToInit = 1;
            System.out.println("  Please choose which environment var to be initialized: (-1 to not initialize any var)");
            for (EnvironmentDefinitionDTO environmentDefinitionDTO : environmentDefinitionListDTO.getEnvironmentDefinitionDTOList()) {
                System.out.println("  " + environmentToInit + ") " + environmentDefinitionDTO.getName());
                environmentToInit++;
            }

            input = scanner.nextLine();
            if(!input.equals("-1")) {
                if (isNumberInRange(input, environmentLength)) {
                    EnvironmentInitDTO res = createEnvironmentInit(environmentDefinitionListDTO.getEnvironmentDefinitionDTOList().get(Integer.parseInt(input) - 1), true, scanner);
                    environmentInitDTOList.add(Integer.parseInt(input) - 1, res);
                    environmentInitDTOList.remove(Integer.parseInt(input));
                }
            }
        }while (!input.equals("-1"));

        EnvironmentInitListDTO environmentsIntDTO = new EnvironmentInitListDTO(environmentInitDTOList);

        return predictionManager.runSimulationStep2(null,null);//todo
    }
    private EnvironmentInitDTO createEnvironmentInit(EnvironmentDefinitionDTO environmentDefinitionDTO, boolean isNotRandom, Scanner scanner) {
        String name = environmentDefinitionDTO.getName();
        String type = environmentDefinitionDTO.getType();
        String from = environmentDefinitionDTO.getRangeFrom();
        String to = environmentDefinitionDTO.getRangeTo();
        String value = null;

        if(isNotRandom){
            System.out.println("  Creating environment: " + name);
        }

        switch(type) {
            case "DECIMAL":
                if(from != null) {
                    value = getIntFromUser(from, to, isNotRandom, scanner);
                }
                else {
                    value = getIntFromUserNoBounds(isNotRandom, scanner);
                }
                break;
            case "FLOAT":
                if(from != null) {
                    value = getFloatFromUser(from, to, isNotRandom, scanner);
                }
                else {
                    value = getFloatFromUserNoBounds(isNotRandom, scanner);
                }
                break;
            case "BOOLEAN":
                value = getBooleanFromUser(isNotRandom, scanner);
                break;
            case "STRING":
                value = getStringFromUser(isNotRandom, scanner);
                break;
        }
        if(isNotRandom){
            System.out.println();
        }

        return new EnvironmentInitDTO(environmentDefinitionDTO.getName(), value);
    }
    private String getIntFromUser(String from, String to, boolean isUserInput, Scanner scanner) {
        if (isUserInput) {
            String rangePattern = "^(?:" + from + "|" + to + "|[1-9]\\d*)$";
            while (true) {
                System.out.print("    Please enter a decimal number between " + from + " - " + to + ": ");
                String input = scanner.nextLine();
                try {
                    int number = Integer.parseInt(input);
                    if (number >= Float.parseFloat(from) && number <= Float.parseFloat(to)) {
                        return input;
                    }
                    else {
                        System.out.println("Error: Invalid input! Please enter decimal number between " + from + " - " + to +".\n");
                    }
                }
                catch (Exception exception) {
                    System.out.println("Error: Input not a number! Please enter decimal number between " + from + " - " + to +".\n");
                }
           }
        }
        else {
            Random random = new Random();
            int lowerBound = (int) Math.ceil(Double.parseDouble(from));
            int upperBound = (int) Math.floor(Double.parseDouble(to));
            int randomNumber = lowerBound + random.nextInt(upperBound - lowerBound + 1);
            return Integer.toString(randomNumber);
        }
    }
    private String getIntFromUserNoBounds(boolean isUserInput, Scanner scanner) {
        if(isUserInput) {
            while (true) {
                System.out.print("    Please enter a decimal number:");
                String input = scanner.nextLine();
                if (isValidInteger(input)) {
                    return input;
                }
                else {
                    System.out.println("Error: Invalid input! Please enter decimal number.\n");
                }
            }
        }
        else {
            Random random = new Random();
            Integer randomInt = random.nextInt(100);
            return randomInt.toString();
        }
    }
    private String getFloatFromUser(String from, String to, boolean isUserInput, Scanner scanner) {
        if (isUserInput) {
            while (true) {
                System.out.print("    Please enter a float number between " + from + " - " + to + ": ");
                String input = scanner.nextLine();
                try {
                    float number = Float.parseFloat(input);
                    if (number >= Float.parseFloat(from) && number <= Float.parseFloat(to)) {
                        return input;
                    }
                    else {
                        System.out.println("Error: Invalid input! Please enter float number between " + from + " - " + to +".\n");
                    }
                }
                catch (Exception exception) {
                    System.out.println("Error: Input not a number! Please enter float number between " + from + " - " + to +".\n");
                }
            }
        }
        else {
            Random random = new Random();
            double randomNumber = Double.parseDouble(from) + (random.nextDouble() * (Double.parseDouble(to) - Double.parseDouble(from)));
            return Double.toString(randomNumber);
        }
    }
    private String getFloatFromUserNoBounds(boolean isUserInput, Scanner scanner) {
        if(isUserInput) {
            while (true) {
                System.out.print("    Please enter a float number:");
                String input = scanner.nextLine();
                if (isValidFloat(input)) {
                    return input;
                }
                else {
                    System.out.println("Error: Invalid input! Please enter float number.\n");
                }
            }
        }
        else {
            Random random = new Random();
            Float randomFloat = random.nextFloat() * 100;
            return randomFloat.toString();
        }
    }
    private String getBooleanFromUser(boolean isUserInput, Scanner scanner) {
        if(isUserInput) {
            while (true) {
                System.out.print("    Please enter 1 for true / 2 for false:");
                String input = scanner.nextLine();
                if (input.matches("^[1-2]$")) {
                    int choice = Integer.parseInt(input);
                    switch (choice) {
                        case 1:
                            return "true";
                        case 2:
                            return "false";
                    }
                }
                else {
                    System.out.println("Error: Invalid input! Please enter 1 or 2.\n");
                }
            }
        }
        else {
            Random random = new Random();
            Boolean randomBoolean = random.nextBoolean();
            return randomBoolean.toString();
        }
    }
    private String getStringFromUser(boolean isUserInput, Scanner scanner) {
        if(isUserInput) {
            System.out.print("    Please enter a string:");
            return scanner.nextLine();
        }
        else {
            return randomizeString();
        }
    }


    private void viewPastSimulationInfo(Scanner scanner){
        List<PastSimulationInfoDTO> pastSimulationInfoDTO = predictionManager.createPastSimulationInfoDTOList();
        int i ;
        String desiredSimulation , desiredInfo;


        do {
            i=1;
            System.out.println("  Past successful simulations: ");
            for(PastSimulationInfoDTO pastSimulationInfo : pastSimulationInfoDTO){
                System.out.println("   "+i + ")");
                System.out.println("    - Simulation id: ----- " + pastSimulationInfo.getIdNum());
                System.out.println("    - Simulation date: --- " + pastSimulationInfo.getDate());
                i++;
            }
            System.out.println();

            System.out.print("  Please choose a desired simulation: ");

            desiredSimulation = scanner.nextLine();
            if (isNumberInRange(desiredSimulation, pastSimulationInfoDTO.size())) {
               break;
            }
            else {
                System.out.println("ERROR: invalid input please enter a number between 1 - " + pastSimulationInfoDTO.size());
                System.out.println();
            }
        }while (true);

        do {
            System.out.println("  Please choose from the following info options: ");
            System.out.println("   1) Amount");
            System.out.println("   2) Histogram");
            desiredInfo = scanner.nextLine();

            if(desiredInfo.equals("1") || desiredInfo.equals("2")){
                break;
            }
            else {
                System.out.println("ERROR: invalid input please enter a number between 1 - 2");
                System.out.println();
            }
        }while (true);

        switch (desiredInfo){
            case "1":
                amount(desiredSimulation);
                break;
            case "2":
                histogram(scanner, desiredSimulation);
                break;
        }

    }

    private void histogram(Scanner scanner, String desiredSimulation) {
        int i;
        String desiredEntity, desiredProperty;
        SimulationDesiredInfoDTO simulationDesiredInfoDTO = new SimulationDesiredInfoDTO(Integer.parseInt(desiredSimulation), InfoType.HISTOGRAM);
        HistogramAllEntitiesDTO histogramAllEntitiesDTO = predictionManager.getAllEntitiesDTO(simulationDesiredInfoDTO);
        HistogramSingleEntityDTO singleEntityDTO;
        HistogramSinglePropDTO singlePropDTO;

        do {
            i = 1;
            System.out.println("   Entities names: ");
            for(String name : histogramAllEntitiesDTO.getNames()){
                System.out.println("   "+i + ") "+ name);
                i++;
            }
            System.out.println();

            System.out.print("  Please choose a desired entity: ");

            desiredEntity = scanner.nextLine();
            if (isNumberInRange(desiredEntity, histogramAllEntitiesDTO.getNames().size())) {
                singleEntityDTO = new HistogramSingleEntityDTO(histogramAllEntitiesDTO.getNames().get(Integer.parseInt(desiredEntity) -1));
                break;
            }
            else {
                System.out.println("ERROR: invalid input please enter a number between 1 - " + histogramAllEntitiesDTO.getNames().size());
                System.out.println();
            }
        }while (true);

        HistogramAllEntityPropsDTO allEntityPropsDTO = predictionManager.getAllEntityPropsDTO(simulationDesiredInfoDTO, singleEntityDTO);

        do {
            i = 1;
            System.out.println("   Entities properties names: ");
            for(String name : allEntityPropsDTO.getNames()){
                System.out.println("   "+i + ") "+ name);
                i++;
            }
            System.out.println();

            System.out.print("  Please choose a desired property: ");

            desiredProperty = scanner.nextLine();
            if (isNumberInRange(desiredProperty, allEntityPropsDTO.getNames().size())) {
                singlePropDTO = new HistogramSinglePropDTO(allEntityPropsDTO.getNames().get(Integer.parseInt(desiredProperty) - 1));
                break;
            }
            else {
                System.out.println("ERROR: invalid input please enter a number between 1 - " + allEntityPropsDTO.getNames().size());
                System.out.println();
            }
        }while (true);

        HistogramSpecificPropDTO histogramSpecificPropDTO = predictionManager.getHistogram(singlePropDTO,simulationDesiredInfoDTO,singleEntityDTO);

        showHistogram(histogramSpecificPropDTO);
    }

    private void showHistogram(HistogramSpecificPropDTO histogramSpecificPropDTO) {
        String propName = histogramSpecificPropDTO.getName();
        Map<String, Integer> propHistogram = histogramSpecificPropDTO.getHistogram();

        System.out.println("    Key      | Value");
        System.out.println("    -----------------");

        for (Map.Entry<String, Integer> entry : propHistogram.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.printf("    %-10s| %d%n", key, value);
        }
    }

    private void amount(String desiredSimulation) {
        SimulationDesiredInfoDTO simulationDesiredInfoDTO = new SimulationDesiredInfoDTO(Integer.parseInt(desiredSimulation), InfoType.AMOUNT);
        AmountDTO amountDTO = predictionManager.getAmountDTO(simulationDesiredInfoDTO);

        System.out.println("   Entities amount:");
        for (int i = 0; i < amountDTO.getEntityNames().size(); i++){
            System.out.println("    " + i + 1 + ")");
            System.out.println("     Name: -------------- " + amountDTO.getEntityNames().get(i));
            System.out.println("     Beginning count: --- " + amountDTO.getStartingCount().get(i));
            System.out.println("     End count: --------- " + amountDTO.getEndingCount().get(i));
        }
        System.out.println();
    }


    private void storeDataToFile(Scanner scanner){
        try {
            System.out.print("  Please enter your full file path including the file name (without the extension): ");
            FilePathDTO filePathDTO = new FilePathDTO(scanner.nextLine() + ".txt");

            predictionManager.storeDataToFile(filePathDTO);
            System.out.println("  File saved successfully!");
            System.out.println();
        }
        catch (Exception exception) {
            System.out.println("\nERROR: " + exception.getMessage() + "\n");
        }
    }
    private boolean loadDataFromFile(Scanner scanner){
        try {
                System.out.print("  Please enter your full file path including the file name: ");

                String filePath = scanner.nextLine();
                if(!filePath.endsWith(".txt")) {
                    System.out.println("ERROR: Please enter a file ending with .txt\n");
                    return false;
                }
                else {
                    FilePathDTO filePathDTO = new FilePathDTO(filePath);
                    predictionManager.loadDataFromFile(filePathDTO);
                    System.out.println("  File loaded successfully!");
                    return true;
            }
        }
        catch (Exception exception) {
            System.out.println("\nERROR: " + exception.getMessage() + "\n");
            return false;
        }
    }

    private boolean checkIfXmlLoaded(boolean isXmlLoaded, String desireOption) {
        if(!isXmlLoaded) {
            System.out.println("ERROR: Your desire option can not be run. \n       You need to load a valid xml file before trying to " + desireOption + ".\n");
        }
        return isXmlLoaded;
    }
    private boolean checkIfSimulationRun(boolean isSimulationRun, String desireOption) {
        if(!isSimulationRun) {
            System.out.println("ERROR: Your desire option can not be run. \n       You need to successfully run a simulation before trying to do " + desireOption +".\n");
        }
        return isSimulationRun;
    }
    private boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean isValidFloat(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private String randomizeString() {
        String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!?,_-.() ";
        int length = new Random().nextInt(50) + 1;
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = new Random().nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    private boolean isNumberInRange(String input ,int upperNumber) {
        try {
            Integer inputNumber = Integer.parseInt(input);
            if(inputNumber >= 1 && inputNumber <= upperNumber) {
                return true;
            }
            else {
                System.out.println("ERROR: please enter a number between 1 to " + upperNumber + "\n");
                return false;
            }
        }
        catch (Exception exception){
            System.out.println("ERROR: please enter a number between 1 to " + upperNumber + "\n");
            return false;
        }
    }
}
