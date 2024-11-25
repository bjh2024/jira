document.querySelector(".header-setbgimg")?.addEventListener("click", function(e) {
	const setBgItem = e.target.closest(".header-setbgimgbtn");
	const removePopupItem = e.target.closest(".header-setbgbtn");
	
	if(setBgItem !== null){
		setBgItem.classList.add("active");
		setBgItem.nextElementSibling.classList.add("show");
	}
	
	if(removePopupItem !== null){
		document.querySelector(".header-setbgimgbtn").classList.remove("active");
		document.querySelector(".header-setbgimgbtn").nextElementSibling.classList.remove("show");
	}
});

document.querySelector(".header-setbtn")?.addEventListener("click", function(e){
	
	const btnItem = document.querySelector(".header-setbtn");
	const windowItem = document.querySelector(".header-menuwindow");
	
	if(btnItem !== null && e.target.closest(".header-menuwindow") == null){
		
		btnItem.classList.toggle("active");
		document.querySelector(".header-setbtn-icon").classList.toggle("color");
		// btnItem.children[1].style.filter = "invert(94%) sepia(93%) saturate(0%) hue-rotate(238deg) brightness(107%) contrast(105%)";
		windowItem.classList.toggle("show");
	}
});

document.querySelector(".menuwindow-optionbtn-bg")?.addEventListener("click", function(e){
	
	const btnItem = document.querySelector(".menuwindow-optionbtn-bg");
	const windowItem = document.querySelector(".header-setbgbox");
	
	if(btnItem !== null && e.target.closest(".header-setbgbox") == null){
		
		btnItem.classList.toggle("active");
		btnItem.children[1].classList.toggle("color");
		// btnItem.children[1].style.filter = "invert(94%) sepia(93%) saturate(0%) hue-rotate(238deg) brightness(107%) contrast(105%)";
		windowItem.classList.toggle("show");
	}
});

