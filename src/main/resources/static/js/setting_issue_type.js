document.querySelector("body").addEventListener("click", function(e) {
	
	document.querySelector(".infobtn-popup.show")?.classList.remove("show");
	
	const removeBg = document.querySelectorAll(".fieldtype-infobtn");
	const removeColor = document.querySelectorAll(".infobtnimg");
	
	for(let i = 0; i < removeBg.length; i++){
		const bgItem = removeBg.item(i);
		const colorItem = removeColor.item(i);
		colorItem.style.filter = "invert(13%) sepia(37%) saturate(1705%) hue-rotate(189deg) brightness(94%) contrast(93%)";
		bgItem.style.backgroundColor = "white";
	}
	
	const infoBtnItem = e.target.closest(".fieldtype-infobtn");
	
	if(infoBtnItem !== null){
		infoBtnItem.children[1].style.filter = "invert(26%) sepia(69%) saturate(4766%) hue-rotate(209deg) brightness(96%) contrast(91%)";
		infoBtnItem.style.backgroundColor = "#E9F2FF";
		infoBtnItem.children[0].classList.add("show");
	}

});

document.querySelector("body").addEventListener("click", function(e) {
	
	document.querySelector(".titleinfobtn-popup.show")?.classList.remove("show");
	
	const removeBg = document.querySelectorAll(".fieldtitle-infobtn");
	const removeColor = document.querySelectorAll(".titleinfobtnimg");
	
	for(let i = 0; i < removeBg.length; i++){
		const bgItem = removeBg.item(i);
		const colorItem = removeColor.item(i);
		colorItem.style.filter = "invert(13%) sepia(37%) saturate(1705%) hue-rotate(189deg) brightness(94%) contrast(93%)";
		bgItem.style.backgroundColor = "#F7F8F9";
	}
	
	const infoBtnItem = e.target.closest(".fieldtitle-infobtn");
	
	if(infoBtnItem !== null){
		infoBtnItem.children[1].style.filter = "invert(26%) sepia(69%) saturate(4766%) hue-rotate(209deg) brightness(96%) contrast(91%)";
		infoBtnItem.style.backgroundColor = "#E9F2FF";
		infoBtnItem.children[0].classList.add("show");
	}

});

document.querySelector("body").addEventListener("click", function(e) {
	
	document.querySelector(".fieldoption-popup.show")?.classList.remove("show");
	
	const removeBg = document.querySelectorAll(".myfield-optionbtn");
	const removeColor = document.querySelectorAll(".fieldoptionbtnimg");
	
	for(let i = 0; i < removeColor.length; i++){
		const bgItem = removeBg.item(i);
		const colorItem = removeColor.item(i);
		colorItem.style.filter = "invert(13%) sepia(37%) saturate(1705%) hue-rotate(189deg) brightness(94%) contrast(93%)";
		bgItem.style.backgroundColor = "#091E420F";
	}
	
	const optionBtnItem = e.target.closest(".myfield-optionbtn");
	
	if(optionBtnItem !== null){
		const xyItem = optionBtnItem.getBoundingClientRect();
		
		if(xyItem.top < 600){
			optionBtnItem.children[1].style.filter = "invert(26%) sepia(69%) saturate(4766%) hue-rotate(209deg) brightness(96%) contrast(91%)";
			optionBtnItem.style.backgroundColor = "#E9F2FF";
			optionBtnItem.children[0].classList.add("show");
			optionBtnItem.children[0].style.top = "30px";
		}else{
			optionBtnItem.children[1].style.filter = "invert(26%) sepia(69%) saturate(4766%) hue-rotate(209deg) brightness(96%) contrast(91%)";
			optionBtnItem.style.backgroundColor = "#E9F2FF";
			optionBtnItem.children[0].classList.add("show");
			
			const popupXyItem = optionBtnItem.children[0].getBoundingClientRect();
			
			if(popupXyItem.height > 300){
				optionBtnItem.children[0].style.top = "30px";
			}else{
				optionBtnItem.children[0].style.bottom = "30px";
			}

		}
		
	}

});