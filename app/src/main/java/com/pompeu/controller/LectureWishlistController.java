package com.pompeu.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pompeu.dao.LectureWishlistDao;
import com.pompeu.domain.LectureWishlist;


@RestController 
public class LectureWishlistController {

  // @Autowired
  // - 필드 선언부에 이 애노테이션을 붙여서 표시해 두면, 
  //   Spring Boot가 UsersController 객체를 만들 때 MemberDao 구현체를 찾아 자동으로 주입한다. 
  //
  @Autowired
  LectureWishlistDao classWishlistDao;

  @RequestMapping("/classWishlist/list")
  public Object list() {
    return classWishlistDao.findAll();
  }

  @RequestMapping("/classWishlist/add")
  public Object add(LectureWishlist classwishlist) {
    return classWishlistDao.insert(classwishlist);
  }


  @RequestMapping("/classWishlist/get")
  public Object get(LectureWishlist classwishlist) {
    List<LectureWishlist> like = classWishlistDao.findByNo(classwishlist);
    if (like == null) {
      return "";
    }
    return like;
  }

  //  @RequestMapping("/classWishlist/update")
  //  public Object update(ClassWishlist classwishlist) {
  //    return classWishlistDao.update(classwishlist);
  //  }

  @RequestMapping("/classWishlist/delete")
  public Object delete(LectureWishlist classwishlist) {
    return classWishlistDao.delete(classwishlist);
  }

}
