window.onload = function(){
	const uri = window.location.pathname;
	if(uri.includes("/info")){
		document.querySelector(".setting_lnb .info").classList.add("active");
	}else if(uri.includes("/access")){
		document.querySelector(".setting_lnb .access").classList.add("active");
	}else if(uri.includes("/issue_type")){
		const moreBox = document.querySelector(".issue_type_box");
		moreBox.classList.toggle("show");
		const issueTypeBtns = moreBox.querySelectorAll("a");
		issueTypeBtns.forEach(function(btn){
			if(btn.getAttribute("href") === uri.split("/")[5]){
				btn.classList.add("active");
			}
		});
	}
}

document.querySelector(".setting_aside .issue_type").addEventListener("click", function(){
	const img = this.querySelector("img");
	let src = "";
	if(img.getAttribute("src") === "/images/arrow_right_icon.svg"){
		src = "/images/arrow_under_icon.svg"
	}else{
		src = "/images/arrow_right_icon.svg"
	}
	img.setAttribute("src", src);
	const moreBox = document.querySelector(".issue_type_box");
	moreBox.classList.toggle("show");
});