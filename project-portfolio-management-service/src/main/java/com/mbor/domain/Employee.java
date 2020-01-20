package com.mbor.domain;

import com.mbor.domain.employeeinproject.IEmployee;
import com.mbor.domain.employeeinproject.ProjectRole;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Employee implements IEmployee {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column( nullable = false, unique = true)
   private Long id;

   private String firstName;

   private String lastName;

   @NaturalId
   private String userName;

   @ManyToOne
   @JoinColumn(name = "business_unit_id")
   private BusinessUnit businessUnit;

   @OneToMany( mappedBy = "employee")
   @Fetch(FetchMode.JOIN)
   @Cascade(org.hibernate.annotations.CascadeType.ALL)
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

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Employee employee = (Employee) o;
      return userName.equals(employee.userName);
   }

   @Override
   public int hashCode() {
      return Objects.hash(userName);
   }
}
