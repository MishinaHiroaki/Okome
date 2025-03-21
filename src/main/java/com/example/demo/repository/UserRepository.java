package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

//Repositoryインターフェース（具体的にはJpaRepository）を利用すると、データベースへのCRUD（Create, Read, Update, Delete）操作が簡単に実装できます
public interface UserRepository extends JpaRepository<User, Long>{

	public User findByEmail(String email); //<エンティティのクラス,主キーの型>を受け取る

}
