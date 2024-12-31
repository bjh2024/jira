
// 토스트 메시지를 받아서 띄워줄 메서드
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
// 윈도우 로드 후 웹 소켓 연결
window.addEventListener("load",function(){
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

// 소켓으로 보낼 메시지
/*function sendToastMessage(toast){
	stompClient.send(`/app/toast/${window.location.pathname.split("/")[1]}`, {}, JSON.stringify(toast));
}*/