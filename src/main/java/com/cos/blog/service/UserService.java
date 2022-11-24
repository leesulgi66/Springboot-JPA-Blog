package com.cos.blog.service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
//Transactional = 일이 처리되기 위한 가장 작은 단위
//Service를 쓰는 이유 1. 트랜젝션 관리, 2. 서비스의 의미(여러개의 Transactional이 묶여 하나의 Service가 되고 하나의 Transactional로 묶여 작동한다)
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void 회원가입(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = true) //Select할 때 트랜잭션 시작, 서비스 종료시에 트랜젝션 종료(정합성 유지)  *save가 없지만 transactional을 사용하는 이유
    public User 로그인(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
