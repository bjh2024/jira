let stompClient = null;
function connection(chatRoomDTOList){
	if(chatRoomDTOList.length !== 0){
		let socket = new SockJS("/websocket-endpoint");
		stompClient = Stomp.over(socket);
		
		stompClient.connect({}, function(frame){
			console.log("연결 성공" + frame);
			
			chatRoomDTOList.forEach(function(chatRoomDTO){
				const topic = `/topic/chat/${chatRoomDTO.chatRoom.idx}`
				stompClient.subscribe(topic, function(msg){
					const messageInfo = JSON.parse(msg.body)
					const chatDetailContainer = document.querySelector(".chat_detail_container");
					const myAccountIdx = chatDetailContainer.getAttribute("account-idx-data");
					const chatRoomIdx = chatDetailContainer.getAttribute("chat-room-idx-data");
					let sendDateTitle = dateTitleFormat(messageInfo.sendDate);
					if(messageInfo.chatRoomIdx == chatRoomIdx){
						chatDetailContainer.innerHTML += `<div class="chat_detail_box">
																${prevSendDate === sendDateTitle ?
															'' :
															`<div class="date_title">
																		<span>${sendDateTitle}</span>
																	</div>`
																 }
																${messageInfo.accountIdx !== Number(myAccountIdx) ?
															`<div class="other_chat_box">
																	<div class="img_box">
																		<img src="/images/${messageInfo.accountIconFilename}" width="24" height="24" />
																	</div>
																	<div class="chat_detail">
																		<p>${messageInfo.content}</p>
																		<span class="date">${dateDetailFormat(messageInfo.sendDate)}</span>
																	</div>
																</div>`
															: `<div class="my_chat_box">
																	<div class="chat_detail">
																		<span class="date">${dateDetailFormat(messageInfo.sendDate)}</span>
																		<p>${messageInfo.content}</p>
																	</div>
																	<div class="img_box">
																		<img src="/images/${messageInfo.accountIconFilename}" width="24" height="24" />
																	</div>
																</div>`}
															</div>`;
						if (prevSendDate !== sendDateTitle)
								prevSendDate = sendDateTitle;
					}
					document.querySelector(".chat-room-content").scrollTop = chatDetailContainer.scrollHeight;
				});
			});
		});
	}
}

function sendMessage(message, chatRoomIdx) {
    stompClient.send(`/app/chat/${chatRoomIdx}`, {}, JSON.stringify(message));
}

window.onload = async function(){
	const uri = `/api/chat/room/list`;
	const chatRoomDTOList = await chatRoomListFetch(uri);
	
	connection(chatRoomDTOList);
}

let isFooterClick = false;
document.querySelector(".footer-btn").addEventListener("click", function() {
	const img = this.querySelector("img");
	if (!isFooterClick) {
		img.setAttribute("src", "/images/arrow_right_icon.svg");
	} else {
		img.setAttribute("src", "/images/logo2_icon.svg");
	}
	isFooterClick = !isFooterClick;
	document.querySelectorAll(".footer-btn-item").forEach(function(container) {
		container.classList.toggle("hidden");
	});
});

// chat_bot 이벤트
document.querySelector("body").addEventListener("click", function(e) {
	if (e.target.closest(".chat-bot") === null) {
		document.querySelector(".selectwindow.show")?.classList.remove("show");
		return;
	}

	if (e.target.closest(".show")?.className.includes("show")) {
		return;
	}


	const createIssueItem = e.target.closest(".btn-container");
	if (createIssueItem !== null) {
		createIssueItem.children[0].classList.toggle("show");
	}
});

function autoResizeTextarea(element) {
	element.style.height = 'auto';
	element.style.height = element.scrollHeight + 'px';
}

input = {
	"question": ""
}

function getAIAnswerFetch(body) {
	let url = "/api/project/get_ai_answer";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json', // JSON으로 전송
		},
		body: JSON.stringify(input) // 객체를 JSON 문자열로 변환
	})
		.then(response => response.json())
		.then(data => {
			// 로딩 알림 채팅을 불러와서 삭제
			const loadDiv = document.getElementById("load-answer");
			loadDiv.remove();

			const answerDiv = document.createElement("div");
			answerDiv.classList.add("answer-container");
			answerDiv.innerHTML = `<div class="answerbox">
									<div>${data.answer}</div>
								 </div>`;
			body.appendChild(answerDiv);
		})
		.catch(error => {
			console.error("Error:", error);
		});
}

