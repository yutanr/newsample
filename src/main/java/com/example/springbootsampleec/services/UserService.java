package com.example.springbootsampleec.services;
 
import com.example.springbootsampleec.entities.User;
 
import java.util.List;
import java.util.Optional;
 
public interface UserService {
    // ユーザー一覧の取得
    List<User> findAll();
    // ユーザーの取得
    Optional<User> findById(long id);
    // ユーザーの登録
    void register(String name, String email, String password, String[] roles);
}