package com.mbor.model;

public class ResourceManagerDTO extends ProjectRoleDTO<SupervisorDTO>{
    @Override
    public SupervisorDTO getEmployee() {
        return (SupervisorDTO) super.getEmployee();
    }
}
