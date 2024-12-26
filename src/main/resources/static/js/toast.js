function showToast(message) {
  let toast = document.querySelector(".toast_message");
  toast.classList.add("show");
  toast.innerHTML = 
  `<div style="margin: 10px;">
  		<img src="/images/alram_icon2.svg" style="width: 16px; height: 16px;"> 알림 </br>
  	</div>
  	<div>
  		${message}
  	</div>`; // 메시지 설정
	
	setTimeout(() => {
	   toast.classList.remove("show");
	 }, 5000);
  
}
	
let toastStompClient = null;
let newIssueData = null;
window.addEventListener("load",function(){
	/*newIssueData = localStorage.getItem('newIssue');
	const issueData = JSON.parse(newIssueData);
	if (newIssueData) {
		
		  document.querySelector(".toast_message").innerHTML = 
		  `<div class="toast_message">
		  	<div style="margin: 10px;">
		  		<img src="/images/alram_icon.png" style="width: 16px; height: 16px;"> 알림 </br>
		  	</div>
		  	<div>
		  		${issueData.issueName}
		  	</div>
		  </div>`; // 메시지 설정
		
	  // 'newIssue'가 있다면 토스트 메시지를 띄운 후, localStorage에서 삭제
	  
	  localStorage.removeItem('newIssue');
	}*/
	  toastConnect();
function toastConnect(){
	let socket = new SockJS("/toast-websocket-endpoint");
	toastStompClient = Stomp.over(socket);
	toastStompClient.connect({}, function(frame){
		console.log("연결 성공" + frame);
		const topic2 = `/topic/toast/${window.location.pathname.split("/")[1]}`
			toastStompClient.subscribe(topic2, function(issueDatas){
				showToast(issueDatas.body);
			})
		})
	}
})


function sendToastMessage(toast){
	stompClient.send(`/app/toast/${window.location.pathname.split("/")[1]}`, {}, JSON.stringify(toast));
}