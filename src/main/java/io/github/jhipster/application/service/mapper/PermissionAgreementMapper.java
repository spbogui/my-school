package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.PermissionAgreementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PermissionAgreement and its DTO PermissionAgreementDTO.
 */
@Mapper(componentModel = "spring", uses = {AskingPermissionMapper.class})
public interface PermissionAgreementMapper extends EntityMapper<PermissionAgreementDTO, PermissionAgreement> {

    @Mapping(source = "askingPermission.id", target = "askingPermissionId")
    PermissionAgreementDTO toDto(PermissionAgreement permissionAgreement);

    @Mapping(source = "askingPermissionId", target = "askingPermission")
    PermissionAgreement toEntity(PermissionAgreementDTO permissionAgreementDTO);

    default PermissionAgreement fromId(Long id) {
        if (id == null) {
            return null;
        }
        PermissionAgreement permissionAgreement = new PermissionAgreement();
        permissionAgreement.setId(id);
        return permissionAgreement;
    }
}
