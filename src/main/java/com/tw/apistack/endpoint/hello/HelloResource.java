package com.tw.apistack.endpoint.hello;

import java.util.concurrent.atomic.AtomicLong;

//import com.tw.apistack.endpoint.hello.dto.RestResult;
import com.tw.apistack.endpoint.hello.dto.RestResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tw.apistack.endpoint.hello.dto.Greeting;

/**
 * Created by jxzhong on 2017/6/27.
 */

@RestController
public class HelloResource {

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(TEMPLATE, name));
    }

    @PostMapping("/examlogin")
    public RestResult examlogin(@RequestParam(value = "phoneNum") String phoneNum,
                                @RequestParam(value = "verCode") String verCode) {
        RestResult result;
        if ("888888".equals(phoneNum) && "1111".equals(verCode)) {
            result = new RestResult(null, null, "success");
        } else if ("666666".equals(phoneNum)){
            result = new RestResult("ER_02", "没有报名", null);
        } else {
            result = new RestResult("ER_01", "验证码错误", null);
        }

        return result;
    }

}