document.querySelectorAll(".question-inputbox").forEach(function(form, index) {
	form.addEventListener("keyup", function(e) {
		const form = document.querySelector(".questionform");

		const issueTitleItem = e.target.closest(".question-inputbox");
		if (issueTitleItem.value != "" && issueTitleItem.value != null) {
			document.querySelectorAll(".searchbtn")[index].style.backgroundColor = "#0C66E4";
			document.querySelectorAll(".searchbtn")[index].style.color = "white";
			document.querySelectorAll(".searchbtn")[index].style.cursor = "pointer";
		} else {
			document.querySelectorAll(".searchbtn")[index].style.backgroundColor = "#091E4208";
			document.querySelectorAll(".searchbtn")[index].style.color = "#A5ADBA";
			document.querySelectorAll(".searchbtn")[index].style.cursor = "not-allowed";
		}

		if (window.event.keyCode == 13) {
			const questionItem = document.querySelector(".question-inputbox");
			const bodyItem = document.querySelector(".selectwindow-body");

			const questionDiv = document.createElement("div");
			questionDiv.classList.add("question-container");
			questionDiv.innerHTML = `<div class="questionbox">
										<div>${questionItem.value}</div>
									 </div>`;

			const loadDiv = document.createElement("div");
			loadDiv.classList.add("answer-container");
			loadDiv.id = "load-answer";
			loadDiv.innerHTML = `<div class="answerbox">
		 							<div>답변 작성 중</div>
		 							<img src="/images/three_dots_icon_small.svg" style="filter: invert(16%) sepia(81%) saturate(3341%) hue-rotate(211deg) brightness(109%) contrast(100%);">
		 						 </div>`;


			bodyItem.appendChild(questionDiv);
			bodyItem.appendChild(loadDiv);
			input.question = questionItem.value;
			questionItem.value = "";
			form.style.height = "18px";
			getAIAnswerFetch(bodyItem);

		}
	});
});

function submitForm(e) {
	const question = document.querySelector(".question-inputbox");
	const bodyItem = document.querySelector(".selectwindow-body");
	const form = document.querySelector(".questionform");

	const questionDiv = document.createElement("div");
	questionDiv.classList.add("question-container");
	questionDiv.innerHTML = `<div class="questionbox">
								<div>${question.value}</div>
							 </div>`;

	const loadDiv = document.createElement("div");
	loadDiv.classList.add("answer-container");
	loadDiv.id = "load-answer";
	loadDiv.innerHTML = `<div class="answerbox">
							<div>답변 작성 중</div>
							<img src="/images/three_dots_icon_small.svg" style="filter: invert(16%) sepia(81%) saturate(3341%) hue-rotate(211deg) brightness(109%) contrast(100%);">
						 </div>`;

	bodyItem.appendChild(questionDiv);
	bodyItem.appendChild(loadDiv);
	question.value = "";
	form.style.height = "18px";
	getAIAnswerFetch(bodyItem);
}

// chat-room 이벤트
document.querySelector("body").addEventListener("click", function(e) {
	const chatRoomBtn = e.target.closest(".chat-room");
	const chatRoomContent = document.querySelector(".chat-room-conatiner");
	if (chatRoomBtn === null) {
		chatRoomContent.classList.remove("show");
		return;
	}
	if (e.target.closest(".chat-room-conatiner") !== null) {
		return;
	}
	if (chatRoomContent.className.includes("show")) {
		chatRoomContent.classList.remove("show");
		return;
	}
	chatRoomContent.classList.add("show");
});

function lastSendDate(date) {
	if (typeof date === "string") {
		date = new Date(date);
	}

	const today = new Date();
	if (dateTitleFormat(today) === dateTitleFormat(date)) {
		return dateDetailFormat(date);
	} else {
		return dateTitleFormat(date);
	}
}

async function chatRoomListFetch(uri) {
	const container = document.querySelector(".chat-room-content .room_list");
	try{
		const res = await fetch(uri, {method: "get" });
		const chatRoomDTOList = await res.json();
			if (chatRoomDTOList.length != 0) {
				container.innerHTML = "";
				chatRoomDTOList.forEach(function(chatRoomDTO) {
					const itemDiv = document.createElement("div");
					itemDiv.classList.add("item");
					itemDiv.setAttribute("chat-room-idx-data", chatRoomDTO.chatRoom.idx);
					itemDiv.innerHTML = `<div class="img_container ${chatRoomDTO.accountList.length == 1 ? 'alone' : ''}">
										</div>
										<div class="chat-content">
											<p>
												<span class="title">
													<span>${chatRoomDTO.chatRoom.name}</span>
													<span class="last-date">${lastSendDate(chatRoomDTO.lastSendDate)}</span>
												</span>
												<span class="last-chat">${chatRoomDTO.lastContent}</span>
											</p>
										</div>`
					const imgContainer = itemDiv.querySelector(".img_container");
					const accountList = chatRoomDTO.accountList;
					accountList.forEach(function(account) {
						imgContainer.innerHTML += `<div class="img_box">
													<img src="/images/${account.iconFilename}" width="24" height="24" />
													<span>${account.name}</span>
												</div>`
					});
					container.appendChild(itemDiv);
				});
			}
			return chatRoomDTOList;
		}catch(err){
			console.error(err);
		}
}

