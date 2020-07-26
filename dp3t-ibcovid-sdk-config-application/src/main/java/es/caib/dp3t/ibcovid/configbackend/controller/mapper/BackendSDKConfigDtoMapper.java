package es.caib.dp3t.ibcovid.configbackend.controller.mapper;

import es.caib.dp3t.ibcovid.configbackend.controller.model.backendsdkconfig.BackendSDKConfigDto;
import es.caib.dp3t.ibcovid.configbackend.service.model.sdkbackendconfig.BackendSDKConfigSrvDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BackendSDKConfigDtoMapper {

    BackendSDKConfigDtoMapper INSTANCE = Mappers.getMapper(BackendSDKConfigDtoMapper.class);

    BackendSDKConfigSrvDto dtoToSrvDto(final BackendSDKConfigDto request);

    BackendSDKConfigDto srvDtoToDto(final BackendSDKConfigSrvDto srvDto);

}
