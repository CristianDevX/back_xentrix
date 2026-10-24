package com.riotstak.back_xentrix.api.v1.local.utils;

public class UtilsService {

    public static boolean isErrorService(Object object) {
        String typeData = (object != null) ? object.getClass().getSimpleName() : "";
        if (typeData.equals("ErrorService"))
            return true;
        return false;
    }

    public static boolean isErrorServiceOtp(Object object) {
        String typeData = (object != null) ? object.getClass().getSimpleName() : "";
        if (typeData.equals("ErrorServiceOtp"))
            return true;
        return false;
    }

}