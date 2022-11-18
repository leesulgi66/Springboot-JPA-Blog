package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

//사용자가 요청 -> 응답(HTML 파일)
//@Controller
//사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

    private static final String TAG = "HttpControllerTest : ";

    @GetMapping("/http/lombok")
    public String lombokTest() {
        //lombok의 빌더를 사용하면 생성자를 따로 생성하지 않아도 되고, 순서를 지킬 필요도 없다.
        Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
        System.out.println(TAG+"getter : "+m.getUsername());
        m.setUsername("cos");
        System.out.println(TAG+"setter : "+m.getUsername());
        return "lombok test 완료";
    }

    //인터넷 브라우저 요청은 무조껀 get요청밖에 할 수 없다.
    @GetMapping("/http/get") //(select)
    public String getTest(Member m) { //@RequestParam으로 각각 받지 않아도 스프링이 알아서 Member 객체에 쿼리스트링을 넣어준다! (누가 -> MessageConverter(스프링부트))

        return "get 요청 : " +m.getId()+" ,"+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
    }

    @PostMapping("/http/post") //(insert)
    public String postTest(@RequestBody Member m) {
        return "post 요청 : " +m.getId()+" ,"+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
    }

    @PutMapping("/http/put") //(update)
    public String putTest(@RequestBody Member m) {
        return "put 요청 : "+m.getId()+" ,"+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
    }

    @DeleteMapping("/http/delete") //(delete)
    public String deleteTest() {
        return "delete 요청";
    }
}
