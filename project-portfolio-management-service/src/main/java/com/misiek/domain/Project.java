package com.misiek.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "Project")
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
public class Project {

    private Long Id;

    private String name;

    private Employee projectManager;

    private Employee solutionArchitect;

    private Employee resourceManager;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    private LocalDateTime startDate;

    private LocalDateTime plannedEndDate;

    private LocalDateTime realEndDate;

    private Employee businessUnitLeader;

    @ManyToMany(cascade  ={
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "business_unit_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "business_unit_id"))
    private Set<BusinessUnit> businessUnits;

    private void addBusinessUnit(BusinessUnit businessUnit){
        businessUnits.add(businessUnit);
        businessUnit.getProjects().add(this);
    }

    private void removeBusinessUnit(BusinessUnit businessUnit){
        businessUnits.remove(businessUnit);
        businessUnit.getProjects().remove(this);
    }

}
