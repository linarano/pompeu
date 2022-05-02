package com.pompeu.domain;

import lombok.Data;

@Data
public class Ask {

  int no;
  int answerNo;
  String askName;
  String askContent;
  String answerContent;
  java.sql.Date registerDate;
  java.sql.Date answerDate;
  boolean answerCheck;
  String answerCheckName;
  /**/
  int memberTypeNo;
  String memberType;
  int memberNo;
  String memberName;
  String memberEmail;
  int pageNo;
  int pageSize;

}
