package com.pompeu.admin.member.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pompeu.admin.member.dao.MemberDaoXx;
import com.pompeu.admin.member.domain.MemberXx;

//@RestController 
public class MemberControllerXx {

  // @Autowired
  // - 필드 선언부에 이 애노테이션을 붙여서 표시해 두면, 
  //   Spring Boot가 MemberController 객체를 만들 때 MemberDao 구현체를 찾아 자동으로 주입한다. 
  //
  @Autowired
  MemberDaoXx memberDao;

  @RequestMapping("/member/list")
  public Object list() {
    return memberDao.findAll();
  }

  @RequestMapping("/member/add")
  public Object add(MemberXx member) {
    return memberDao.insert(member);
  }

  @RequestMapping("/member/get")
  public Object get(int no) {
    MemberXx member = memberDao.findByNo(no);
    if (member == null) {
      return "";
    }
    return member;
  }

  @RequestMapping("/member/update")
  public Object update(MemberXx member) {
    return memberDao.update(member);
  }

  @RequestMapping("/member/delete")
  public Object delete(int no) {
    return memberDao.delete(no);
  }

  @RequestMapping("/member/findCount")
  public Object memberList() {
    return memberDao.findCount();
  }

  @RequestMapping("/member/srchMember")
  public Object srchMember(MemberXx member) {

    System.out.println("srchMember : " + member.getNickName());
    System.out.println("srchMember : " + member.getPhone());
    System.out.println("srchMember : " + member.getEmail());
    System.out.println("srchMember : " + member.getMemberTypeNo());

    return memberDao.srchMember(member);
  }

  @RequestMapping("/member/memberRegi")
  public Object memberRegi(MemberXx member) {

    System.out.println("memberRegi : " + member.getName());
    System.out.println("memberRegi : " + member.getEmail());
    System.out.println("memberRegi : " + member.getPassword());
    System.out.println("memberRegi : " + member.getPhone());
    System.out.println("memberRegi : " + member.getNickName());
    System.out.println("memberRegi : " + member.getBirth());
    System.out.println("memberRegi : " + member.getGender());

    return memberDao.memberRegi(member);
  }


}