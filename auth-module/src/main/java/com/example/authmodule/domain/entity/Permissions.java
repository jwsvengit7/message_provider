package com.example.authmodule.domain.entity;

import com.example.authmodule.domain.entity.enums.Permission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PERMISSION_TB")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name ="PERMISSION_TB" ,allocationSize = 1,sequenceName = "PERMISSION_TB")
    private Long id;
    @Enumerated(EnumType.STRING)
    private Permission permission;

    private String description;

    @Column(name = "permission_number")
    private Integer permissionNumber;

    @Override
    public String toString() {
        return "Permissions{" +
                "id=" + id +
                ", permission=" + permission +
                ", description='" + description + '\'' +
                ", permission_number=" + permissionNumber +
                '}';
    }
}
