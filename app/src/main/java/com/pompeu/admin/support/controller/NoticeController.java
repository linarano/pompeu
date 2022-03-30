package com.pompeu.admin.support.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pompeu.admin.support.dao.NoticeDao;
import com.pompeu.admin.support.service.NoticeService;
import com.pompeu.domain.Notice;

@RestController 
public class NoticeController {

  // @Autowired
  // - 필드 선언부에 이 애노테이션을 붙여서 표시해 두면, 
  //   Spring Boot가 NoticeController 객체를 만들 때 NoticeDao 구현체를 찾아 자동으로 주입한다. 
  //
  @Autowired
  NoticeService noticeService;

  @Autowired
  NoticeDao noticeDao; // noticeDao들어간 부분에서 에러뜨길래 임시로 넣어놓음. 나중에 수정요 (효범)

  @RequestMapping("/notice/list")
  public Object list() {
    return noticeService.list();
  }

  @RequestMapping("/notice/add")
  public Object add(Notice notice) {
    return noticeService.add(notice);
  }


  @RequestMapping("/notice/get")
  public Object get(int no) {
    Notice notice = noticeService.get(no);
    if (notice == null) {
      return "";
    }
    noticeService.increaseViewCount(no);
    return notice;
  }

  @RequestMapping("/notice/update")
  public Object update(Notice notice) {
    return noticeDao.update(notice);
  }

  @RequestMapping("/notice/delete")
  public Object delete(int no) {
    return noticeDao.delete(no);
  }

}
