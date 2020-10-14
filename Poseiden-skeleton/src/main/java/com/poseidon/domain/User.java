package com.poseidon.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.poseidon.util.Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    private Long id = System.nanoTime();

    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    @Column(unique = true)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Pattern(regexp = Constants.PASSWORD_PATTERN, message = "The password must include a digit, an uppercase and lowercase letter and a special character, without space.")
    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    @Column(name = "password")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    @Column(name = "fullname")
    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    @Column(name = "role")
    @NotBlank(message = "Role is mandatory")
    private String role;

    public User(
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) @NotBlank(message = "Username is mandatory") final String usernameUser,
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*-])(?=\\S+$).{8,}$", message = "The password must include a digit, an uppercase and lowercase letter and a special character, without space.") @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) @NotBlank(message = "Password is mandatory") final String passwordUser,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) @NotBlank(message = "FullName is mandatory") final String fullnameUser,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) @NotBlank(message = "Role is mandatory") final String roleUser) {
        super();
        this.username = usernameUser;
        this.password = passwordUser;
        this.fullname = fullnameUser;
        this.role = roleUser;
    }

}
