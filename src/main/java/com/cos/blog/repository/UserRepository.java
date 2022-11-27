package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// DAO
// 자동으로 bean등록이 된다.
//@Repository 생략이 가능하다
public interface UserRepository extends JpaRepository<User, Integer> {

}

// JPA Naming 쿼리
// SELECT * FROM user WHERE =?1 AND password = ?2;
//User findByUsernameAndPassword(String username, String password);

//위의 JPA 쿼리문을 nativeQuery로 사용하면 아래와 같음.
//@Query(value = "SELECT * FROM user WHERE =?1 AND password = ?2", nativeQuery = true)
//User login(String username, String password);
