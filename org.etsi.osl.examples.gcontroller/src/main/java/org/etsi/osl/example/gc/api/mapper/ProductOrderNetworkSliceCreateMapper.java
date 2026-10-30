package org.etsi.osl.example.gc.api.mapper;

//import com.itav.slicemanager.stub.models.NetworkSliceCreate;
//import com.itav.slicemanager.stub.models.ProductOrder;
import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingTarget;

// TODO targets the old Slice Management API Mock models, no longer on the classpath. Commented out
//  pending the migration to the new Slice Manager API.
@Mapper(componentModel = "spring", uses = {JsonNullableMapper.class})
public interface ProductOrderNetworkSliceCreateMapper {

//    @Mapping(target = "dnn", source = "DNN")
//    @Mapping(target = "n6protection", source = "n6protections")
//    NetworkSliceCreate productOrderToNetworkSliceCreate(ProductOrder source);
//
//    @Mapping(target = "dnn", source = "DNN")
//    @Mapping(target = "n6protection", source = "n6protections")
//    void updateNetworkSliceCreate(ProductOrder source, @MappingTarget NetworkSliceCreate target);

}