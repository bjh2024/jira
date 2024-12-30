document.querySelector(".login_need_pw_box .password_input_box input").addEventListener("focus", function(){
	const inputBox = document.querySelector(".login_need_pw_box .password_input_box");
	inputBox.classList.add("active");
});

document.querySelector(".login_need_pw_box .password_input_box input").addEventListener("blur", function(){
	const inputBox = document.querySelector(".login_need_pw_box .password_input_box");
	if(this.value.length === 0){
		inputBox.classList.remove("active");
		return;
	}
	inputBox.classList.add("active");
});

document.querySelector(".login_need_pw_box .password_view_check_box input").addEventListener("change", function(){
	const passwordInput = document.querySelector(".login_need_pw_box .password_input_box input");
	if(this.checked){
		passwordInput.setAttribute("type", "text");
		return;
	}
	passwordInput.setAttribute("type", "password");
});


document.querySelector(".login_need_pw_box .btn_box button").addEventListener("click", function(){
	const uri = `/api/account/jira/add/login`;
	
	const email = document.querySelector("#username").value;
	const pw = document.querySelector("#password").value;
	const jiraIdx = this.getAttribute("idx-data");
	
	fetch(uri, {method: "post", 
				headers:{"Content-Type" : "application/json"}, 
				body:JSON.stringify({
					"email" : email,
					"password" : pw,
					"jiraIdx" : jiraIdx
	})})
	.then(res => res.json())
	.then(res => {
		if(res){
			location.href=`/`;
		}else{
			alert("비밀번호를 확인해주세요");
		}
	})
	.catch(err => {
		console.error(err);
	})
	
});