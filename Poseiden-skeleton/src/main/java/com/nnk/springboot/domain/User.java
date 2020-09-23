package com.nnk.springboot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // @Column(name = "id")
    private Integer id;

    // @Column(name = "username")
    @NotBlank(message = "Username is mandatory")
    private String username;

    // @Column(name = "password")
    @NotBlank(message = "Password is mandatory")
    private String password;

    // @Column(name = "fullname")
    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    // @Column(name = "role")
    @NotBlank(message = "Role is mandatory")
    private String role;

}
