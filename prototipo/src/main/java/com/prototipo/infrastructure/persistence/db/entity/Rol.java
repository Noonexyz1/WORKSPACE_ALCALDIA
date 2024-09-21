package com.prototipo.infrastructure.persistence.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String nombreRol;

    @OneToMany(mappedBy = "fkRol")
    private List<Usuario> listRoles;

    @OneToMany(mappedBy = "fkRol")
    private List<DashboardConfig> listDashConfig;
}
