package club.xubowei.epframework.constant;

/**
 * @author kpgoing
 * @date 2018/8/26.
 */
public final class WebConstant {

    public static final String URL_SPILT_SEPARATOR = "/";

    /**
     * Use for the http status.
     */
    public enum HttpStatus {
        /**
         * success
         */
        SUCCESS("200", "success");


        private String status;
        private String description;

        HttpStatus(String status, String description) {
            this.status = status;
            this.description = description;
        }
    }

    public static final class ContentType {
        public static final String APPLICATION_JSON = "application/json";
    }

    public static final class EncodingType {
        public static final String UTF_8 = "UTF-8";
    }


}
