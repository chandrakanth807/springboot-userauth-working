package com.razorthink.personalbrain.constants;

public final class Constant {

    public static final String IP_ADDRESS_LOCAL_LOOPBACK = "127.0.0.1";
    public static final String IP_ADDRESS_BIND_ALL = "0.0.0.0";
    public static final String CONFIG_FILE_NAME = "application.properties";

    private Constant()
    {

    }

    public class Webapp {

        public static final String ERROR_FETCHING_CURRENT_USER = "error.fetching.current.user";

        private Webapp()
        {

        }

        public class ErrorCode {

            public static final int INTERNAL_SERVER_ERROR = 500;
            public static final int BAD_REQUEST = 400;

            private ErrorCode()
            {

            }
        }

        public class Auth {

            public static final String DEFAULT_TOKEN = "123456";
            public static final String DEFAULT_USER = "admin";

            private Auth()
            {

            }
        }
    }

}
