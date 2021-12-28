package cn.sixlab.minesoft.singte.core.service;

import cn.sixlab.minesoft.singte.core.common.utils.StCacheHolder;
import cn.sixlab.minesoft.singte.core.common.utils.StConst;
import cn.sixlab.minesoft.singte.core.dao.StUserDao;
import cn.sixlab.minesoft.singte.core.models.StUser;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StUserDetailsService implements UserDetailsService {

    @Autowired
    private StUserDao userMapper;

    @Value("${st.login.expire}")
    private Integer expire;

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        StUser stUser = userMapper.selectByUsername(username);
        if (stUser != null) {
            boolean enable = StConst.YES.equals(stUser.getStatus());

            List<GrantedAuthority> authorityList = new ArrayList<>();
            authorityList.add(new SimpleGrantedAuthority(stUser.getRole()));

            return new User(username, stUser.getPassword(), enable, true, true, !enable, authorityList);
        } else {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
    }

    public StUser loadUserByToken(String token){
        return userMapper.selectByToken(token);
    }

    public void updateToken(String username, String token) {
        StUser stUser = userMapper.selectByUsername(username);
        if (stUser != null) {
            stUser.setToken(token);
            stUser.setTokenValid(DateUtils.addMinutes(new Date(), expire));
            userMapper.save(stUser);
        }
    }

    public void updateTokenValid(String username, String token) {
        Object flag = StCacheHolder.CACHE_5m.get("token_reload_" + username);
        if (null == flag) {
            StCacheHolder.CACHE_5m.put("token_reload_" + username, StConst.YES);
            updateToken(username, token);
        }
    }
}