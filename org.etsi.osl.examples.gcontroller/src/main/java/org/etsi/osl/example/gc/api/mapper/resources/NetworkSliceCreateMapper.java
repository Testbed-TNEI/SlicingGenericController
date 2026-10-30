package org.etsi.osl.example.gc.api.mapper.resources;

//import com.itav.slicemanager.stub.models.*;

import org.etsi.osl.example.gc.api.mapper.CharacteristicsMapper;
import org.etsi.osl.example.gc.api.mapper.CharacteristicsValueMapper;
import org.etsi.osl.tmf.common.model.EValueType;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCharacteristic;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCharacteristicValue;
import org.etsi.osl.tmf.ri639.model.Characteristic;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CharacteristicsMapper.class, CharacteristicsValueMapper.class})
public abstract class NetworkSliceCreateMapper {

    private static final Logger logger = LoggerFactory.getLogger(NetworkSliceCreateMapper.class);

    @Autowired
    protected CharacteristicsMapper characteristicsMapper;
    @Autowired
    protected CharacteristicsValueMapper characteristicsValueMapper;

    protected ResourceSpecificationCharacteristic createResourceSpecCharacteristic(String field, EValueType type, ResourceSpecificationCharacteristicValue value) {
        logger.info("createResourceSpecCharacteristic [field={}] value: {}", field, value);
        ResourceSpecificationCharacteristic ci = characteristicsMapper.toResourceSpecCharacteristic(
            field, "Parameter: " + field, type, true, ""
        );
        if (value != null) {
            ci.addResourceSpecCharacteristicValueItem(value);
        }
        return ci;
    }

