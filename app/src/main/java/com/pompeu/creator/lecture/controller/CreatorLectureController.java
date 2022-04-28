package com.pompeu.creator.lecture.controller;


import static com.pompeu.creator.lecture.controller.ResultMap.SUCCESS;
import java.io.File;
import java.io.FileInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.pompeu.creator.lecture.Service.CreatorLectureService;
import com.pompeu.domain.Lecture;

@RestController
@RequestMapping("/creatorLecture/")
public class CreatorLectureController {
  // log를 출력하는 도구 준비
  private static final Logger log = LogManager.getLogger(CreatorLectureController.class);

  @Autowired
  CreatorLectureService creatorLectureService;

  //크리에이터가 등록한 클래스 목록조회
  @RequestMapping("list")
  public Object list(/*@AuthenticationPrincipal Member member*/) {
    //log.debug(member.toString());member.getNo()
    //return new ResultMap().setStatus(SUCCESS).setData(boardService.list());
    int no=93;
    return new ResultMap().setStatus(SUCCESS).setData(creatorLectureService.list(no));
  }

  //클래스 등록
  @RequestMapping("add")
  public Object add(/*Lecture lecture, String[] time,*/ MultipartFile files) {
    log.debug(files.toString());
    System.out.println("하하핳");
    try { saveFile(files);

    } catch (Exception e) {
      e.getStackTrace();
    }
    //log.debug(lecture.toString());
    // creatorLectureService.add(lecture);

    //    //강의 시간 데이터
    //    try {
    //
    //     // lecture.setPhoto(saveFile(file))
    //
    //      ArrayList<LectureTime> timesList = new ArrayList<>();
    //      for(int i= 0; i< time.length; i++) {
    //        String[] value = time[i].split(",");
    //        if(value[0].length() == 0) { //시작시간
    //          continue;
    //        } 
    //        if(value[1].length() == 0) { //종료시간
    //          continue;
    //        }  
    //        LectureTime lectureTime = new LectureTime(value[0],value[1],Integer.parseInt(value[2]));
    //        timesList.add(lectureTime);
    //      }
    //      lecture.setTimes(timesList);
    //      lecture.setImages(saveFileMulti(files));
    //
    //    } catch (Exception e) {
    //      e.printStackTrace();
    //    } 
    //    return creatorLectureService.add(lecture);
    return "1";
  }

  //클래스 상세보기
  @RequestMapping("get")
  public Object get(int no) {
    Lecture lecture = creatorLectureService.get(no);
    if (lecture == null) {
      return "";
    }
    return lecture;
  }

  //클래스 수정
  @RequestMapping("update")
  public Object update(Lecture lecture) {
    return creatorLectureService.update(lecture);
  }

  //클래스 삭제
  @RequestMapping("delete")
  public Object delete(int no) {
    return creatorLectureService.delete(no);
  }

  @RequestMapping("photo")
  public ResponseEntity<Resource> photo(String filename) {

    try {
      // 다운로드할 파일의 입력 스트림 자원을 준비한다.
      File downloadFile = new File("./upload/lecture-image/" + filename); // 다운로드 상대 경로 준비
      FileInputStream fileIn = new FileInputStream(downloadFile.getCanonicalPath()); // 다운로드 파일의 실제 경로를 지정하여 입력 스트림 준비
      InputStreamResource resource = new InputStreamResource(fileIn); // 입력 스트림을 입력 자원으로 포장

      // HTTP 응답 헤더를 준비한다.
      HttpHeaders header = new HttpHeaders();
      header.add("Cache-Control", "no-cache, no-store, must-revalidate");
      header.add("Pragma", "no-cache");
      header.add("Expires", "0");

      // 다운로드 파일명을 지정하고 싶다면 다음의 응답 헤더를 추가하라!
      // => 다운로드 파일을 지정하지 않으면 요청 URL이 파일명으로 사용된다.
      header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);



      //      // HTTP 응답 생성기를 사용하여 다운로드 파일의 응답 데이터를 준비한다.
      //      BodyBuilder http응답생성기 = ResponseEntity.ok(); // 요청 처리에 성공했다는 응답 생성기를 준비한다.
      //      http응답생성기.headers(header); // HTTP 응답 헤더를 설정한다.
      //      http응답생성기.contentLength(downloadFile.length()); // 응답 콘텐트의 파일 크기를 설정한다.
      //      http응답생성기.contentType(MediaType.APPLICATION_OCTET_STREAM); // 응답 데이터의 MIME 타입을 설정한다.
      //      
      //      // 응답 데이터를 포장한다.
      //      ResponseEntity<Resource> 응답데이터 = http응답생성기.body(resource);
      //      
      //      return 응답데이터; // 포장한 응답 데이터를 클라이언트로 리턴한다.

      return ResponseEntity.ok() // HTTP 응답 프로토콜에 따라 응답을 수행할 생성기를 준비한다.
          .headers(header) // 응답 헤더를 설정한다.
          .contentLength(downloadFile.length()) // 응답할 파일의 크기를 설정한다.
          .contentType(MediaType.APPLICATION_OCTET_STREAM) // 응답 콘텐트의 MIME 타입을 설정한다.
          .body(resource); // 응답 콘텐트를 생성한 후 리턴한다.

    } catch (Exception e) {

      return null;
    }

  }




  //이미지파일 등록
  private Object saveFile(MultipartFile files) throws Exception {
    log.info("호출되었음");
    log.debug(files);
    //    List<LectureImage> list = new ArrayList<LectureImage>();
    //    for(int i=0; i<files.size(); i++) {
    //
    //      if (files.get(i) != null && files.get(i).getSize() > 0) { 
    //        // 파일을 저장할 때 사용할 파일명을 준비한다.
    //        String filename = UUID.randomUUID().toString();
    //
    //        // 파일명의 확장자를 알아낸다.
    //        int dotIndex = files.get(i).getOriginalFilename().lastIndexOf(".");
    //        if (dotIndex != -1) {
    //          filename += files.get(i).getOriginalFilename().substring(dotIndex);
    //        }
    //
    //        // 파일을 지정된 폴더에 저장한다.
    //        File photoFile = new File("./upload/lecture_image/" + filename); // App 클래스를 실행하는 프로젝트 폴더
    //        files.get(i).transferTo(photoFile.getCanonicalFile()); // 프로젝트 폴더의 전체 경로를 전달한다.
    //
    //        LectureImage filenames = new LectureImage(files.get(i).getOriginalFilename(), filename);
    //        list.add(filenames);
    //      }
    //    } 
    //    log.debug(list.toString());
    //    return list;
    return "1";

  }
}