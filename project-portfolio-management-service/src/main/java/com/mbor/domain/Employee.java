package com.mbor.domain;

import com.mbor.domain.employeeinproject.IEmployee;
import com.mbor.domain.employeeinproject.ProjectRole;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Employee implements IEmployee {


   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String firstName;

   private String lastName;

   @ManyToOne
   @JoinColumn(name = "business_unit_id")
   private BusinessUnit businessUnit;

   @OneToMany(cascade  ={
           CascadeType.PERSIST,
           CascadeType.MERGE
   })
   @Fetch(FetchMode.JOIN)
   private Set<ProjectRole> projectRoleSet = new HashSet<>();

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

   public void addProjectRole(ProjectRole projectRole){
      projectRoleSet.add(projectRole);
      projectRole.setEmployee(this);
   }

   public void removeProjectRole(ProjectRole projectRole){
      projectRoleSet.remove(projectRole);
      projectRole.setEmployee(null);
   }



}
