document.querySelector("body").addEventListener("click", function(e) {
	const account = e.target.closest(".account_modify");
	const modal = document.querySelector(".modal");
	const active = document.querySelector(".modal.active")
	const modalContent = document.querySelector(".modal_content")
	console.log(e.target.closest(".modal_content"));
	console.log(e.target.closest(".modal_active"));
	if(account){
		modal.classList.add("active");
	}	
	if (modal.classList.contains("active") && !modalContent.contains(e.target) && !e.target.closest(".account_modify")) {
	        modal.classList.remove("active");
	    }
});