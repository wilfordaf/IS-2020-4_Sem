package services;

import dao.UserDao;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserService implements UserDetailsService{

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " was not found");
        }

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().toString())));
    }

    public void save(User user) {
        userDao.save(user);
    }

    public void deleteById(int id) {
        userDao.deleteById(id);
    }

    public User getAuthorizedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDao.getByUsername(
                principal instanceof UserDetails userDetails
                        ? userDetails.getUsername() : principal.toString());
    }
}
