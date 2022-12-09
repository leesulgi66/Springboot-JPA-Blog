package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;


//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
//Transactional = 일이 처리되기 위한 가장 작은 단위
//Service를 쓰는 이유 1. 트랜젝션 관리, 2. 서비스의 의미(여러개의 Transactional이 묶여 하나의 Service가 되고 하나의 Transactional로 묶여 작동한다)
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public User 회원찾기(String username) {
        User user = userRepository.findByUsername(username).orElseGet(User::new);
        return user;
    }

    @Transactional
    public int 회원가입(User user) {
        String rawPassword = user.getPassword(); // 1234 원문
        String encPassword = encoder.encode(rawPassword); // 해쉬
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        try {
            userRepository.save(user);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    @Transactional
    public void 회원수정(User user) {
        //수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
        //select를 해서 User오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위함
        //영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려줌
        User psersistance = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원 찾기 실패");
        });

        //Validate 체크 (카카오 로그인 사용자들은 개인정보를 변경을 할 수 없다.)
        if(psersistance.getOauth() == null || psersistance.getOauth().equals("")){
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);
            psersistance.setPassword(encPassword);
            psersistance.setEmail(user.getEmail());
        }

        //회원수정 함수 종료시 = 서비스 종료 = 트랜잭셕 종료 = commit이 자동으로 됨.
        //영속화된 persistance 객체의 변화가 감지되면 더티체킹으로 update문을 날려줌.
    }

    @Transactional
    public void 회원탈퇴(int userId, HttpSession session) {
        User user = userRepository.findById(userId).orElseThrow(()->{
            return new IllegalArgumentException("회원을 찾을 수 없습니다.");
        });
        session.removeAttribute(user.getUsername());
        boardRepository.deleteAllByUserId(user.getId());
        userRepository.deleteById(userId);
    }
}
