// modal 끄기
const teamCreateModal = document.querySelector(".team_create_modal");

// modal창 껴졌을때 input 지우기
function removeTeamInput() {
	const inputs = document.querySelectorAll(".team_create_content_box .input_box input");
	const checkbox = document.querySelector(".team_create_content_box .input_check_box .check_box input");
	inputs.forEach(function(input) {
		input.value = "";
	});
	checkbox.checked = false;
}

teamCreateModal.addEventListener("click", function(e) {
	if (e.target.closest(".team_create_container") === null) {
		removeTeamInput();
		this.classList.remove("show");
	}
})

document
	.querySelector(".team_create_container .btn_box .cancle_btn")
	.addEventListener("click", function() {
		removeTeamInput();
		teamCreateModal.classList.remove("show");
	})