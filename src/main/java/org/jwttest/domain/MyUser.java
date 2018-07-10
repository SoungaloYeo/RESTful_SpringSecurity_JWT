package org.jwttest.domain;

import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.ManyToMany;

// import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUser {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String username;
    // @JsonIgnore
    private String password;
    
    @ManyToMany(fetch=FetchType.EAGER)
    private Collection<MyRole> roles = new LinkedList<>();
}