package com.mbor.domain;

import com.mbor.domain.employeeinproject.IEmployee;
import com.mbor.domain.employeeinproject.ProjectRole;
import com.mbor.domain.security.User;
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

   @OneToMany( mappedBy = "employee", cascade = {CascadeType.MERGE, CascadeType.PERSIST} )
   @Fetch(FetchMode.JOIN)
   private Set<ProjectRole> projectRoleSet = new HashSet<>();

   @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
   private User user;

   @Override
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   @Override
   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   @Override
   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   @Override
   public BusinessUnit getBusinessUnit() {
      return businessUnit;
   }

   public void setBusinessUnit(BusinessUnit businessUnit) {
      this.businessUnit = businessUnit;
   }

   @Override
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

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
      user.setEmployee(this);
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
