package com.example.authmodule.domain.entity;

import com.example.authmodule.domain.entity.enums.Permission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "ROLE_TB")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name ="ROLE_TB" ,allocationSize =  1,sequenceName = "ROLE_TB")
    private Long id;
    @Column(name = "role_name",nullable = false)
    private String roleName;
    @Column(name = "date_added",nullable = false)
    @DateTimeFormat(pattern = "mm/dd/yy")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Permission permission;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id",name = "customer_id")
    private Customer customer;

    @OneToMany
    private Set<Permissions> permissions;


    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", date=" + date +
                ", permission=" + permission +
                ", customer=" + customer +
                ", permissions=" + permissions +
                '}';
    }
}
