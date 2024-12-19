package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;


/**
 * ユーザ情報 Service
 */
@Service
public class UserService {

  /**
   * ユーザ情報 Repository
   */
  @Autowired
  private UserRepository userRepository;

  /**
   * ユーザ情報 全検索
   * @return 検索結果
   */
  public List<UserEntity> searchAll() {
    return userRepository.findAll();
  }
  
  /**
   * ユーザ情報 主キー検索
   * @return 検索結果
   */
  public UserEntity findById(Long id) {
    return userRepository.findById(id).get();
  }
  
  /**
   * ユーザ情報 新規登録
   * @param user ユーザ情報
   */
  public void create(UserRequest userRequest) {
    Date now = new Date();
    UserEntity user = new UserEntity();
    user.setName(userRequest.getName());
    user.setAddress(userRequest.getAddress());
    user.setPhone(userRequest.getPhone());
    user.setCreateDate(now);
    user.setUpdateDate(now);
    userRepository.save(user);
  }
  
  /**
   * ユーザ情報 更新
   * @param user ユーザ情報
   */
  public void update(UserUpdateRequest userUpdateRequest) {
    UserEntity user = findById(userUpdateRequest.getId());
    user.setAddress(userUpdateRequest.getAddress());
    user.setName(userUpdateRequest.getName());
    user.setPhone(userUpdateRequest.getPhone());
    user.setUpdateDate(new Date());
    userRepository.save(user);
  }

  /**
   * ユーザー情報 物理削除
   * @param id ユーザーID
   */
  public void delete(Long id) {
    UserEntity user = findById(id);
    userRepository.delete(user);
  }
}