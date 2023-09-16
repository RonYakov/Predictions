package manager;

import entity.definition.EntityDefinition;
import entity.instance.EntityInstance;
import entity.instance.EntityInstanceManager;
import ex2DTO.*;
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
import simulation.impl.SimulationState;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static factory.instance.FactoryInstance.createSimulation;

public class PredictionManager {
    private SimulationDefinition simulationDefinition;
    private Map<Integer, SimulationExecutionDetails> simulationExecutionDetailsMap;
    private Integer numOfThreads;
    private XmlLoader xmlLoader;
    private ThreadPoolExecutor executorService;
    private int currIDNum;
    private Integer simDoneCounter = 0;

    private Boolean isNewSimLoaded = false;


    public PredictionManager() {
        simulationDefinition = null;
        simulationExecutionDetailsMap = new HashMap<>();
        xmlLoader = new XmlLoader();
        currIDNum = 1;
    }

    public void storeDataToFile(FilePathDTO filePathDTO){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePathDTO.getFilePath()))) {
            outputStream.writeObject(simulationDefinition);
            outputStream.writeObject(currIDNum);
            outputStream.flush();
        } catch (Exception ignore) {
            throw new FileNotFoundException("file not found, please type a valid file path" + ignore.getMessage());
        }
    }
    public void loadDataFromFile(FilePathDTO filePathDTO){
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePathDTO.getFilePath()))) {
            simulationDefinition = (SimulationDefinition) inputStream.readObject();
            currIDNum = (Integer) inputStream.readObject();
        } catch (Exception ignore) {
            throw new FileNotFoundException("file not found, please type a valid file path");
        }
    }

    public void loadXmlData(XmlFullPathDTO xmlFullPathDTO) throws JAXBException, IOException {
        SimulationDefinition newSimulationDefinition = xmlLoader.loadXmlData(xmlFullPathDTO.getFullPathXML());
        setGetIsNewSimLoaded("true");
        if(executorService != null) {
            executorService.getQueue().clear();
            stopAllSimulation();
            executorService.shutdownNow();
        }

        this.simulationDefinition = newSimulationDefinition;
        simulationExecutionDetailsMap.clear();
        currIDNum = 1;
        numOfThreads = simulationDefinition.getNumOfThreads();
        executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(numOfThreads);
        isDoneCounterChanger("reset");
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
        simulationExecutionDetails.setEnvironmentInitListDTO(environmentInitListDTO);
        simulationExecutionDetails.setEntityPopulationDTOList(entityPopulationDTOList);

        Map<String, AbstractPropertyInstance> environment = simulationExecutionDetails.getEnvironments();

        for (EnvironmentInitDTO environmentInitDTO: environmentInitListDTO){
            environment.get(environmentInitDTO.getName()).setValue(environmentInitDTO.getNewValue());
        }

        simulationExecutionDetailsMap.put(currIDNum, simulationExecutionDetails);

        SimulationRunner simulationRunner = new SimulationRunner(simulationExecutionDetails);

        simulationExecutionDetails.setPredictionManager(this);
        executorService.submit(simulationRunner);

        currIDNum++;

        return new SimulationFinishDTO(simulationExecutionDetails.getIdentifyNumber(), simulationExecutionDetails.getSimulationStopCause());
    }

    public void simDone() {
        isDoneCounterChanger("simDone");
    }
    private synchronized Boolean setGetIsNewSimLoaded(String isNewSimLoaded) {
        if(isNewSimLoaded.equals("true")) {
            this.isNewSimLoaded = true;
        }
        else if(isNewSimLoaded.equals("false")) {
            this.isNewSimLoaded = false;
        }
        return this.isNewSimLoaded;
    }

    private synchronized void isDoneCounterChanger(String action) {
        if(action.equals("simDone")) {
            this.simDoneCounter++;
        }
        else {
            this.simDoneCounter = 0;
        }
    }

    public List<EnvironmentInitDTO> getEnvironmentRerun(SimulationIDDTO simulationIDDTO){
        return simulationExecutionDetailsMap.get(simulationIDDTO.getId()).getEnvironmentInitListDTO();
    }
    public List<EntityPopulationDTO> getEntityRerun(SimulationIDDTO simulationIDDTO){
        return simulationExecutionDetailsMap.get(simulationIDDTO.getId()).getEntityPopulationDTOList();
    }
    public SimulationDefinitionDTO showCurrentSimulationData() {
        TerminationDTO terminationDTO = new TerminationDTO(simulationDefinition.getTermination().getTicks(), simulationDefinition.getTermination().getSeconds());
        List<RulesDTO> rulesDTOList = createRulesDTOList();
        List<EntityDefinitionDTO> entityDefinitionDTOList = createEntityDTOlist();
        List<PropertyDefinition> environmentDefenitionList = new ArrayList<>(simulationDefinition.getEnvironmentsDef().values());
        List<PropertyDefinitionDTO> environmentDefenitionDTOList = createPropertyDTOlist(environmentDefenitionList);

        return new SimulationDefinitionDTO(entityDefinitionDTOList, rulesDTOList, environmentDefenitionDTOList, terminationDTO, simulationDefinition.getGrid().getRows(), simulationDefinition.getGrid().getCols());
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
                if(propertyDefinition.getValue() != null) {
                    propertyDefinitionDTOList.add(new PropertyDefinitionDTO(propertyDefinition.getName(),propertyDefinition.getType().toString(),
                            propertyDefinition.getRange().getFrom(), propertyDefinition.getRange().getTo(),
                            propertyDefinition.getValue().isRandomInitialize()));
                }
                else {
                    propertyDefinitionDTOList.add(new PropertyDefinitionDTO(propertyDefinition.getName(),propertyDefinition.getType().toString(),
                            propertyDefinition.getRange().getFrom(), propertyDefinition.getRange().getTo(),
                            null));
                }
            }
            else {
                if(propertyDefinition.getValue() != null) {
                    propertyDefinitionDTOList.add(new PropertyDefinitionDTO(propertyDefinition.getName(),propertyDefinition.getType().toString(),
                            null, null,
                            propertyDefinition.getValue().isRandomInitialize()));
                }
                else {
                    propertyDefinitionDTOList.add(new PropertyDefinitionDTO(propertyDefinition.getName(),propertyDefinition.getType().toString(),
                            null, null, null));
                }
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

    public PastSimulationInfoDTO getPastSimulationInfo(SimulationIDDTO simulationIDDTO) {
        SimulationExecutionDetails simulationExecutionDetails = simulationExecutionDetailsMap.get(simulationIDDTO.getId());

        return new PastSimulationInfoDTO(simulationExecutionDetails.getFormattedDate(), simulationExecutionDetails.getIdentifyNumber());
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
        EntityInstanceManager entityInstanceManager = simulationExecutionDetailsMap.get(simulationDesiredInfoDTO.getIdNum()).getEntityManager().get(singleEntityDTO.getName());


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

    public List<EntityCountDTO> getEntitiesCountDTO(Integer id){
        List<EntityCountDTO>res = new ArrayList<>();
        SimulationExecutionDetails simulationExecutionDetails = simulationExecutionDetailsMap.get(id);

        List<EntityInstanceManager> entityInstanceManagers = new ArrayList<>(simulationExecutionDetails.getEntityManager().values());
        for(EntityInstanceManager entityInstanceManager : entityInstanceManagers) {
            res.add(new EntityCountDTO(entityInstanceManager.getName(), entityInstanceManager.getPopulation()));
        }

        return res;
    }

    public SimulationDetailsDTO simulationDetailsDTO(Integer id) {
        if(simulationExecutionDetailsMap.get(id) != null) {
            SimulationExecutionDetails simulationExecutionDetails = simulationExecutionDetailsMap.get(id);
            TerminationDTO terminationDTO = new TerminationDTO(simulationExecutionDetails.getCurrTicks(),simulationExecutionDetails.getSeconds());
            return new SimulationDetailsDTO(getEntitiesCountDTO(id), terminationDTO, simulationExecutionDetails.getSimulationState().toString());
        }
        return null;
    }

    public void stopSimulation(StopDTO stopDTO) {
        SimulationExecutionDetails simulationExecutionDetails = simulationExecutionDetailsMap.get(stopDTO.getId());
        simulationExecutionDetails.setSimulationState(SimulationState.STOPPED);
    }

    private void stopAllSimulation() {
        List<SimulationExecutionDetails> simulationExecutionDetailsList = new ArrayList<>(simulationExecutionDetailsMap.values());
        for (SimulationExecutionDetails simulationExecutionDetails: simulationExecutionDetailsList) {
            if(simulationExecutionDetails.getSimulationState() == SimulationState.RUNNING || simulationExecutionDetails.getSimulationState() == SimulationState.PAUSED ) {
                simulationExecutionDetails.setSimulationState(SimulationState.STOPPED);
            }
        }
    }
    public StopCauseResDTO stopCause(StopCauseReqDTO id) {
        if(simulationExecutionDetailsMap.get(id.getId()) != null) {
            return new StopCauseResDTO(simulationExecutionDetailsMap.get(id.getId()).getSimulationState().toString());
        }
        return null;
    }

    public void pauseSimulation(PauseAndResumeSimulationDTO id){
        simulationExecutionDetailsMap.get(id.getId()).setRunning(false);
        simulationExecutionDetailsMap.get(id.getId()).setSimulationState(SimulationState.PAUSED);
    }

    public void resumeSimulation(PauseAndResumeSimulationDTO id){
        simulationExecutionDetailsMap.get(id.getId()).setRunning(true);
        synchronized (simulationExecutionDetailsMap.get(id.getId())) {
            simulationExecutionDetailsMap.get(id.getId()).notify();
        }
        simulationExecutionDetailsMap.get(id.getId()).setSimulationState(SimulationState.RUNNING);
    }

    public SimulationStateDTO getSimulationState(SimulationIDDTO simulationIDDTO) {
        return new SimulationStateDTO(simulationExecutionDetailsMap.get(simulationIDDTO.getId()).getSimulationState().toString());
    }
    public FailedCauseDTO getFailedCauseDTO(SimulationIDDTO simulationIDDTO) {
        return new FailedCauseDTO(simulationExecutionDetailsMap.get(simulationIDDTO.getId()).getFailCause());
    }

    public ConsistencyResDTO getConsistency(ConsistencyReqDTO consistencyReqDTO) {
        SimulationExecutionDetails simulationExecutionDetails = simulationExecutionDetailsMap.get(consistencyReqDTO.getId());
        EntityInstanceManager entityInstanceManager = simulationExecutionDetails.getEntityManager().get(consistencyReqDTO.getEntity());
        return new ConsistencyResDTO(entityInstanceManager.avgPropertyNotChanged(consistencyReqDTO.getProperty()));
    }

    public ConsistencyResDTO getPropAvg(ConsistencyReqDTO consistencyReqDTO) {
        SimulationExecutionDetails simulationExecutionDetails = simulationExecutionDetailsMap.get(consistencyReqDTO.getId());
        EntityInstanceManager entityInstanceManager = simulationExecutionDetails.getEntityManager().get(consistencyReqDTO.getEntity());
        return new ConsistencyResDTO(entityInstanceManager.avgPropertyValue(consistencyReqDTO.getProperty()));
    }

    public EntityCountResDTO getEntityCounters(EntityCountReqDTO entityCountReqDTO) {
        SimulationExecutionDetails simulationExecutionDetails = simulationExecutionDetailsMap.get(entityCountReqDTO.getId());
        EntityInstanceManager entityInstanceManager = simulationExecutionDetails.getEntityManager().get(entityCountReqDTO.getEntityName());
        return new EntityCountResDTO(entityInstanceManager.getPopulationHistory());
    }
    public QueueInfoDTO getQueueInfo() {

        return new QueueInfoDTO(executorService.getActiveCount(), executorService.getQueue().size(), simDoneCounter);
    }
    public IsNewSimLoadDTO isNewSimLoad() {
        IsNewSimLoadDTO res = new IsNewSimLoadDTO(setGetIsNewSimLoaded(""));
        if(setGetIsNewSimLoaded("")) {
            setGetIsNewSimLoaded("false");
        }
        return res;
    }
}
