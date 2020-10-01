package com.nnk.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Size(max = 125)
    @Column(unique = true)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*-])(?=\\S+$).{8,}$", message = "The password must include a digit, an uppercase and lowercase letter and a special character, without space.")
    @Size(max = 125)
    @Column(name = "password")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Size(max = 125)
    @Column(name = "fullname")
    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    @Size(max = 125)
    @Column(name = "role")
    @NotBlank(message = "Role is mandatory")
    private String role;

    public User(
            @Size(max = 125) @NotBlank(message = "Username is mandatory") String username,
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*-])(?=\\S+$).{8,}$", message = "The password must include a digit, an uppercase and lowercase letter and a special character, without space.") @Size(max = 125) @NotBlank(message = "Password is mandatory") String password,
            @Size(max = 125) @NotBlank(message = "FullName is mandatory") String fullname,
            @Size(max = 125) @NotBlank(message = "Role is mandatory") String role) {
        super();
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }

}
