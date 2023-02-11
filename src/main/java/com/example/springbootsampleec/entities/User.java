package com.example.springbootsampleec.entities;
 
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id
    
    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private List<Item> items;
    
    // ManyToMany, JoinTable を追記
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="cart",
        joinColumns = @JoinColumn(name="user_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="item_id", referencedColumnName="id"))
    private Set<Item> orderItems = new HashSet<Item>();
 
 
    // ManyToMany, JoinTable を追記
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="likes",
    	joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
    	inverseJoinColumns = @JoinColumn(name="item_id", referencedColumnName = "id"))
	private Set<Item> likeItems = new HashSet<Item>();
    
    @Column(name = "name", length = 60, nullable = false)
    private String name; // ユーザー名
 
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email; // メールアドレス
 
    @Column(name = "password", length = 100, nullable = false)
    private String password;  // パスワード
 
    @Column(name = "roles", length = 120)
    private String roles; // ロール（役割）
 
    @Column(name = "enable_flag", nullable = false)
    private Boolean enable; // 有効フラグ

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, name, password);
	}
}