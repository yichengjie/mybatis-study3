package com.yicj.study.mybatis.i18n;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * <p>
 * MessageSourceTest
 * </p>
 *
 * @author yicj
 * @since 2023年11月22日 17:53
 */
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
