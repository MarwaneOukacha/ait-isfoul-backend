package com.aitIsfoul.hotel.config;

import com.aitIsfoul.hotel.entity.Permission;
import com.aitIsfoul.hotel.entity.Role;
import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.enums.PermissionType;
import com.aitIsfoul.hotel.enums.RoleType;
import com.aitIsfoul.hotel.enums.UserStatus;
import com.aitIsfoul.hotel.enums.UserType;
import com.aitIsfoul.hotel.repository.PermissionRepository;
import com.aitIsfoul.hotel.repository.RoleRepository;
import com.aitIsfoul.hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final  PermissionRepository permissionRepository;
    private final PasswordEncoder encoder;

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            log.info("Initializing database with roles, permissions, and users...");

            // Create roles if not exists
            List<String> roleNames = List.of("ADMIN", "MANAGER", "USER");
            for (String roleName : roleNames) {
                if (roleRepository.findByRoleName(RoleType.valueOf(roleName)).isEmpty()) {
                    Role role = new Role();
                    role.setRoleName(RoleType.valueOf(roleName));
                    role.setIsActive("Y");
                    roleRepository.save(role);
                    log.info("Role '{}' added to the database.", roleName);
                    // Add permissions to role
                    addPermissionsToRole(role);
                }
            }

            // Create users if not exists
            if (userRepository.count() == 0) {
                Role adminRole = roleRepository.findByRoleName(RoleType.ADMIN).orElseThrow();
                Role userRole = roleRepository.findByRoleName(RoleType.USER).orElseThrow();

                User admin = createUser("MARWANE", "OUKACHA", "123456789", "marwaneoukacha2001@gmail.com", adminRole, UserType.SYS,"PB267544");
                User admin2 = createUser("Kamal", "aitisfoul", "987654321", "oukacha@gmail.com", adminRole, UserType.SYS,"MK267544");

                userRepository.saveAll(List.of(admin, admin2));
                log.info("Users added to the database.");
            }

            log.info("Database initialization complete!");
        };
    }

    private User createUser(String firstName, String lastName, String phone, String email, Role role, UserType userType,String iden) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phone);
        user.setEmail(email);
        user.setIsActive("Y");
        user.setRole(role);
        user.setType(userType);
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        user.setIden(iden);
        user.setStatus(UserStatus.ACTIVE);
        user.setPassword(encoder.encode(iden));
        return user;
    }

    private void addPermissionsToRole(Role role) {
        // List of permissions for each role
        List<PermissionType> permissions = getPermissionsForRole(role.getRoleName());

        for (PermissionType permissionType : permissions) {
            Permission permission = new Permission();
            permission.setPermissionName(permissionType);
            permission.setIsPossible("Y");
            permission.setRole(role);

            permissionRepository.save(permission);
            log.info("Permission '{}' added to role '{}'", permissionType, role.getRoleName());
        }
    }

    private List<PermissionType> getPermissionsForRole(RoleType roleType) {
        switch (roleType) {
            case ADMIN:
                return List.of(
                        PermissionType.CAN_CREATE_CUSTOMER,
                        PermissionType.CAN_UPDATE_CUSTOMER,
                        PermissionType.CAN_DELETE_CUSTOMER,
                        PermissionType.CAN_VIEW_CUSTOMER,
                        PermissionType.CAN_CREATE_USER,
                        PermissionType.CAN_UPDATE_USER,
                        PermissionType.CAN_DELETE_USER,
                        PermissionType.CAN_VIEW_USER,
                        PermissionType.CAN_ASSIGN_ROLE,
                        PermissionType.CAN_MANAGE_PERMISSIONS,
                        PermissionType.CAN_PROCESS_PAYMENTS,
                        PermissionType.CAN_VIEW_TRANSACTIONS,
                        PermissionType.CAN_REFUND_PAYMENT,
                        PermissionType.CAN_VIEW_REPORTS,
                        PermissionType.CAN_GENERATE_REPORTS,
                        PermissionType.CAN_EXPORT_REPORTS
                );
            case MANAGER:
                return List.of(
                        PermissionType.CAN_VIEW_CUSTOMER,
                        PermissionType.CAN_CREATE_USER,
                        PermissionType.CAN_UPDATE_USER,
                        PermissionType.CAN_VIEW_USER,
                        PermissionType.CAN_PROCESS_PAYMENTS,
                        PermissionType.CAN_VIEW_TRANSACTIONS,
                        PermissionType.CAN_VIEW_REPORTS
                );
            case USER:
                return List.of(
                        PermissionType.CAN_VIEW_CUSTOMER,
                        PermissionType.CAN_VIEW_REPORTS
                );
            default:
                return List.of();
        }
    }




}