package com.example.springbootsampleec.services.impl;
 
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.repositories.UserRepository;
import com.example.springbootsampleec.services.UserService;
 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;
 
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
 
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
 
    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        // userRepository の findAll メソッドを呼び出す。
        return userRepository.findAll();
    }
 
    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }
 
	@Transactional
	@Override
	public void register(String name, String email, String password, String[] roles) {
		// 該当のメールアドレスが登録されているかどうかをチェック
		if (userRepository.findByEmail(email).isPresent()) {
			throw new RuntimeException("該当のメールアドレスは登録済みです。");
		}
		// パスワードを暗号化
		String encodedPassword = passwordEncode(password);
		// ユーザー権限の配列を文字列にコンバート
		String joinedRoles = joinRoles(roles);

		// User エンティティの生成

		User user = new User(null, null, null, null, name, email, encodedPassword, joinedRoles, Boolean.TRUE);

		// ユーザー登録
		userRepository.saveAndFlush(user);
	}
 
    // パスワードを暗号化
    private String passwordEncode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
 
    // ユーザー権限の配列を文字列にコンバート
    private String joinRoles(String[] roles) {
        if (roles == null || roles.length == 0) {
            return "";
        }
        return Stream.of(roles)
            .map(String::trim)
            .map(String::toUpperCase)
            .collect(Collectors.joining(","));
    }
}