document.querySelectorAll(".chat-room-gnb .img_box").forEach(function(btn, gnbIdx) {
	btn.addEventListener("click", function() {
		const activeImgBox = document.querySelector(".chat-room-gnb .img_box.active");
		activeImgBox.classList.remove("active");
		this.classList.add("active");

		if (this.className.includes("friend_btn")) {
			const uri = `/api/chat/friend/list`;
		} else {
			if (document.querySelector(".chat-room-content .room_list").className.includes("show")) return;
			const uri = `/api/chat/room/list`;
			chatRoomListFetch(uri);
		}

		const contentBoxs = document.querySelectorAll(".chat-room-content .content");
		contentBoxs.forEach(function(box, contentIdx) {
			box.classList.remove("show");
			if (contentIdx === gnbIdx) {
				box.classList.add("show");
			}
		});
	});
});

function dateTitleFormat(date) {
	if (typeof date === "string") {
		date = new Date(date);
	}
	return date.getFullYear() + '년' + (date.getMonth() + 1) + '월' + date.getDate() + '일';
}

function dateDetailFormat(date) {
	if (typeof date === "string") {
		date = new Date(date);
	}

	let hours = "";
	if (date.getHours() <= 12) {
		hours = `오전 ${date.getHours()}`
	} else {
		hours = `오후 ${date.getHours() - 12}`
	}
	let minute = date.getMinutes() < 10 ? `0${date.getMinutes()}` : date.getMinutes();

	return hours + ':' + minute;
}

let prevSendDate = dateTitleFormat(new Date());
async function chatMessageFetch(uri, chatRoomIdx) {

	const res = await fetch(uri, { method: "get" });
	const chatMessageList = await res.json();
	try {
		const chatRoomList = document.querySelector(".chat-room-content .room_list");
		const dynamicChatRoom = document.querySelector(".chat-room-content .dynamic_chat");
		chatRoomList.classList.remove("show");
		dynamicChatRoom.classList.add("show");

		const chatDetailContainer = dynamicChatRoom.querySelector(".chat_detail_container");
		const myAccountIdx = chatDetailContainer.getAttribute("account-idx-data");
		chatDetailContainer.setAttribute("chat-room-idx-data", chatRoomIdx);
		chatDetailContainer.innerHTML = "";
		
		if (chatMessageList.length !== 0) {
			prevSendDate = dateTitleFormat(chatMessageList[0].sendDate);
			chatMessageList.forEach(function(messageInfo, idx) {
				let sendDateTitle = dateTitleFormat(messageInfo.sendDate);
				chatDetailContainer.innerHTML += `
						<div class="chat_detail_box">
							${idx !== 0 && prevSendDate === sendDateTitle ?
						'' :
						`<div class="date_title">
									<span>${sendDateTitle}</span>
								</div>`
							 }
							${messageInfo.accountIdx !== Number(myAccountIdx) ?
						`<div class="other_chat_box">
								<div class="img_box">
									<img src="/images/${messageInfo.accountIconFilename}" width="24" height="24" />
								</div>
								<div class="chat_detail">
									<p>${messageInfo.content}</p>
									<span class="date">${dateDetailFormat(messageInfo.sendDate)}</span>
								</div>
							</div>`
						: `<div class="my_chat_box">
								<div class="chat_detail">
									<span class="date">${dateDetailFormat(messageInfo.sendDate)}</span>
									<p>${messageInfo.content}</p>
								</div>
								<div class="img_box">
									<img src="/images/${messageInfo.accountIconFilename}" width="24" height="24" />
								</div>
							</div>`}
						</div>`
				if (prevSendDate !== sendDateTitle)
					prevSendDate = sendDateTitle;
			});
		}
	}catch(err){
		console.error(err);
	}
}

document.querySelector(".chat-room-content .room_list").addEventListener("click", async function(e) {
	const chatRoomItem = e.target.closest(".item");
	if (chatRoomItem === null) return;
	const chatRoomIdx = chatRoomItem.getAttribute("chat-room-idx-data");
	const uri = `/api/chat/room/detail?chatRoomIdx=${chatRoomIdx}`;

	await chatMessageFetch(uri, chatRoomIdx);

	let chatDetailContainer = document.querySelector(".chat_detail_container");
	document.querySelector(".chat-room-content").scrollTop = chatDetailContainer.scrollHeight;
});

document.querySelector(".dynamic_chat .chat_input_box textarea").addEventListener("keydown", function(e) {
	const sendText = this.value;
	
	if(e.key === "Enter" && !e.shiftKey){
		e.preventDefault();
		// 서버에 데이터 보내기
		if(sendText.trim().length === 0) return;
		const chatDetailContainer = document.querySelector(".chat_detail_container");
		const accountIdx = chatDetailContainer.getAttribute("account-idx-data");
		const accountIconFilename = chatDetailContainer.getAttribute("account-iconFile-name-data");
		const chatRoomIdx = chatDetailContainer.getAttribute("chat-room-idx-data");
		
		const requestMessageInfo = {
			"chatRoomIdx": chatRoomIdx,
			"accountIdx": accountIdx,
			"accountIconFilename": accountIconFilename,
			"content": sendText,
			"sendDate":new Date()
		}
		sendMessage(requestMessageInfo, chatRoomIdx);
		this.value = "";
	}
});

