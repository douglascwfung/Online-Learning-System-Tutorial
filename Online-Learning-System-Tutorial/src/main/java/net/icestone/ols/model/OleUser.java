package net.icestone.ols.model;

import java.util.*;

import javax.persistence.*;

import lombok.Data;
 
@Entity
@Table(name = "ole_users")
@Data
public class OleUser {
 
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String username;
    private String password;
    private boolean enabled;
     
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ole_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
            )
    private Set<OleRole> roles = new HashSet<>();
 
    public Long getId() {
        return id;
    }
 
}