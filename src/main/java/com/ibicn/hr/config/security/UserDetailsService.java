package com.ibicn.hr.config.security;

import com.ibicn.hr.entity.sys.systemRole;
import com.ibicn.hr.entity.sys.systemUser;
import com.ibicn.hr.dao.sys.SystemUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * @Author 王立方
 * @Description 用于Security查询角色进行认证
 * @Date 10:20 2019/8/1
 * @param
 * @return
 **/
@Service("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private SystemUserDao userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        systemUser user = userRepository.findByUserName(userName);
        if(user==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (systemRole role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return new User(user.getUserName(), user.getPassword(), authorities);
    }
}