package com.misiek.model;

public class SupervisorDTO extends EmployeeDTO {

    private DirectorDTO directorDTO;

    public DirectorDTO getDirectorDTO() {
        return directorDTO;
    }

    public void setDirectorDTO(DirectorDTO directorDTO) {
        this.directorDTO = directorDTO;
    }
}
