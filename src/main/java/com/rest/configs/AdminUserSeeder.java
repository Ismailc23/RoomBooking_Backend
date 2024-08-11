package com.rest.configs;

import com.rest.services.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminUserSeeder implements CommandLineRunner {

    @Autowired
    private AdminUserService adminUserService;

    @Override
    public void run(String... args) throws Exception {
        adminUserService.createAdminUser("Admin User", "admin@example.com", "Admin@123");
    }
}
