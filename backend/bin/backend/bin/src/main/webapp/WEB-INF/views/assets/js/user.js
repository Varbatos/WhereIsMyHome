window.onload = function () {
  var loginUser = localStorage.getItem("loginUser");
  if (loginUser !== null) {
    loginUser = JSON.parse(loginUser);

    var loginOff = document.querySelector("#login-off");
    var loginOn = document.querySelector("#login-on");

    // console.log(loginOff);
    // console.log(loginOn);

    loginOff.style.display = "none";
    loginOn.style.display = "";

    document.querySelector("#user-name").innerText = loginUser.userName;
  }
};

// logout
function logout() {
  localStorage.removeItem("loginUser");

  var loginOff = document.querySelector("#login-off");
  var loginOn = document.querySelector("#login-on");

  loginOff.style.display = "";
  loginOn.style.display = "none";

  // board 만들기 삭제
  var boardCreateLink = document.querySelector("#board-create");
  boardCreateLink.setAttribute("style", "display: none");
}
function login() {
  var id = document.querySelector("#id").value;
  var pw = document.querySelector("#pw").value;

  var xhr = new XMLHttpRequest();
  xhr.open("GET", "../assets/data/user.json")
  
  xhr.setRequestHeader("Content-Type", "application/text")

  //객체 상태 변화 이벤트 핸들러 함수
  xhr.onreadystatechange = function() {
    if(xhr.readyState === xhr.DONE){
      if(xhr.status === 200){
        var users = JSON.parse(xhr.responseText)

        for(var i = 0; i<users.length; i++){
          if(users[i].userId === id && users[i].userPw === pw) {
            localStorage.setItem("loginUser", JSON.stringify(users[i]));
            //페이지를 index.html로 이동하므로 이곳에서 header 조작 x
            location.href = "../../index.html";
            return;
          }
        }
        alert("ID 혹은 PW가 일치하지 않습니다.")
      }
    }
  }
  xhr.send();
}
