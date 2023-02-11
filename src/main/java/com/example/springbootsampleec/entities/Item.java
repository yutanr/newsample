package com.example.springbootsampleec.entities;
 
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id
    
    @ManyToOne(fetch = FetchType.EAGER)
    private User user; // ユーザーid
 
    // 追記
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="cart",
        joinColumns = @JoinColumn(name="item_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName="id"))
    private Set<User> orderedUsers = new HashSet<User>();
 
    // ManyToMany, JoinTable を追記
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="likes",
    	joinColumns = @JoinColumn(name="item_id", referencedColumnName = "id"),
    	inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"))
    private Set<User> likedUsers = new HashSet<User>();
    
    @Column(name = "name", length = 200, nullable = false)
    private String name; // 商品名
    
    @Column(name = "price", nullable = false)
    private int price; // 金額
    
    @Column(name = "stock", nullable = false)
    private int stock; // 在庫数
    
    @Column(name = "description", length = 1000, nullable = false)
    private String description; // 商品説明
 
    @Column(name = "image", length = 100, nullable = false)
    private String image;  // 画像
    
    // 作成日時
    @Column(name="createdAt",nullable = false, updatable = false, insertable = false, 
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private ZonedDateTime createdAt;
 
    // 更新日時
    @Column(name="updatedAt",nullable = false, updatable = false, insertable = false, 
    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private ZonedDateTime updatedAt;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name);
	}
 
}