    protected ResourceSpecificationCharacteristic createResourceSpecCharacteristic(String field, EValueType type, Set<ResourceSpecificationCharacteristicValue> values) {
        logger.info("createResourceSpecCharacteristic [field={}] values: {}", field, values);
        ResourceSpecificationCharacteristic ci = characteristicsMapper.toResourceSpecCharacteristic(
            field, "Parameter: " + field, type, true, ""
        );
        Set<ResourceSpecificationCharacteristicValue> filteredValues = values
            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        if (!filteredValues.isEmpty()) {
            ci.setResourceSpecificationCharacteristicValue(filteredValues);
        }
        return ci;
    }

//    public List<ResourceSpecificationCharacteristic> toResourceSpecCharacteristics(NetworkSliceCreate slice) {
//        return List.of(
//            createResourceSpecCharacteristic("id", EValueType.lONGINT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getId())),
//            createResourceSpecCharacteristic("name", EValueType.TEXT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getName())),
//            createResourceSpecCharacteristic("administrative_state", EValueType.ENUM, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getAdministrativeState())),
//            createResourceSpecCharacteristic("operational_state", EValueType.ENUM, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getOperationalState())),
//            createResourceSpecCharacteristic("coverage_area", EValueType.ARRAY, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getCoverageArea())),
//            createResourceSpecCharacteristic("sst", EValueType.INTEGER, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getSst())),
//            createResourceSpecCharacteristic("sd", EValueType.TEXT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getSd())),
//            createResourceSpecCharacteristic("dnn", EValueType.TEXT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDnn())),
//            createResourceSpecCharacteristic("prioritylabel", EValueType.INTEGER, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getPrioritylabel())),
//            createResourceSpecCharacteristic("reliability", EValueType.FLOAT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getReliability())),
//            createResourceSpecCharacteristic("dllatency", EValueType.INTEGER, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDllatency())),
//            createResourceSpecCharacteristic("ullatency", EValueType.INTEGER, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getUllatency())),
//            createResourceSpecCharacteristic("delaytolerance", EValueType.ENUM, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDelaytolerance())),
//            createResourceSpecCharacteristic("dldeterministiccomm", EValueType.ENUM, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDldeterministiccomm())),
//            createResourceSpecCharacteristic("uldeterministiccomm", EValueType.ENUM, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getUldeterministiccomm())),
//            createResourceSpecCharacteristic("dlguathptperue", EValueType.FLOAT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDlguathptperue())),
//            createResourceSpecCharacteristic("ulguathptperue", EValueType.FLOAT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getUlguathptperue())),
//            createResourceSpecCharacteristic("dlmaxthptperue", EValueType.FLOAT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDlmaxthptperue())),
//            createResourceSpecCharacteristic("ulmaxthptperue", EValueType.FLOAT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getUlmaxthptperue())),
//            createResourceSpecCharacteristic("plmnid", EValueType.INTEGER, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getPlmnid()))),
//            createResourceSpecCharacteristic("maxnumberofues", EValueType.INTEGER, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getMaxnumberofues()))),
//            createResourceSpecCharacteristic("dlguathptperslice", EValueType.FLOAT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getDlguathptperslice()))),
//            createResourceSpecCharacteristic("ulguathptperslice", EValueType.FLOAT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getUlguathptperslice()))),
//            createResourceSpecCharacteristic("dlmaxthptperslice", EValueType.FLOAT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getDlmaxthptperslice()))),
//            createResourceSpecCharacteristic("ulmaxthptperslice", EValueType.FLOAT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getUlmaxthptperslice()))),
//            createResourceSpecCharacteristic("dlmaxpktsize", EValueType.INTEGER, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getDlmaxpktsize()))),
//            createResourceSpecCharacteristic("ulmaxpktsize", EValueType.INTEGER, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getUlmaxpktsize()))),
//            createResourceSpecCharacteristic("maxnumberofpdusessions", EValueType.INTEGER, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getMaxnumberofpdusessions()))),
//            createResourceSpecCharacteristic("nroperatingbands", EValueType.TEXT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getNroperatingbands()))),
//            createResourceSpecCharacteristic("slicesimultaneoususe", EValueType.ENUM, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getSlicesimultaneoususe()))),
//            createResourceSpecCharacteristic("energyefficiency", EValueType.ENUM, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getEnergyefficiency()))),
//            createResourceSpecCharacteristic("termdensity", EValueType.INTEGER, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getTermdensity()))),
//            createResourceSpecCharacteristic("activityfactor", EValueType.FLOAT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getActivityfactor()))),
//            createResourceSpecCharacteristic("resourcesharinglevel", EValueType.ENUM, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getResourcesharinglevel()))),
//            createResourceSpecCharacteristic("uemobilitylevel", EValueType.ENUM, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getUemobilitylevel()))),
//            createResourceSpecCharacteristic("uespeed", EValueType.INTEGER, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getUespeed()))),
//            createResourceSpecCharacteristic("dldeterminperiodicity", EValueType.INTEGER, characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDldeterministiccomm())),
//            createResourceSpecCharacteristic("uldeterminperiodicity", EValueType.INTEGER, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getUldeterminperiodicity()))),
//            createResourceSpecCharacteristic("survivaltime", EValueType.FLOAT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getSurvivaltime()))),
//            createResourceSpecCharacteristic("positioningtype", EValueType.ENUM, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getPositioningtype()))),
//            createResourceSpecCharacteristic("positioningfrequency", EValueType.ENUM, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getPositioningfrequency()))),
//            createResourceSpecCharacteristic("positioningaccuracy", EValueType.FLOAT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getPositioningaccuracy()))),
//            createResourceSpecCharacteristic("synchronicitysupport", EValueType.ENUM, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getSynchronicitysupport()))),
//            createResourceSpecCharacteristic("synchronicityaccuracy", EValueType.FLOAT, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getSynchronicityaccuracy()))),
//            createResourceSpecCharacteristic("nssaasupport", EValueType.ENUM, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getNssaasupport()))),
//            createResourceSpecCharacteristic("n6protection", EValueType.ARRAY, characteristicsValueMapper.toResourceSpecCharacteristicsValue(characteristicsValueMapper.unwrapJsonNullable(slice.getN6protection())))
//        );
//    }

//    public NetworkSliceCreate fromResourceCharacteristics(List<Characteristic> characteristics) {
//        // Characteristic names are not unique in practice: OSL sends the specification defaults
//        // alongside values written back by this controller, and createResourceLogic appends the
//        // freshly mapped characteristics onto the ones that arrived. Keep the last occurrence —
//        // appends come after the originals, so that is the newest value. Without a merge function
//        // toMap throws IllegalStateException("Duplicate key ...") on any repeat.
//        Map<String, Characteristic> charMap = characteristics.stream()
//            .collect(Collectors.toMap(Characteristic::getName, c -> c, (first, last) -> last));
//
//        NetworkSliceCreate slice = new NetworkSliceCreate();
//        slice.setId(characteristicsValueMapper.fromCharacteristic(charMap.get("id")));
//        slice.setName(characteristicsValueMapper.fromCharacteristic(charMap.get("name")));
//        slice.setAdministrativeState(characteristicsValueMapper.fromCharacteristicToEnum(charMap.get("administrative_state"), AdministrativeState.class));
//        slice.setOperationalState(characteristicsValueMapper.fromCharacteristicToEnum(charMap.get("operational_state"), OperationalState.class));
//        slice.setCoverageArea(characteristicsValueMapper.fromCharacteristicToStringList(charMap.get("coverage_area")));
//        slice.setSst(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("sst")));
//        slice.setSd(characteristicsValueMapper.fromCharacteristic(charMap.get("sd")));
//        slice.setDnn(characteristicsValueMapper.fromCharacteristic(charMap.get("dnn")));
//        slice.setPrioritylabel(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("prioritylabel")));
//        slice.setReliability(characteristicsValueMapper.fromCharacteristicToBigDecimal(charMap.get("reliability")));
//        slice.setDllatency(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("dllatency")));
//        slice.setUllatency(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("ullatency")));
//        slice.setDelaytolerance(characteristicsValueMapper.fromCharacteristicToEnum(charMap.get("delaytolerance"), DelayTolerance.class));
//        slice.setDldeterministiccomm(characteristicsValueMapper.fromCharacteristicToEnum(charMap.get("dldeterministiccomm"), DeterministicCommAvailability.class));
//        slice.setUldeterministiccomm(characteristicsValueMapper.fromCharacteristicToEnum(charMap.get("uldeterministiccomm"), DeterministicCommAvailability.class));
//        slice.setDlguathptperue(characteristicsValueMapper.fromCharacteristicToBigDecimal(charMap.get("dlguathptperue")));
//        slice.setUlguathptperue(characteristicsValueMapper.fromCharacteristicToBigDecimal(charMap.get("ulguathptperue")));
//        slice.setDlmaxthptperue(characteristicsValueMapper.fromCharacteristicToBigDecimal(charMap.get("dlmaxthptperue")));
//        slice.setUlmaxthptperue(characteristicsValueMapper.fromCharacteristicToBigDecimal(charMap.get("ulmaxthptperue")));
//        List<N6Protection> n6List = characteristicsValueMapper.fromCharacteristicToN6ProtectionList(charMap.get("n6protection"));
//        slice.setN6protection(n6List != null ? JsonNullable.of(n6List) : JsonNullable.undefined());
//        return slice;
//    }
//
//    public List<Characteristic> toResourceCharacteristics(NetworkSliceCreate slice) {
//        List<N6Protection> n6List = characteristicsValueMapper.unwrapJsonNullable(slice.getN6protection());
//        return List.of(
//            characteristicsValueMapper.toCharacteristic("id", slice.getId()),
//            characteristicsValueMapper.toCharacteristic("name", slice.getName()),
//            characteristicsValueMapper.toCharacteristic("administrative_state", slice.getAdministrativeState()),
//            characteristicsValueMapper.toCharacteristic("operational_state", slice.getOperationalState()),
//            characteristicsValueMapper.toCharacteristicStringList("coverage_area", slice.getCoverageArea()),
//            characteristicsValueMapper.toCharacteristic("sst", slice.getSst()),
//            characteristicsValueMapper.toCharacteristic("sd", slice.getSd()),
//            characteristicsValueMapper.toCharacteristic("dnn", slice.getDnn()),
//            characteristicsValueMapper.toCharacteristic("prioritylabel", slice.getPrioritylabel()),
//            characteristicsValueMapper.toCharacteristic("reliability", slice.getReliability()),
//            characteristicsValueMapper.toCharacteristic("dllatency", slice.getDllatency()),
//            characteristicsValueMapper.toCharacteristic("ullatency", slice.getUllatency()),
//            characteristicsValueMapper.toCharacteristic("delaytolerance", slice.getDelaytolerance()),
//            characteristicsValueMapper.toCharacteristic("dldeterministiccomm", slice.getDldeterministiccomm()),
//            characteristicsValueMapper.toCharacteristic("uldeterministiccomm", slice.getUldeterministiccomm()),
//            characteristicsValueMapper.toCharacteristic("dlguathptperue", slice.getDlguathptperue()),
//            characteristicsValueMapper.toCharacteristic("ulguathptperue", slice.getUlguathptperue()),
//            characteristicsValueMapper.toCharacteristic("dlmaxthptperue", slice.getDlmaxthptperue()),
//            characteristicsValueMapper.toCharacteristic("ulmaxthptperue", slice.getUlmaxthptperue()),
//            characteristicsValueMapper.toCharacteristicN6ProtectionList("n6protection", n6List)
//        );
//    }

}
