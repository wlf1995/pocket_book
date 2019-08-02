package com.ibicn.hr.config;

import com.ibicn.hr.entity.sys.SystemUser;
import com.ibicn.hr.service.sys.SystemUserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

/**
 * 监听
 *
 * @CreatedBy
 * @LastModifiedBy 自动注入用户
 */
@Component
public class UserAuditorAware implements AuditorAware<Integer> {
    @Autowired
    SystemUserServiceI userServiceI;

    @Override
    public Optional<Integer> getCurrentAuditor() {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = principal.getName();
        SystemUser curUser = userServiceI.findByUserName(userName);
        //TODO: 根据实际情况取真实用户
        return Optional.of(curUser.getId());
    }
}