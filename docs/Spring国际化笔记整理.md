1. resources/message中添加hello.properties配置
    ```properties
    user.name=UserName {0}
    ```
2. resources/message中添加hello_zh_CN.properties配置
    ```properties
    user.name=用户名 {0}
    ```
3. 添加单元测试
    ```java
    @Slf4j
    public class MessageSourceTest {
        @Test
        public void message(){
            Locale locale = LocaleContextHolder.getLocale();
            String errorMessage = "message/hello";
            ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
            bundleMessageSource.setBasenames(errorMessage);
            bundleMessageSource.setDefaultEncoding("UTF-8");
            String message = bundleMessageSource.getMessage("user.name", new Object[]{"测试"}, locale);
            log.info("message : {}", message);
        }
    }
    ```