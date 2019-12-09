package com.misiek.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Employee")
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
public class Employee {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String firstName;

   private String LastName;

   @ManyToOne
   private BusinessUnit businessUnit;

   @ManyToOne
   private Employee supervisor;

}
