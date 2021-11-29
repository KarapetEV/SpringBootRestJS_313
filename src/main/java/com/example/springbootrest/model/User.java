package com.example.springbootrest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Column(name = "email")
    private String email;

    public User(String password, String firstName, String lastName, int age, String email) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    public User(String password, String firstName, String lastName, int age, String email, Set<Role> roles) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.roles = roles;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private Set<Role> roles = new HashSet<>();

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return firstName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String rolesToString() {
        Set<Role> rolesSet = new TreeSet<Role>(Comparator.comparing(Role::getRole));
        rolesSet.addAll(getRoles());
        StringBuilder roles = new StringBuilder();
        for (Role role : rolesSet) {
            roles.append(role.getRole().replace("ROLE_", "")).append(" ");
        }
        return roles.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && password.equals(user.password) && firstName.equals(user.firstName) && lastName.equals(user.lastName) && email.equals(user.email) && roles.equals(user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, firstName, lastName, age, email, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", First name='" + firstName + '\'' +
                ", Last name='" + lastName + '\'' +
                ", age='" + age + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
