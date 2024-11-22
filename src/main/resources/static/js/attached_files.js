document.querySelectorAll(".attached-file-box").forEach(function(box, index){
	box.addEventListener("mouseover", function(e){
		
		const fileBoxItem = e.target.closest(".attached-file-box");
		if(fileBoxItem !== null){
			document.querySelectorAll(".attached-file-detail")[index].style.backgroundColor = "#F1F2F4";
			document.querySelectorAll(".attached-file-options")[index].style.display = "flex";
		}
	})
});

document.querySelectorAll(".attached-file-box").forEach(function(box, index){
	box.addEventListener("mouseout", function(e){
		
		const fileBoxItem = e.target.closest(".attached-file-box");
		if(fileBoxItem !== null){
			document.querySelectorAll(".attached-file-detail")[index].style.backgroundColor = "white";
			document.querySelectorAll(".attached-file-options")[index].style.display = "none";
		}
	})
});

document.querySelector("body").addEventListener("click", function(e) {
	
	document.querySelector(".attached-headerwindow.show")?.classList.remove("show");
	document.querySelector(".attached-headerwindow-user.show")?.classList.remove("show");
	
	const removeBg = document.querySelectorAll(".attached-headerbtn");
	const removeColor = document.querySelectorAll(".attached-headerbtn-icon");
	
	for(let i = 0; i < removeBg.length; i++){
		const bgItem = removeBg.item(i);
		const colorItem = removeColor.item(i);
		colorItem.style.filter = "none";
		colorItem.style.filter = "invert(14%) sepia(10%) saturate(4925%) hue-rotate(184deg) brightness(96%) contrast(94%)";
		bgItem.style.backgroundColor = "#091E420F";
		bgItem.style.color = "#172B4D";
	}
	
	const headerBtnItem = e.target.closest(".attached-headerbtn");
	
	if(headerBtnItem !== null){
		headerBtnItem.children[1].style.filter = "invert(100%) sepia(0%) saturate(14%) hue-rotate(193deg) brightness(103%) contrast(103%)";
		headerBtnItem.style.backgroundColor = "#2C3E5D";
		headerBtnItem.style.color = "white";
		headerBtnItem.children[0].classList.add("show");
	}

});