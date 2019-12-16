package com.misiek.domain;

import com.misiek.domain.employeeinproject.IEmployee;
import com.misiek.domain.employeeinproject.ProjectRole;

import javax.persistence.*;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Employee implements IEmployee {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String firstName;

   private String lastName;

   @ManyToOne
   private BusinessUnit businessUnit;

   @OneToMany
   private Set<ProjectRole> projectRoleSet;

   private boolean isActive;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public BusinessUnit getBusinessUnit() {
      return businessUnit;
   }

   public void setBusinessUnit(BusinessUnit businessUnit) {
      this.businessUnit = businessUnit;
   }

   public Set<ProjectRole> getProjectRoleSet() {
      return projectRoleSet;
   }

   public void setProjectRoleSet(Set<ProjectRole> projectRoleSet) {
      this.projectRoleSet = projectRoleSet;
   }

   public boolean isActive() {
      return isActive;
   }

   public void setActive(boolean active) {
      isActive = active;
   }

   public void merge(Employee employee){
      if ( employee.getFirstName() != null){
         this.firstName = employee.getFirstName();
      }
      if (employee.getLastName() != null){
         this.lastName = employee.getLastName();
      }
      if(employee.getBusinessUnit() != null){
         this.businessUnit = employee.getBusinessUnit();
      }

   }
}
