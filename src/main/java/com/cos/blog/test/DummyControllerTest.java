package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Supplier;

//html 파일이 아니라 data를 리턴해주는 controller = Restcontroller
@RestController
public class DummyControllerTest {

    @Autowired // 의존성 주입(DI)
    private UserRepository userRepository;

    //http://localhost:8000/blog/dummy/user
    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    //한페이지당 2건에 데이터를 리턴받아 볼 예정
    @GetMapping("dummy/user")
    public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable);

        List<User> users = pagingUser.getContent();
        return users;
    }

    //{id} 주소로 파라미터를 전달 받을 수 있음.
    //http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        //user/4을 찾으면 내가 내가 데이터베이스에서 찾지 못하면 user가 널이 될것이다.
        //그렇게 되면 return이 null이 되므로 오류가 생길 수 있다.
        //Optional로 너의 User객첼르 감싸서 가져올테니 null인지 아닌지 판단해서 return해라.

//        //람다식
//        User user = userRepository.findById(id).orElseThrow(()->{
//            return new IllegalArgumentException("해당 사용자는 없습니다. id: "+id);
//        });

        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 사용자가 없습니다. id: "+id);
            }
        });

        // 요청 : 웹브라우저
        // user 객체 = 자바 오브젝트
        //변환 (웹브라우저가 이해할 수 있는 데이터) -> json(Gson 라이브러리)
        //스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
        //만약 자바 오브젝트를 리턴하게 되면 MessageConverter가 jackson라이버리를 호출해서
        //user 오브젝트를 json으로 변환해서 브라우저에게 던져준다.
        return user;
    }

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

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
