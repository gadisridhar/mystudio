/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yy.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author yyi
 */

@Entity
@Table(name="person")
@NamedQuery(name="Person.findByLastName",
    query="select p from Person p where p.lastName=:lastName")
public class Person extends BaseEntity<Long> {

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(name="first_name", length=50)
    private String firstName;
    @Column(name="last_name", length=50)
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if ((this.firstName == null) ? (other.firstName != null) : !this.firstName.equals(other.firstName)) {
            return false;
        }
        if ((this.lastName == null) ? (other.lastName != null) : !this.lastName.equals(other.lastName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.firstName != null ? this.firstName.hashCode() : 0);
        hash = 17 * hash + (this.lastName != null ? this.lastName.hashCode() : 0);
        return hash;
    }


}
