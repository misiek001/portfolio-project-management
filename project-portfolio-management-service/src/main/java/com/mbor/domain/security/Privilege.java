package com.mbor.domain.security;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NaturalId
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege(final String name) {
        super();
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Privilege privilege = (Privilege) o;
        return name.equals(privilege.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
