// 토스트 메시지를 받아서 띄워줄 메서드
function showToast(message) {
  let toast = document.querySelector(".toast_message");
  toast.classList.add("show");
  let notice = message.split("/");
  toast.innerHTML = 
  `<a href="/project/${notice[1]}/board_main">
  <div style="margin: 10px;" >
  		<img src="/images/alram_icon2.svg" style="width: 16px; height: 16px;"> <b>알림</b> </br>
  	</div>
  	<div>
	   ${notice[0]}
  	</div>
	</a>`; // 메시지 설정
	
	setTimeout(() => {
	   toast.classList.remove("show");
	 }, 5000);
}
	
// 윈도우 로드 후 웹 소켓 연결
let toastStompClient = null;
window.addEventListener("load",function(){
	let jiraIdx = document.querySelector(".toast_message").getAttribute("jira-idx");
	toastConnect();
	function toastConnect(){
		let socket = new SockJS("/websocket-endpoint");
		toastStompClient = Stomp.over(socket);
		toastStompClient.connect({}, function(frame){
			const topic = `/topic/${jiraIdx}`
			toastStompClient.subscribe(topic, function(issueDatas){
				showToast(issueDatas.body);
			})
		})
	}
})

// 소켓으로 보낼 메시지
function sendToastMessage(toast){
	let jiraIdx = document.querySelector(".toast_message").getAttribute("jira-idx")
	toastStompClient.send(`/app/toast/${jiraIdx}`, {}, JSON.stringify(toast));
}