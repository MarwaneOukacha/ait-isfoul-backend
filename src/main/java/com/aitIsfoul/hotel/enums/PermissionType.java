package com.aitIsfoul.hotel.enums;

public enum PermissionType {
    // Permissions for customer management
    CAN_CREATE_CUSTOMER, // Create a customer
    CAN_UPDATE_CUSTOMER, // Update a customer
    CAN_DELETE_CUSTOMER, // Delete a customer
    CAN_VIEW_CUSTOMER, // View a customer

    // Permissions for user management
    CAN_CREATE_USER, // Create a user
    CAN_UPDATE_USER, // Update a user
    CAN_DELETE_USER, // Delete a user
    CAN_VIEW_USER, // View users

    // Permissions for role and permission management
    CAN_ASSIGN_ROLE, // Assign roles to users
    CAN_MANAGE_PERMISSIONS, // Manage user permissions



    // Permissions for payment processing
    CAN_PROCESS_PAYMENTS, // Process payment
    CAN_VIEW_TRANSACTIONS, // View transactions
    CAN_REFUND_PAYMENT, // Refund a payment

    // Permissions for report generation
    CAN_VIEW_REPORTS, // View reports
    CAN_GENERATE_REPORTS, // Generate reports
    CAN_EXPORT_REPORTS // Export reports
}
