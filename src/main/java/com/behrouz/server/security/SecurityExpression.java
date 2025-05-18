package com.behrouz.server.security;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

/**
 * created by: Hapi
 * company: mobin
 * package: com.behrouz.server.security
 * project name:  ximaServer
 * 11 July 2018
 **/



public class SecurityExpression extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {


    //region Methods can used in @PreAuthorize

    private Object filterObject;
    private Object returnObject;

    //endregion
    private Object target;
    private String packageName;
//    private OperatorPackageRoleRepository operatorPackageRoleRepository;



    SecurityExpression(Authentication a) {
        super(a);
    }

//
//    public boolean isAdmin() {
//        return checkAccess( Role.ADMIN);
//    }
//    public  boolean isSuperAdmin()
//    {
//        return checkAccess(Role.SuperAdmin);
//    }
//    public boolean isUse() {
//        return checkAccess(Role.USER);
//    }
//
//    public boolean isView() {
//        return checkAccess(Role.VIEW);
//    }
//
//    public boolean isAdmin(String packageName) {
//        this.packageName = packageName;
//        boolean success = checkAccess(Role.ADMIN, packageName);
//        System.out.println(success ? "isAdmin" : "notAdmin");
//        return success;
//    }
//
//    public boolean isUser(String packageName) {
//        this.packageName = packageName;
//        boolean success = checkAccess(Role.USER, packageName);
//        System.out.println(success ? "isUser" : "notUser");
//        return success;
//    }
//    public boolean isViewer(String packageName) {
//        this.packageName = packageName;
//        boolean success = checkAccess(Role.VIEW, packageName);
//        System.out.println(success ? "isView" : "notView");
//        return success;
//    }

//    private boolean checkAccess(int roleId) {
//        return checkAccess(roleId, packageName);
//    }
//
//    private boolean checkAccess(int roleId, String packageName) {
//        System.out.println(Auth.getId());
//        PackageRoleEntity entity = packageRoleRepository.getFirstByUserIdAndPack_PackageRouteAndRole_IdAndDeletedIsFalse(Auth.getId(), packageName, roleId);
//        return entity != null;
//    }


//    public OperatorPackageRoleRepository getOperatorPackageRoleRepository() {
//        return operatorPackageRoleRepository;
//    }
//    public void setOperatorPackageRoleRepository(OperatorPackageRoleRepository operatorPackageRoleRepository) {
//        this.operatorPackageRoleRepository = operatorPackageRoleRepository;
//    }




    public Object getFilterObject() {
        return filterObject;
    }
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }



    public Object getReturnObject() {
        return returnObject;
    }
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }




    private String detectMethodMapping(Method method) {
        if (method.getAnnotation(GetMapping.class) != null) {
            return method.getAnnotation(GetMapping.class).value()[0];
        }

        if (method.getAnnotation(PostMapping.class) != null) {
            return method.getAnnotation(PostMapping.class).value()[0];
        }

        if (method.getAnnotation(RequestMapping.class) != null) {
            return method.getAnnotation(RequestMapping.class).value()[0];
        }
        return "";
    }

    private String detectControllerMapping(Class<?> controller) {
        if (controller.getAnnotation(GetMapping.class) != null) {
            return controller.getAnnotation(GetMapping.class).value()[0];
        }

        if (controller.getAnnotation(PostMapping.class) != null) {
            return controller.getAnnotation(PostMapping.class).value()[0];
        }

        if (controller.getAnnotation(RequestMapping.class) != null) {
            return controller.getAnnotation(RequestMapping.class).value()[0];
        }
        return "";
    }




    public Object getThis() {
        return target;
    }

    void setThis(MethodInvocation target) {
        String url = detectControllerMapping(target.getThis().getClass());
        url += detectMethodMapping(target.getMethod());

        if (!url.isEmpty()) {
            url = url.replaceFirst("/admin", "");
            packageName = url.split("/")[1];
        }
        this.target = target;
    }


}

