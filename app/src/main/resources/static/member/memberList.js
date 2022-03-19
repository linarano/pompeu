document.addEventListener("DOMContentLoaded", function(){
   fetch("/member/findCount")
    .then(function(response) {
      return response.json();
    })
    .then(function(result) {
      console.log(result);
      for (var rst of result) {
        var tr = document.createElement("tr");
        tr.innerHTML = `<td>${rst.total}</td>
        <td>${rst.creator}</a></td>
        <td>${rst.user}</td>`;
        document.querySelector("#tbody1").appendChild(tr);
      }
    });
});

var btn = document.getElementById("srchBtn");
btn.addEventListener('click', function() {
  var nickName = document.querySelector("#nick_name").value;
  var phone = document.querySelector("#phone").value;
  var email = document.querySelector("#email").value;
  var memberTypeNo = document.querySelector("#member_type_no").value;
  
  

  
  console.log("nickName : " + nickName);
  console.log("phone : " + phone);
  console.log("email : " + email);
  console.log("memberTypeNo : " + memberTypeNo);
  
  
  
  fetch(`/member/srchMember?memberTypeNo=${memberTypeNo}`)
    .then(function(response) {
      return response.json();
    })
    .then(function(result) {
      console.log("AAA");
      console.log(result);
      var count = 0;
      
      for (var rst of result) {
        count = count + 1;
        
        var tr = document.createElement("tr");
        tr.innerHTML = `<td>`+count+`</td>
        <td>${rst.member_type_no_name}</a></td>
        <td>${rst.nickname}</td>
        <td>${rst.name}</td>
        <td>${rst.email}</td>
        <td>${rst.phone}</td>`;
        document.querySelector("#tbody2").appendChild(tr);
        
      }
    });
  
});