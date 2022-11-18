package com.cos.blog.test;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyControllerTest {

    @Autowired // 의존성 주입(DI)
    private UserRepository userRepository;

    //http://localhost:8000/blog/dummy/join (요청)
    //http의 body에 username, password, email 데이터를 가지고 (요청)
    @PostMapping("/dummy/join1")
    public String join(String username, String password, String email) { // key=balue(약속된 규칙) x-www-form 으로 오는 것들
        System.out.println("username : "+username );
        System.out.println("password : "+password );
        System.out.println("email : "+email );
        return "회원가입이 완료되었습니다.";
    }

    @PostMapping("/dummy/join")
    public String join(User user) { //
        System.out.println("id : "+user.getId());
        System.out.println("username : "+user.getUsername());
        System.out.println("password : "+user.getPassword());
        System.out.println("email : "+user.getEmail());
        System.out.println("role : "+user.getRole());
        System.out.println("createDate : "+user.getCreateDate());

        user.setRole("user");
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
