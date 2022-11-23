package com.cos.blog.service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
