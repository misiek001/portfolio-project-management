package com.misiek.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee {

   private Long id;

   private String firstName;

   private String LastName;

   private BusinessUnit businessUnit;

   private Employee supervisor;

   private boolean isOverload;

}
