package cn.itcast.user.web;

import cn.itcast.user.config.PatternProperties;
import cn.itcast.user.pojo.User;
import cn.itcast.user.service.UserService;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.netflix.ribbon.proxy.annotation.Http;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/user")
//@RefreshScope
public class UserController {

    @Autowired
    private UserService userService;
    // 注入nacos中的配置属性
    @Autowired
    private PatternProperties patternProperties;

    //环境测试
    @GetMapping("/prop")
    public  PatternProperties pattern(){
        return patternProperties;
    }
    // 编写controller，通过日期格式化器来格式化现在时间并返回
    @GetMapping("/now")
    public String now(){
        return LocalDateTime.now().format(
                DateTimeFormatter.ofPattern(patternProperties.getDateformat(), Locale.CHINA)
        );
    }

    /**
     * 路径： /user/110
     *
     * @param id 用户id
     * @return 用户
     */
    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id,@RequestHeader(value = "Truth",required = false) String truth) {
        System.out.println("truth:"+truth);
        return userService.queryById(id);
    }
}
