package fr.gopartner.tregusto.common.exception.core;

public final class CustomErrorCodes {

    private CustomErrorCodes() {
    }

    // Common codes
    public static final class Common {
        private Common() {
        }

        public static final int SOMETHING_WENT_WRONG = 1000;
        public static final int RESOURCE_NOT_FOUND = 1001;
        public static final int RESOURCE_ALREADY_EXISTS = 1002;
        public static final int INVALID_ARGUMENTS = 1003;
        public static final int INVALID_CAPTCHA = 1004;
    }

    // Account module codes
    public static final class Account {
        private Account() {
        }

        public static final int ACCOUNT_ALREADY_EXISTS = 2000;
        public static final int INVALID_OTP_CODE = 2001;
        public static final int ASSIGN_USER_TO_GROUP = 2002;
    }

}