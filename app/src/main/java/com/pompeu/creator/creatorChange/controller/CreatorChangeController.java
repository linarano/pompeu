package com.pompeu.creator.creatorChange.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.pompeu.creator.creatorChange.service.CreatorChangeService;
import com.pompeu.domain.CreatorUpdate;
import com.pompeu.domain.Member;


@RestController 
public class CreatorChangeController {

  // @Autowired
  // - 필드 선언부에 이 애노테이션을 붙여서 표시해 두면, 
  //   Spring Boot가 MemberController 객체를 만들 때 CreatorChangeDao 구현체를 찾아 자동으로 주입한다. 
  //
  @Autowired
  CreatorChangeService creatorChangeService;

  @Autowired
  PasswordEncoder passwordEncoder;                          //1

  @RequestMapping("/creatorChange/list")
  public Object list() {
    return creatorChangeService.list();
  }

  @RequestMapping("/creatorChange/add")
  public Object add(Member member) {
    return creatorChangeService.add(member);
  }

  @RequestMapping("/creatorChange/get")
  public Object get(int no) {
    Member member = creatorChangeService.get(no);
    return member != null ? member : "";
  }

  @RequestMapping("/creatorChange/update")
  public Object update(Member member) {
    return creatorChangeService.update(member);
  }

  @RequestMapping("/creatorChange/delete")
  public Object delete(int no) {
    return creatorChangeService.delete(no);
  }

  @RequestMapping("/creatorChange/getCreator")
  public Object getCreator(@AuthenticationPrincipal Member member) {
    int no = member.getNo();
    CreatorUpdate creator = creatorChangeService.getCreator(no);
    return creator != null ? creator : "";
  }

  @RequestMapping("/creatorChange/updateCreator")
  public Object updateCreator(CreatorUpdate cUpdate, MultipartFile file, @AuthenticationPrincipal Member member) {
    try {
      cUpdate.setImg(saveFile(file));
      cUpdate.setNo(member.getNo());
      cUpdate.setPassword(passwordEncoder.encode(cUpdate.getPassword()));                   //2
      return creatorChangeService.updateCreator(cUpdate);

    } catch (Exception e) {
      e.printStackTrace();
      return "error!";
    }
  }

  @RequestMapping("/creatorChange/checkNickname")
  public Object checkNickname(String nickname) {
    return creatorChangeService.checkNickname(nickname);
  }

  @RequestMapping("/creatorChange/deleteCreator")
  public Object deleteCreator(Member member, @AuthenticationPrincipal Member noBringer) {
    try {
      member.setNo(noBringer.getNo());
      return creatorChangeService.deleteCreator(member);
    } catch (Exception e) {
      e.printStackTrace();
      return "error!";
    }
  }

  @RequestMapping("/creatorChange/deleteCreatorDetail")
  public Object deleteCreatorDetail(@AuthenticationPrincipal Member member) {
    int no = member.getNo();
    return creatorChangeService.deleteCreatorDetail(no);
  }

  @RequestMapping("/creatorChange/image")
  public ResponseEntity<Resource> image(String filename) {

    try {
      // 다운로드할 파일의 입력 스트림 자원을 준비한다.
      File downloadFile = new File("./upload/creator/" + filename); // 다운로드 상대 경로 준비
      FileInputStream fileIn = new FileInputStream(downloadFile.getCanonicalPath()); // 다운로드 파일의 실제 경로를 지정하여 입력 스트림 준비
      InputStreamResource resource = new InputStreamResource(fileIn); // 입력 스트림을 입력 자원으로 포장

      // HTTP 응답 헤더를 준비한다. (캐시를 막기 위한 헤더 설정)
      HttpHeaders header = new HttpHeaders();
      header.add("Cache-Control", "no-cache, no-store, must-revalidate");
      header.add("Pragma", "no-cache");
      header.add("Expires", "0");

      // 다운로드 파일명을 지정하고 싶다면 다음의 응답 헤더를 추가하라!
      // => 다운로드 파일을 지정하지 않으면 요청 URL이 파일명으로 사용된다.
      header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

      return ResponseEntity.ok() // HTTP 응답 프로토콜에 따라 응답을 수행할 생성기를 준비한다.
          .headers(header) // 응답 헤더를 설정한다.
          .contentLength(downloadFile.length()) // 응답할 파일의 크기를 설정한다.
          .contentType(MediaType.APPLICATION_OCTET_STREAM) // 응답 콘텐트의 MIME 타입을 설정한다.
          .body(resource); // 응답 콘텐트를 생성한 후 리턴한다.

    } catch (Exception e) {
      //e.printStackTrace();
      System.out.println("요청한 파일이 없습니다.");
      return null;
    }
  }

  private String saveFile(MultipartFile file) throws Exception {
    // Multipart : 웹 클라이언트가 요청을 보낼 때, http 프로토콜의 바디 부분에 
    // 데이터를 여러 부분으로 나눠서 보내는 것.

    if (file != null && file.getSize() > 0) { 
      // 파일을 저장할 때 사용할 파일명을 준비한다.
      String filename = UUID.randomUUID().toString();

      // 파일명의 확장자를 알아낸다.
      int dotIndex = file.getOriginalFilename().lastIndexOf(".");
      // "."이 문자열에서 몇번째 문자인지 알아낸다
      if (dotIndex != -1) {
        filename += file.getOriginalFilename().substring(dotIndex);
        //"."부터 마지막까지 문자열을 추출해 filename에 더한다.
      }

      // 파일을 지정된 폴더에 저장한다.
      File photoFile = new File("./upload/creator/" + filename); // App 클래스를 실행하는 프로젝트 폴더
      file.transferTo(photoFile.getCanonicalFile()); // 프로젝트 폴더의 전체 경로를 전달한다.

      //      // 썸네일 이미지 파일 생성
      //      Thumbnails.of(photoFile)
      //      .size(50, 50)
      //      .crop(Positions.CENTER)
      //      .outputFormat("jpg")
      //      .toFile(new File("./upload/book/" + "50x50_" + filename));

      return filename;

    } else {
      return null;
    }
  }

}