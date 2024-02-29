package com.example.authmodule.domain.repository;

import com.example.authmodule.domain.entity.Permissions;
import com.example.authmodule.domain.entity.enums.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface PermissionRepository extends JpaRepository<Permissions,Long> {
    Optional<Permissions> findPermissionsByPermission(Permission permission);
}
