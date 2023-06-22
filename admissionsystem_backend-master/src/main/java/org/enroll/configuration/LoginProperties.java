package org.enroll.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data//提高代码的简洁，使用这个注解可以省去代码中大量的get()、 set()、 toString()等方法
@Component
@ConfigurationProperties(prefix = "enroll.login")
public class LoginProperties {

    private String adminName;//用户名

    private String adminPass;//密码
}
