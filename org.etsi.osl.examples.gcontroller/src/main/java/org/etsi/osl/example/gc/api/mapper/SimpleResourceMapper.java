package org.etsi.osl.example.gc.api.mapper;

import org.etsi.osl.tmf.ri639.model.Resource;
import org.etsi.osl.tmf.ri639.model.ResourceCreate;
import org.etsi.osl.tmf.ri639.model.ResourceUpdate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SimpleResourceMapper {

    ResourceUpdate resourceToResourceUpdate(Resource source);

    ResourceUpdate resourceCreateToResourceUpdate(ResourceCreate source);

}
