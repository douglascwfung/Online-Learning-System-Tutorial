package net.icestone.ols.model;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "ole_roles")
@Data
public class OleRole {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
    private String name;
    public Long getId() {
        return id;
    }
     
}