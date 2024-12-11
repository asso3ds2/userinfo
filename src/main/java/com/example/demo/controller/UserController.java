package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;

/**
 * ユーザ情報 Controller
 */
@Controller
public class UserController {

  /**
   * ユーザ情報 Service
   */
  @Autowired
  private UserService userService;

  /**
   * ユーザ情報一覧画面を表示
   * @param model Model
   * @return ユーザ情報一覧画面
   */
  @GetMapping(value = "/user/list")
  public String displayList(Model model) {
    List<UserEntity> userlist = userService.searchAll();
    model.addAttribute("userlist", userlist);
    return "user/list";
  }

  /**
   * ユーザ新規登録画面を表示
   * @param model Model
   * @return ユーザ情報一覧画面
   */
  @GetMapping(value = "/user/add")
  public String displayAdd(Model model) {
    return "user/add";
  }

  /**
   * ユーザ新規登録画面へ渡すリクエストデータを作成
   * @param userRequest リクエストデータ
   * @param model Model
   * @return リクエストデータ
   */
  @ModelAttribute
  public UserRequest createUserRequest(){
    return new UserRequest();
  }

  /**
   * ユーザ新規登録
   * @param userRequest リクエストデータ
   * @param model Model
   * @return ユーザ情報一覧画面
   */
  @PostMapping("/user/create")
  public String create(@Validated @ModelAttribute UserRequest userRequest, BindingResult result, Model model) {

    if (result.hasErrors()) {
      // 入力チェックエラーの場合
      List<String> errorList = new ArrayList<String>();
      for (ObjectError error : result.getAllErrors()) {
        errorList.add(error.getDefaultMessage());
      }
      model.addAttribute("validationError", errorList);
      return "user/add";
    }
    // ユーザ情報の登録
    userService.create(userRequest);
    return "redirect:/user/list";
  }


  /**
   * ユーザ情報詳細画面を表示
   * @param id 表示するユーザID
   * @param model Model
   * @return ユーザ情報詳細画面
   */
  @GetMapping("/user/{id}")
  public String displayView(@PathVariable Long id, Model model) {
    UserEntity user = userService.findById(id);
    model.addAttribute("userData", user);
    return "user/view";
  }
  

  /**
   * ユーザ編集画面を表示
   * @param id 表示するユーザID
   * @param model Model
   * @return ユーザ編集画面
   */
  @GetMapping("/user/{id}/edit")
  public String displayEdit(@PathVariable Long id, Model model) {
    UserEntity user = userService.findById(id);
    UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
    userUpdateRequest.setId(user.getId());
    userUpdateRequest.setName(user.getName());
    userUpdateRequest.setPhone(user.getPhone());
    userUpdateRequest.setAddress(user.getAddress());
    model.addAttribute("userUpdateRequest", userUpdateRequest);
    return "user/edit";
  }

  /**
   * ユーザ更新
   * @param userRequest リクエストデータ
   * @param model Model
   * @return ユーザ情報詳細画面
   */
  @RequestMapping(value = "/user/update", method = RequestMethod.POST)
  public String update(@Validated @ModelAttribute UserUpdateRequest userUpdateRequest, BindingResult result, Model model) {

    if (result.hasErrors()) {
      List<String> errorList = new ArrayList<String>();

      for (ObjectError error : result.getAllErrors()) {
        errorList.add(error.getDefaultMessage());
      }
      model.addAttribute("validationError", errorList);
      return "user/edit";
    }

    // ユーザ情報の更新
    userService.update(userUpdateRequest);
    return String.format("redirect:/user/%d", userUpdateRequest.getId());
  }

  /**
   * ユーザ情報削除
   * @param id 表示するユーザID
   * @param model Model
   * @return ユーザ情報詳細画面
   */
  @GetMapping("/user/{id}/delete")
  public String delete(@PathVariable Long id, Model model) {
    // ユーザー情報の削除
    userService.delete(id);
    return "redirect:/user/list";
  }
}