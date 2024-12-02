window.addEventListener('load', function() {
  var loginUser = JSON.parse(this.localStorage.getItem('loginUser'))

  this.document.querySelector('#user-id').innerText = loginUser.userId
  this.document.querySelector('#user').innerText = loginUser.userName
  this.document.querySelector('#user-nickname').innerText = loginUser.userNickName
  this.document.querySelector('#user-email').innerText = loginUser.email


  var xhr = new XMLHttpRequest();
  xhr.open("GET", "../data/article.json");
  xhr.setRequestHeader("Content-Type", "application/text");

  //객체 상태 변화 이벤트 핸들러 함수 정의
  xhr.onreadystatechange = function(){
    if(xhr.readyState === xhr.DONE){
      if(xhr.status === 200){
        var articles = JSON.parse(xhr.responseText)

        var articleTable = document.querySelector('#my-article-table')
        for(var i = articles.length-1; i >= 0; i--){
          var article = articles[i]
          if(loginUser.userNickName == article.user){
            var articleItem = 
            '<tr>'
            +'<td>'+article.articleId+'</td>'
            +'<td><a href="../article/detail.html?articleId='+article.articleId+'">'+article.title+'</a></td>'
            +'<td>'+article.createDate+'</td>'
            +'<td>'+article.viewCnt+'</td>'
            +'<td>'+article.like+'</td>'
            +'</tr>'
            articleTable.innerHTML+=articleItem
          }
        }

        var comments = JSON.parse(localStorage.getItem('comments'))
        var commentListTag = document.querySelector("#my-comment-list")
        for(var j = 0; j<comments.length; j++ ){
          console.log(comments[j])
          if(comments[j].user == loginUser.userNickName){
            var commentItem =
            '<li>'
            +'<span>'+comments[j].content+'</span>'
            +'</li>'
            commentListTag.innerHTML += commentItem
          }
        }
      }
    }
  }

  xhr.send();
})