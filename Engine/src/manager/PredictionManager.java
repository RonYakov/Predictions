package manager;

import entity.definition.EntityDefinition;
import entity.instance.EntityInstance;
import entity.instance.EntityInstanceManager;
import exception.FileNotFoundException;
import option1.XmlFullPathDTO;
import option2.*;
import option2.ActionDTO.ActionDTO;
import option3.*;
import option4.AmountDTO;
import option4.PastSimulationInfoDTO;
import option4.SimulationDesiredInfoDTO;
import option4.histogram.*;
import option56.FilePathDTO;
import property.definition.PropertyDefinition;
import property.instance.AbstractPropertyInstance;
import rule.Rule;
import rule.action.api.Action;
import simulation.impl.SimulationExecutionDetails;
import simulation.impl.SimulationRunner;
import simulation.definition.SimulationDefinition;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static factory.instance.FactoryInstance.createSimulation;

public class PredictionManager {
    private SimulationDefinition simulationDefinition;
    private Map<Integer, SimulationExecutionDetails> simulationExecutionDetailsMap;
    private Integer numOfThreads;
    private XmlLoader xmlLoader;
    private ExecutorService executorService;
    private int currIDNum;


    public PredictionManager() {
        simulationDefinition = null;
        simulationExecutionDetailsMap = new HashMap<>();
        xmlLoader = new XmlLoader();
        currIDNum = 1;
    }

