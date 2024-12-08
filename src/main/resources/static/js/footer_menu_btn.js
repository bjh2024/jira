document.querySelector("body").addEventListener("click", function(e) {
	if(e.target.closest(".show")?.className.includes("show")){
		return;
	}
	document.querySelector(".selectwindow.show")?.classList.remove("show");

	const createIssueItem = e.target.closest(".btn-container");
	if(createIssueItem !== null){
		createIssueItem.children[0].classList.toggle("show");
	}
});

function autoResizeTextarea(element) {
	element.style.height = 'auto';
	element.style.height = element.scrollHeight + 'px';
}