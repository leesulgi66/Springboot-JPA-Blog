package com.cos.blog.controller.api;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
        System.out.println("UserApiController : save 호출됨");
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴 (Jackson)
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user) {
        userService.회원수정(user);
        //여기서는 트랜잭션이 종료되기 때문에 DB값은 변경이됨.
        //하지만 세션값은 변경되지 않은 상태이므로 직접 세션값을 변경해 주어야 함.
        //세션등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseDto<Integer> delete(@PathVariable int userId, HttpSession session) {
        userService.회원탈퇴(userId, session);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
