let isClick = false;
document.querySelector(".footer-btn").addEventListener("click", function(){
	document.querySelectorAll(".footer-btn-item").forEach(function(container){
		container.classList.toggle("hidden");
		if(isClick){
			isClick = !isClick;
		}
	});
});

// chat_bot 이벤트
document.querySelector("body").addEventListener("click", function(e) {
	
	if(e.target.closest(".chat-bot") === null){
		document.querySelector(".selectwindow.show")?.classList.remove("show");
		return;
	}
	
	if(e.target.closest(".show")?.className.includes("show")){
		return;
	}
	

	const createIssueItem = e.target.closest(".btn-container");
	if(createIssueItem !== null){
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

function getAIAnswerFetch(body){
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

document.querySelectorAll(".question-inputbox").forEach(function(form, index){
	form.addEventListener("keyup", function(e){
		const form = document.querySelector(".questionform");
		
		const issueTitleItem = e.target.closest(".question-inputbox");
		if(issueTitleItem.value != "" && issueTitleItem.value != null){
			document.querySelectorAll(".searchbtn")[index].style.backgroundColor = "#0C66E4";
			document.querySelectorAll(".searchbtn")[index].style.color = "white";
			document.querySelectorAll(".searchbtn")[index].style.cursor = "pointer";
		}else{
			document.querySelectorAll(".searchbtn")[index].style.backgroundColor = "#091E4208";
			document.querySelectorAll(".searchbtn")[index].style.color = "#A5ADBA";
			document.querySelectorAll(".searchbtn")[index].style.cursor = "not-allowed";
		}
		
		if(window.event.keyCode == 13){
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

function submitForm(e){
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