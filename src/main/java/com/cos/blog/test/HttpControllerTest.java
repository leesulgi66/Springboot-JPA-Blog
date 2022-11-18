package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

//사용자가 요청 -> 응답(HTML 파일)
//@Controller

//사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

    //인터넷 브라우저 요청은 무조껀 get요청밖에 할 수 없다.
    @GetMapping("/http/get") //(select)
    public String getTest() {
        return "get 요청";
    }

    @PostMapping("/http/post") //(insert)
    public String postTest() {
        return "post 요청";
    }

    @PutMapping("/http/put") //(update)
    public String putTest() {
        return "put 요청";
    }

    @DeleteMapping("/http/delete") //(delete)
    public String deleteTest() {
        return "delete 요청";
    }
}
