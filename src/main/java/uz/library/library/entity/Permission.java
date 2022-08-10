package uz.library.library.entity;

public enum Permission {
    // user permissions
    C_RENTAL,D_USER,

    // admin permission
    C_BOOK,U_BOOK,D_BOOK,
    C_CATEGORY,U_CATEGORY,D_CATEGORY,
    C_LIBRARY,U_LIBRARY,D_LIBRARY,
    C_ROLE,R_ROLE,U_ROLE,D_ROLE,
    R_USER,

    //admin va user
    R_BOOK,
    R_CATEGORY,
    R_LIBRARY,
    R_RENTAL,U_RENTAL,D_RENTAL,
    U_USER
}
