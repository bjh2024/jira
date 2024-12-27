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
	"newPw" : null
}
document.querySelector(".newPassword").addEventListener("click",function(){
	changePw.oldPw = document.querySelector("input[name=oldpw]").value
	changePw.newPw = document.querySelector("input[name=newpw]").value
	UpdatePassword()
})

function UpdatePassword(){
	let url = "/api/filter_issue_table/project_filter";
}


