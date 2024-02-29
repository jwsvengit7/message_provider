package com.example.authmodule.seeders;

import com.example.authmodule.domain.entity.Customer;
import com.example.authmodule.domain.entity.Permissions;
import com.example.authmodule.domain.entity.enums.Permission;
import com.example.authmodule.domain.entity.Role;
import com.example.authmodule.domain.repository.CustomerRepository;
import com.example.authmodule.domain.repository.PermissionRepository;
import com.example.authmodule.domain.repository.RoleRepository;
import com.sms.smscommonsmodule.constant.Registeration_Type;
import com.sms.smscommonsmodule.constant.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

@Component
@Slf4j
public class Seeders implements CommandLineRunner {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {

            Arrays.stream(Permission.values())
                    .forEach(permission -> {

                        Permissions entity = new Permissions();
                        entity.setPermission(permission);
                        entity.setDescription(permission.getDescription());
                        entity.setPermissionNumber(permission.ordinal());
                        permissionsRepository.save(entity);

                    });
        Permissions permissions = permissionsRepository.findPermissionsByPermission(Permission.USER_CREATE_USER).get();
        Permissions permissions2 = permissionsRepository.findPermissionsByPermission(Permission.USER_CAN_BLOCK_USER).get();

        Role role = new Role();
        role.setRoleName(Roles.MANAGER.name());
        role.setDate(Date.from(Instant.now()));
        role.setPermission(permissions.getPermission());
        role.setPermissions(Set.of(permissions,permissions2));

        Role saveRole = roleRepository.save(role);
        Customer customer = new Customer();

        customer.setEmail("admin@gmail.com");
        customer.setPassword(passwordEncoder.encode("12345"));
        customer.setFullname("SMS READER");
        customer.setRole(Set.of(role));
        customer.setRoles(Roles.MANAGER);
        customer.setType(Registeration_Type.REGISTERATION_TYPE);
        customer.setStatus(true);
        customerRepository.save(customer);
        saveRole.setCustomer(customer);
        roleRepository.save(saveRole);
        log.info("role {}", customer.getRole());

//

    }
}
