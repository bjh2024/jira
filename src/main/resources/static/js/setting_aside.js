window.onload = function(){
	const uri = window.location.pathname;
	if(uri.includes("/info")){
		document.querySelector(".setting_lnb .info").classList.add("active");
	}else if(uri.includes("/access")){
		document.querySelector(".setting_lnb .access").classList.add("active");
	}else if(uri.includes("/issue_type")){
		document.querySelector(".setting_lnb .issue_type").classList.add("active");
	}
}