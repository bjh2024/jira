document.querySelector("body").addEventListener("click", function(e) {
	const account = e.target.closest(".account_modify");
	const modal = document.querySelector(".modal");
	const modalContent = document.querySelector(".modal_content")
	const cancleButton = document.querySelector(".newPasswordCancle")
	if(account){
		modal.classList.add("active");
	}	
	if (modal.classList.contains("active") && !modalContent.contains(e.target) && !e.target.closest(".account_modify")) {
	        modal.classList.remove("active");
	}else if(e.target == cancleButton){
	        modal.classList.remove("active");
	}
	
});

let changePw = {
	"oldPw" : null,
	"newPw" : null,
	"email" : null
}
document.querySelector(".newPassword").addEventListener("click",function(){
	changePw.oldPw = document.querySelector("input[name=oldpw]").value
	changePw.newPw = document.querySelector("input[name=newpw]").value
	changePw.email = document.querySelector(".newPassword").dataset.email
	UpdatePassword()
})

function UpdatePassword(){
	let url = "/api/account/password";
	fetch(url,{
		method : 'POST',
		headers : {
			'Content-Type' : 'application/json'
		},
		body : JSON.stringify({
			oldPw : changePw.oldPw,
			newPw : changePw.newPw,
			email : changePw.email
		})
	}).then(res => res.json())
	  .then(pw => {
		if(pw === true){
			window.location.href = "/account/logout";
		}else{
			alert("변경에 실패했습니다")
		}
	  }).catch(err => {
		console.error("Fetch error:", err);
	  })
}