    public void storeDataToFile(FilePathDTO filePathDTO){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePathDTO.getFilePath()))) {
            outputStream.writeObject(simulationDefinition);
            //outputStream.writeObject(simulationRunners);
            //todo - check ^
            outputStream.writeObject(currIDNum);
            outputStream.flush();
        } catch (Exception ignore) {
            throw new FileNotFoundException("file not found, please type a valid file path" + ignore.getMessage());
        }
    }
    public void loadDataFromFile(FilePathDTO filePathDTO){
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePathDTO.getFilePath()))) {
            simulationDefinition = (SimulationDefinition) inputStream.readObject();
            //simulationRunners = (List<SimulationRunner>) inputStream.readObject();
            //todo - check ^
            currIDNum = (Integer) inputStream.readObject();
        } catch (Exception ignore) {
            throw new FileNotFoundException("file not found, please type a valid file path");
        }
    }

    public void loadXmlData(XmlFullPathDTO xmlFullPathDTO) throws JAXBException, IOException {
        SimulationDefinition newSimulationDefinition = xmlLoader.loadXmlData(xmlFullPathDTO.getFullPathXML());
        this.simulationDefinition = newSimulationDefinition;
        simulationExecutionDetailsMap.clear();
        currIDNum = 1;
        numOfThreads = simulationDefinition.getNumOfThreads();
        executorService = Executors.newFixedThreadPool(numOfThreads);
    }


    public EnvironmentDefinitionListDTO runSimulationStep1() {
        List<EnvironmentDefinitionDTO> environmentDefinitionDTOList = new ArrayList<>();
        List<PropertyDefinition> environmentDefinitionList = new ArrayList<>(simulationDefinition.getEnvironmentsDef().values());

        for(PropertyDefinition environment: environmentDefinitionList){
            if(environment.getRange() == null){
                environmentDefinitionDTOList.add(new EnvironmentDefinitionDTO(environment.getName(),environment.getType().toString(),
                        null,null));
            }else {
                Double from = environment.getRange().getFrom();
                Double to = environment.getRange().getTo();

                environmentDefinitionDTOList.add(new EnvironmentDefinitionDTO(environment.getName(),environment.getType().toString(),
                        from.toString(),to.toString()));
            }
        }

        return new EnvironmentDefinitionListDTO(environmentDefinitionDTOList);
    }

    public SimulationFinishDTO runSimulationStep2(List<EnvironmentInitDTO> environmentInitListDTO, List<EntityPopulationDTO> entityPopulationDTOList) {
        for(EntityPopulationDTO entityPopulationDTO : entityPopulationDTOList){
            simulationDefinition.getEntitiesDef().get(entityPopulationDTO.getEntityName()).setPopulation(entityPopulationDTO.getCount());
        }

        SimulationExecutionDetails simulationExecutionDetails = createSimulation(simulationDefinition, currIDNum);
        Map<String, AbstractPropertyInstance> environment = simulationExecutionDetails.getEnvironments();

        for (EnvironmentInitDTO environmentInitDTO: environmentInitListDTO){
            environment.get(environmentInitDTO.getName()).setValue(environmentInitDTO.getNewValue());
        }

        simulationExecutionDetailsMap.put(currIDNum, simulationExecutionDetails);

        SimulationRunner simulationRunner = new SimulationRunner(simulationExecutionDetails);

        // Submit the task to the thread pool
        executorService.submit(simulationRunner);

        currIDNum++;

        return new SimulationFinishDTO(simulationExecutionDetails.getIdentifyNumber(), simulationExecutionDetails.getSimulationStopCause());
    }

    public SimulationDefinitionDTO showCurrentSimulationData() {
        TerminationDTO terminationDTO = new TerminationDTO(simulationDefinition.getTermination().getTicks(), simulationDefinition.getTermination().getSeconds());
        List<RulesDTO> rulesDTOList = createRulesDTOList();
        List<EntityDefinitionDTO> entityDefinitionDTOList = createEntityDTOlist();

        return new SimulationDefinitionDTO(entityDefinitionDTOList, rulesDTOList,terminationDTO, simulationDefinition.getGrid().getRows(), simulationDefinition.getGrid().getCols());
    }

    public SimulationDefinition getSimulationDefinition() {
        return simulationDefinition;
    }

    private List<RulesDTO> createRulesDTOList(){
        List<RulesDTO> rulesDTOList = new ArrayList<>();

        for (Rule rule: simulationDefinition.getRules()){
            Integer actionCount = rule.numOfActions();
            List<ActionDTO> actions = new ArrayList<>();
            for(Action action: rule.getActions()){
                actions.add(createActionDTO(action));
            }
            rulesDTOList.add(new RulesDTO(rule.getName(), rule.getActivation().getTicks(), rule.getActivation().getProbability(), actionCount, actions ));
        }
        return rulesDTOList;
    }

    private ActionDTO createActionDTO(Action action) {
        return action.createDTO();
    }

    private List<EntityDefinitionDTO> createEntityDTOlist(){
        List<EntityDefinitionDTO> entityDefinitionDTOList = new ArrayList<>();
        List<EntityDefinition> entityDefinitionList = new ArrayList<>(simulationDefinition.getEntitiesDef().values());
        for (EntityDefinition entityDefinition : entityDefinitionList){
            List<PropertyDefinitionDTO> propertyDefinitionDTOList = createPropertyDTOlist(new ArrayList<>(entityDefinition.getProperties().values()));
            entityDefinitionDTOList.add(new EntityDefinitionDTO(entityDefinition.getName(), entityDefinition.getPopulation(),
                    propertyDefinitionDTOList));
        }

        return entityDefinitionDTOList;
    }

    private List<PropertyDefinitionDTO> createPropertyDTOlist(List<PropertyDefinition> propertyDefinitions){
        List<PropertyDefinitionDTO> propertyDefinitionDTOList = new ArrayList<>();

        for(PropertyDefinition propertyDefinition: propertyDefinitions){
            if(propertyDefinition.getRange() != null) {
                propertyDefinitionDTOList.add(new PropertyDefinitionDTO(propertyDefinition.getName(),propertyDefinition.getType().toString(),
                        propertyDefinition.getRange().getFrom(), propertyDefinition.getRange().getTo(),
                        propertyDefinition.getValue().isRandomInitialize()));
            }
            else {
                propertyDefinitionDTOList.add(new PropertyDefinitionDTO(propertyDefinition.getName(),propertyDefinition.getType().toString(),
                        null, null,
                        propertyDefinition.getValue().isRandomInitialize()));
            }
        }

        return propertyDefinitionDTOList;
    }

    public List<PastSimulationInfoDTO> createPastSimulationInfoDTOList(){
        List<PastSimulationInfoDTO> pastSimulationInfoDTOS = new ArrayList<>();
        List<SimulationExecutionDetails> simulationExecutionDetails = new ArrayList<>(simulationExecutionDetailsMap.values());

        for(SimulationExecutionDetails simulationExecutionDetails1 : simulationExecutionDetails){
            pastSimulationInfoDTOS.add(new PastSimulationInfoDTO(simulationExecutionDetails1.getFormattedDate(), simulationExecutionDetails1.getIdentifyNumber()));
        }

        return pastSimulationInfoDTOS;
    }

    public AmountDTO getAmountDTO(SimulationDesiredInfoDTO simulationDesiredInfoDTO){
        List<Integer> beginning =  new ArrayList<>();
        List<Integer> end =  new ArrayList<>();
        List<String> names = new ArrayList<>();

        SimulationExecutionDetails simulationExecutionDetails = simulationExecutionDetailsMap.get(simulationDesiredInfoDTO.getIdNum() - 1);
        List<EntityInstanceManager> entityInstanceList = new ArrayList<>(simulationExecutionDetails.getEntityManager().values());

        for(EntityInstanceManager entityInstanceManager: entityInstanceList){
            beginning.add(simulationDefinition.getEntitiesDef().get(entityInstanceManager.getName()).getPopulation());
            end.add(entityInstanceManager.getEntityInstanceList().size());
            names.add(entityInstanceManager.getName());
        }
        return new AmountDTO(names,beginning,end);
    }

    public HistogramAllEntitiesDTO getAllEntitiesDTO(SimulationDesiredInfoDTO simulationDesiredInfoDTO){
        List<String> names = new ArrayList<>();

        SimulationExecutionDetails simulationExecutionDetails = simulationExecutionDetailsMap.get(simulationDesiredInfoDTO.getIdNum() - 1);
        List<EntityInstanceManager> entityInstanceList = new ArrayList<>(simulationExecutionDetails.getEntityManager().values());

        for(EntityInstanceManager entityInstanceManager: entityInstanceList){
            names.add(entityInstanceManager.getName());
        }

        return new HistogramAllEntitiesDTO(names);
    }

    public HistogramAllEntityPropsDTO getAllEntityPropsDTO(SimulationDesiredInfoDTO simulationDesiredInfoDTO, HistogramSingleEntityDTO singleEntityDTO){
        List<String> names = new ArrayList<>();
        EntityDefinition entityDefinition = simulationDefinition.getEntitiesDef().get(singleEntityDTO.getName());

        List<PropertyDefinition> propertyDefinitions = new ArrayList<>(entityDefinition.getProperties().values());

        for(PropertyDefinition propertyDefinition: propertyDefinitions){
            names.add(propertyDefinition.getName());
        }

        return new HistogramAllEntityPropsDTO(names);
    }

    public HistogramSpecificPropDTO getHistogram(HistogramSinglePropDTO histogramSinglePropDTO, SimulationDesiredInfoDTO simulationDesiredInfoDTO ,HistogramSingleEntityDTO singleEntityDTO){
        EntityInstanceManager entityInstanceManager = simulationExecutionDetailsMap.get(simulationDesiredInfoDTO.getIdNum() - 1).getEntityManager().get(singleEntityDTO.getName());


        Map<String,Integer> histogram = new HashMap<>();

        for(EntityInstance entityInstance: entityInstanceManager.getEntityInstanceList()){
            if(histogram.get(entityInstance.getProperty(histogramSinglePropDTO.getName()).getValue()) == null){
                histogram.put(entityInstance.getProperty(histogramSinglePropDTO.getName()).getValue(), 1);
            }
            else {
                Integer temp = histogram.get(entityInstance.getProperty(histogramSinglePropDTO.getName()).getValue());
                histogram.put(entityInstance.getProperty(histogramSinglePropDTO.getName()).getValue(), temp + 1);
            }
        }

        return new HistogramSpecificPropDTO(histogramSinglePropDTO.getName(), histogram);
    }
}
