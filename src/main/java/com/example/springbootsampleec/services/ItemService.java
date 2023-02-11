package com.example.springbootsampleec.services;
 

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;
 
public interface ItemService {
    // 投稿一覧の取得
    List<Item> findAll();
    // ID を指定して投稿を取得
    Optional<Item> findById(long id);
    

    
    
    // 商品情報を更新
    void updateItem(long id, String name, int price, int stock, String description);
    // 削除
    void delete(long id);
    // 投稿の登録
    void register(String name, int price, int stock, String description, MultipartFile image);
	

    List<Item> findAllByNameContaining(String name);
    List<Item> findAllByDescriptionContaining(String description);
//	List<Item> search(String name, String description);
    
    // ブックマーク処理
    void toggleLike(User user, long item_id);
    List<Item> search(String name, String description);

	List<Item> findAllById(long id);
	
	void getOrderItems(User user, long Item_id);
//	void addItem(User user, long Item_id);
	void getDeleteItems(User user, long Item_id);
	
	
}