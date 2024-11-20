document.querySelector(".header-setbgimg").addEventListener("click", function(e) {
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