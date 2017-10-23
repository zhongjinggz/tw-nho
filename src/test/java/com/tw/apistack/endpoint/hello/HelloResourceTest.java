package com.tw.apistack.endpoint.hello;

import com.tw.apistack.endpoint.hello.dto.Greeting;
//import com.tw.apistack.endpoint.hello.HelloResource;
//import com.tw.apistack.endpoint.hello.dto.RestResult;
import static org.junit.Assert.*;

import com.tw.apistack.endpoint.hello.dto.RestResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by jxzhong on 2017/6/27.
 */


public class HelloResourceTest {

    @Test
    public void greeting() throws Exception {
        HelloResource helloResource = new HelloResource();
        Greeting result = helloResource.greeting("Man");
        assertThat(result.getContent()).isEqualTo("Hello, Man!");
        assertThat(result.getId()).isNotNull();
    }

    @Test
    public void examlogin() throws Exception {
        HelloResource helloResource = new HelloResource();
        RestResult result = helloResource.examlogin("12121", "cccc");

    }

    @Test
    public void successful_login() throws Exception {
        HelloResource helloResource = new HelloResource();
        RestResult result = helloResource.examlogin("888888", "1111");
        RestResult except = new RestResult(null, null, "success");

        assertEquals(except, result);
    }

    @Test
    public void verify_code_is_error() throws Exception {
        HelloResource helloResource = new HelloResource();
        RestResult result = helloResource.examlogin("888888", "1333");
        RestResult except = new RestResult("ER_01", "验证码错误", null);

        assertEquals(except, result);
    }

    @Test
    public void didnt_enroll() throws Exception {
        HelloResource helloResource = new HelloResource();
        RestResult result = helloResource.examlogin("666666", "1333");
        RestResult except = new RestResult("ER_02", "没有报名", null);

        assertEquals(except, result);
    }



}