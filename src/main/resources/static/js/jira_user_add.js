// modal창 끄기
const jiraUserModal = document.querySelector(".jira_user_add_modal");
// modal창 껴졌을때 input 지우기
function removeJiraInput(){
	const input = document.querySelector(".jira_user_add_box .input_box input");
	input.value = "";
}

jiraUserModal.addEventListener("click", function(e) {
	if (e.target.closest(".jira_user_add_container") === null) {
		removeJiraInput()
		this.classList.remove("show");
	}
});

document
	.querySelector(".jira_user_add_box > .btn_box .cancle_btn")
	.addEventListener("click", function() {
		removeJiraInput()
		jiraUserModal.classList.remove("show");
	});