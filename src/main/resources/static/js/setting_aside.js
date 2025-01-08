window.onload = function(){
	const uri = window.location.pathname;
	if(uri.includes("/info")){
		document.querySelector(".setting_lnb .info").classList.add("active");
	}else if(uri.includes("/access")){
		document.querySelector(".setting_lnb .access").classList.add("active");
	}else if(uri.includes("/issue_type")){
		const moreBox = document.querySelector(".issue_type_box");
		moreBox.classList.toggle("show");
		const issueTypeBtns = moreBox.querySelectorAll("a");
		issueTypeBtns.forEach(function(btn){
			if(btn.getAttribute("href").split("/")[5] === uri.split("/")[5]){
				btn.classList.add("active");
			}
		});
	}
}

document.querySelector(".setting_aside .issue_type").addEventListener("click", function(){
	const img = this.querySelector("img");
	let src = "";
	if(img.getAttribute("src") === "/images/arrow_right_icon.svg"){
		src = "/images/arrow_under_icon.svg"
	}else{
		src = "/images/arrow_right_icon.svg"
	}
	img.setAttribute("src", src);
	const moreBox = document.querySelector(".issue_type_box");
	moreBox.classList.toggle("show");
});

document.querySelector(".setting_aside_btn.insert").addEventListener("click", function(e){
	const btnItem = e.target.closest(".setting_aside_btn.insert");
	if(btnItem !== null){
		document.querySelector(".create_issuetype_container").classList.add("show");
	}
});

document.querySelector(".create_issuetype_container").addEventListener("mousedown", function(e) {
	document.querySelector(".create_issuetype_container")?.classList.remove("show");
	
	const bgItem = e.target.closest(".create_issuetype_btn.cancel");
	const detailItem = e.target.closest(".create_issuetype_box");
	
	if(bgItem == null && detailItem !== null){
		document.querySelector(".create_issuetype_container").classList.add("show");
	}else{
		document.querySelector(".create_issuetype_container").classList.remove("show");
	}
});

document.querySelector(".set_issuetype_name_input").onfocus = function() {
	document.querySelector(".set_issuetype_name_input").parentElement.classList.add("active");
};

document.querySelector(".set_issuetype_name_input").onblur = function() {
	document.querySelector(".set_issuetype_name_input").parentElement.classList.remove("active");
};

document.querySelector(".set_issuetype_content_area").onfocus = function() {
	document.querySelector(".set_issuetype_content_area").classList.add("active");
};

document.querySelector(".set_issuetype_content_area").onblur = function() {
	document.querySelector(".set_issuetype_content_area").classList.remove("active");
};

document.querySelector(".issuetype_icon_selectbtn").addEventListener("click", function(e){
	const btn = e.target.closest(".issuetype_icon_selectbtn");
	if(btn !== null){
		btn.children[0].classList.toggle("show");
	}
});

document.querySelectorAll(".issuetype_icon_option").forEach(function(btn){
	btn.addEventListener("click", function(e){
		const selectedType = document.querySelector(".issuetype_icon_selected");
		selectedType.dataset.iconfilename = btn.dataset.iconfilename;
		selectedType.children[0].src = `/images/${btn.dataset.iconfilename}`;
	});
});

// input 값이 존재할 때에만 submit 버튼 활성화
document.querySelectorAll(".set_issuetype_name_input").forEach(function(form, index){
	form.addEventListener("keyup", function(e){
		
		const issueTitleItem = e.target.closest(".set_issuetype_name_input");
		if(issueTitleItem.value != "" && issueTitleItem.value != null){
			document.querySelector(".create_issuetype_btn.submit").classList.add("active");
		}else{
			document.querySelector(".create_issuetype_btn.submit").classList.remove("active");
		}
	})
});

let createIssueTypeData = {
	"projectIdx": "",
	"name": "",
	"content":  "",
	"iconFilename": ""
}

function verificationIssueType(){
	let url = "/api/project/verification_issueType";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(createIssueTypeData)
	}).then(response => response.json())
	.then(isExist => {
		if(isExist){
			document.querySelector(".issuetype_duplicate_alert")?.classList.remove("show");
			createIssueType();
		}else{
			document.querySelector(".issuetype_duplicate_alert").classList.add("show");
		}
	}).catch(error => {
			console.error("Fetch error:", error);
	});
}

function createIssueType(){
	let url = "/api/project/create_issueType";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(createIssueTypeData)
	})
	.then(response => {
        if (response.ok) {
            location.reload();
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
    }).catch(error => {
		console.error("Fetch error:", error);
	});
}

document.querySelector(".create_issuetype_btn.submit").addEventListener("click", function(e){
	if(e.target.closest(".create_issuetype_btn.submit").className.includes("active")){
		createIssueTypeData.name = document.querySelector(".set_issuetype_name_input").value;
		createIssueTypeData.content = document.querySelector(".set_issuetype_content_area").value;
		createIssueTypeData.projectIdx = document.querySelector(".issuetype_icon_selected").dataset.projectidx;
		createIssueTypeData.iconFilename = document.querySelector(".issuetype_icon_selected").dataset.iconfilename;
		verificationIssueType();
	}
});
