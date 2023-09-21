package pnu.cse.cloudchain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pnu.cse.cloudchain.auth.entity.UserInfoEntity;


public interface SignRepository extends JpaRepository<UserInfoEntity, String> {
    UserInfoEntity findByUserid(String userid);
    UserInfoEntity findByName(String name);
//    UserInfo deleteByUserid(String userid);
//    UserInfo deleteUserInfoByUserid(String userid);
}