package com.ilmazidan.expense_tracker.constant;

public class Constant {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final String ROLE = "role";
    // Table
    public static final String USER_TABLE = "m_user_account";
    public static final String PAYMENT_METHOD_TABLE = "m_payment_method";
    public static final String EXPENSE_PAYMENT = "t_expense_payment";
    public static final String EXPENSE_TABLE = "m_expense";
    public static final String CATEGORY_TABLE = "m_category";

    // API
    public static final String API = "/api";
    public static final String AUTH_API = API + "/auth";
    public static final String USERS_API = API + "/users";
    public static final String CATEGORIES_API = API + "/categories";
    public static final String EXPENSES_API = API + "/expenses";
    public static final String EXPENSE_PAYMENTS_API = API + "/expense-payments";
    public static final String PAYMENT_METHODS_API = API + "/payment-methods";

    // Auth Controller
    public static final String SUCCESS_LOGIN = "Successfully logged in";
    public static final String SUCCESS_REGISTER = "Successfully registered";
    public static final String SUCCESS_LOGOUT = "Successfully logged out";
    public static final String SUCCESS_REFRESH_TOKEN = "Successfully refreshed token";
    public static final String REQUIRE_REFRESH_TOKEN = "Require refresh token";
    public static final String REFRESH_TOKEN = "refreshToken";
    // Auth Service
    public static final String ERROR_ROLE_NOT_FOUND = "Error role is not found";
    public static final String ERROR_INVALID_ROLE = "Invalid role";

    // User Controller
    public static final String SUCCESS_CREATE_USER = "Successfully create user";
    public static final String SUCCESS_UPDATE_USER_PASSWORD = "Successfully update password";
    public static final String SUCCESS_GET_CURRENT_USER_INFO = "Successfully retrieve current user info";
    // User Service
    public static final String ERROR_USER_NOT_FOUND = "User does not exist";
    public static final String ERROR_USER_ALREADY_EXISTS = "User already exists";
    public static final String ERROR_INVALID_CREDENTIALS = "Invalid credentials";

    // Category Controller
    public static final String SUCCESS_RETRIEVE_ALL_CATEGORIES = "Successfully retrieved all categories";
    public static final String SUCCESS_RETRiEVE_CATEGORY = "Success retrieve category";
    public static final String SUCCESS_CREATE_CATEGORY = "Successfully create category";
    public static final String SUCCESS_UPDATE_CATEGORY = "Successfully update category";
    public static final String SUCCESS_DELETE_CATEGORY = "Successfully delete category";// Category Controller
    // Category Service
    public static final String ERROR_CATEGORY_NOT_FOUND = "Category is not found";

    // ExpensePayment Controller
    public static final String SUCCESS_RETRIEVE_ALL_EXPENSE_PAYMENTS = "Successfully retrieved all expense payments";
    public static final String SUCCESS_RETRiEVE_EXPENSE_PAYMENT = "Success retrieve expense payment";
    public static final String SUCCESS_CREATE_EXPENSE_PAYMENT = "Successfully create expense payment";
    public static final String SUCCESS_UPDATE_EXPENSE_PAYMENT = "Successfully update expense payment";
    public static final String SUCCESS_DELETE_EXPENSE_PAYMENT = "Successfully delete expense payment";
    // ExpensePayment Service
    public static final String ERROR_EXPENSE_PAYMENT_NOT_FOUND = "Expense Payment is not found";

    // Expense Controller
    public static final String SUCCESS_RETRIEVE_ALL_EXPENSES = "Successfully retrieved all expenses";
    public static final String SUCCESS_RETRiEVE_EXPENSE = "Success retrieve expense";
    public static final String SUCCESS_CREATE_EXPENSE = "Successfully create expense";
    public static final String SUCCESS_UPDATE_EXPENSE = "Successfully update expense";
    public static final String SUCCESS_DELETE_EXPENSE = "Successfully delete expense";
    // Expense Service
    public static final String ERROR_EXPENSE_NOT_FOUND = "Expense is not found";

    // JWT Service
    public static final String ERROR_CREATING_JWT_TOKEN = "Error creating JWT token";
    public static final String ERROR_INVALID_JWT_TOKEN = "Invalid JWT token";

    // Payment Method Controller
    public static final String SUCCESS_RETRIEVE_ALL_PAYMENT_METHODS = "Successfully retrieved all payment methods";
    public static final String SUCCESS_RETRiEVE_PAYMENT_METHOD = "Success retrieve payment method";
    public static final String SUCCESS_CREATE_PAYMENT_METHOD = "Successfully create payment method";
    public static final String SUCCESS_UPDATE_PAYMENT_METHOD = "Successfully update payment method";
    public static final String SUCCESS_DELETE_PAYMENT_METHOD = "Successfully delete payment method";
    // Payment Method Service
    public static final String ERROR_PAYMENT_METHOD_NOT_FOUND = "Error payment method is not found";
}

