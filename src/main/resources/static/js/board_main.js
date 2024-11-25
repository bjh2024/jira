document.querySelector("body").addEventListener("click", function(e) {
	if(e.target.closest(".show")?.className.includes("show")){
		return;
	}
	
	document.querySelector(".sidebarbtn.active")?.classList.remove("active");
	document.querySelector(".sidebarbtn-filter.active")?.classList.remove("active");
	document.querySelector(".sidebarbtn-other.active")?.classList.remove("active");
	document.querySelector(".btnwindow.show")?.classList.remove("show");
	document.querySelector(".btnwindow-filter.show")?.classList.remove("show");

	document.querySelector(".sidebarbtnicon-filter").style.filter = "none";
	document.querySelector(".sidebarbtnicon-other").style.filter = "none";
	
	const sidebarGroupItem = e.target.closest(".sidebarbtn-group");
	const sidebarFilterItem = e.target.closest(".sidebarbtn-filter");
	const sidebarOtherItem = e.target.closest(".sidebarbtn-other");
	
	if(sidebarGroupItem !== null){
		sidebarGroupItem.children[0].classList.add("show");
	}
	
	if(sidebarFilterItem !== null){
		sidebarFilterItem.classList.add("active");
		sidebarFilterItem.children[0].classList.add("show");
		document.querySelector(".sidebarbtnicon-filter").style.filter = "invert(100%) sepia(1%) saturate(7498%) hue-rotate(57deg) brightness(102%) contrast(102%)";
	}
	
	if(sidebarOtherItem !== null){
		sidebarOtherItem.classList.add("active");
		sidebarOtherItem.children[0].classList.add("show");
		document.querySelector(".sidebarbtnicon-other").style.filter = "invert(100%) sepia(1%) saturate(7498%) hue-rotate(57deg) brightness(102%) contrast(102%)";
	}
});

document.querySelector(".subissuebtn").addEventListener("click", function(e) {
	if(e.target.closest(".show")?.className.includes("show")){
		return;
	}
	document.querySelector(".subissuebox.show")?.classList.remove("show");
	document.querySelector(".subissuebtnimg.rotate")?.classList.remove("rotate");

	const subissueBtn = e.target.closest(".subissuebtn");
	const subissueBtnIcon = document.querySelector(".subissuebtnimg");
	const subissueItem = document.querySelector(".subissuebox");
	// subissueItem.classList.toggle("show");
	// subissueBtnIcon.classList.toggle("rotate");
	
	if(subissueBtn !== null){
		subissueItem.classList.add("show");
		subissueBtnIcon.classList.add("rotate");
	}

});

document.querySelector("body").addEventListener("click", function(e) {
	if(e.target.closest(".show")?.className.includes("show")){
		return;
	}
	document.querySelector(".createissuebox.show")?.classList.remove("show");
	document.querySelector(".issuetypeselectbox.show")?.classList.remove("show");
	// document.querySelector(".create-issuekey").value = null;

	const createIssueItem = e.target.closest(".issuebox-create");
	if(createIssueItem !== null){
		createIssueItem.previousElementSibling.classList.add("show");
	}
});


document.querySelectorAll(".create-issuekey").forEach(function(form, index){
	form.addEventListener("keyup", function(e){
		
		const issueTitleItem = e.target.closest(".create-issuekey");
		if(issueTitleItem.value != "" && issueTitleItem.value != null){
			document.querySelectorAll(".createissuebtn")[index].style.backgroundColor = "#0C66E4";
			document.querySelectorAll(".createissuebtn")[index].style.color = "white";
			document.querySelectorAll(".createissuebtn")[index].style.cursor = "pointer";
		}else{
			document.querySelectorAll(".createissuebtn")[index].style.backgroundColor = "#091E4208";
			document.querySelectorAll(".createissuebtn")[index].style.color = "#A5ADBA";
			document.querySelectorAll(".createissuebtn")[index].style.cursor = "not-allowed";
		}
	})
});

document.querySelectorAll(".createissue-typebtn").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		
		// document.querySelector(".issuetypeselectbox.show")[index].classList.remove("show");
		e.target.closest(".issuetypeselectbox.show")?.classList.remove("show");
		
		const issueTypeBtn = document.querySelectorAll(".createissue-typebtn")[index];
		const issueTypeList = document.querySelectorAll(".issuetypeselectbox")[index];
		const xyItem = issueTypeBtn.getBoundingClientRect();
		
 		if(issueTypeBtn !== null){
			issueTypeList.classList.add("show");
			if(xyItem.top >= 600){
				issueTypeList.style.marginTop = "26px";
				issueTypeList.style.marginLeft = "-242px";
			}else{
				issueTypeList.style.left = xyItem.left;
				issueTypeList.style.top = xyItem.top;
				issueTypeList.style.marginLeft = "16px";
				issueTypeList.style.marginTop = "60px";
			}
		}else{
			issueTypeList.classList.remove("show");
		}
	})
});

document.querySelectorAll(".issues").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		if(e.target.closest(".subissuebtn") !== null || e.target.closest(".issue-menubtn") !== null){
			return;
		}
		
		document.querySelector(".issuedetail-container.show")?.classList.remove("show");
		
		const issueItem = document.querySelectorAll(".issues")[index];
		const issueDetailItem = document.querySelector(".issuedetail-container");
		
		if(issueItem !== null){
			issueDetailItem.classList.add("show");
		}
	})
});

document.querySelector(".issuedetail-container").addEventListener("mouseup", function(e) {

	document.querySelector(".issuedetail-container.show")?.classList.remove("show");
	
	const bgItem = e.target.closest(".issuedetail-off");
	const issueDetailItem = e.target.closest(".issuedetailbox");
	
	if(bgItem == null && issueDetailItem !== null){
		document.querySelector(".issuedetail-container").classList.add("show");
	}else{
		document.querySelector(".issuedetail-container").classList.remove("show");
	}
});


document.querySelector(".issuedetail-exarea").addEventListener("click", function(e) {

	
	const areaItem = e.target.closest(".issuedetail-exarea");
	const editorItem = document.querySelector(".editor-container");
	const btnItem = e.target.closest(".editarea-cancel");
	

	if(btnItem !== null && editorItem !== null){
		document.querySelector(".editor-container").style.display = "none";
		document.querySelector(".issuedetail-exvalue").style.display = "block";
		areaItem.style.padding = "6px 8px";
		return;
	}
	
	if(areaItem !== null){
		areaItem.children[0].style.display = "block";
		areaItem.children[1].style.display = "none";
		areaItem.style.padding = "0px";
		areaItem.children[0].style.marginLeft = "8px";
	}
});

document.querySelector(".writereplybox").addEventListener("click", function(e) {

	const areaItem = e.target.closest(".writereplybox");
	const editorItem = document.querySelector(".editor-container");
	const btnItem = e.target.closest(".editarea-cancel");
	

	if(btnItem !== null && editorItem !== null){
		areaItem.children[1].style.display = "none";
		areaItem.children[2].style.display = "block";
		return;
	}
	
	if(areaItem !== null){
		areaItem.children[1].style.display = "block";
		areaItem.children[1].style.width = `${690}px`;
		areaItem.children[2].style.display = "none";
	}
});

document.querySelector(".issuedetail-replylist").addEventListener("click", function(e) {

	document.querySelector(".editor-container.show")?.classList.remove("show");
	
	const areaItem = e.target.closest(".issuedetail-reply");
	const editorItem = document.querySelector(".editor-container");
	const replyBtnItem = e.target.closest(".reply-geteditbtn");
	const btnItem = e.target.closest(".editarea-cancel");
	
	const contentItem = e.target.closest(".replydetail-content");
	const manageItem = e.target.closest(".replydetail-managebar");

	if(btnItem !== null && editorItem !== null){
		areaItem.children[4].style.display = "none";
		areaItem.children[3].style.display = "flex";
		document.querySelector(".replydetail-content").style.display = "block";
		return;
	}
	
	if(replyBtnItem !== null){
		areaItem.children[4].style.display = "block";
		areaItem.children[4].style.width = `${695}px`;
		areaItem.children[4].style.marginLeft = `${-1}px`;
		areaItem.children[4].style.marginTop = `${-16}px`;
		areaItem.children[3].style.display = "none";
		document.querySelector(".replydetail-content").style.display = "none";
	}
});

document.querySelectorAll(".issuedetail-statusbtn").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		btn.children[0].classList.toggle("show");
	})
});

document.querySelector(".issuedetail-insertbtn").addEventListener("click", function(e){
	const btnItem = e.target.closest(".issuedetail-insertbtn");
	const windowItem = btnItem.children[0];
	btnItem.classList.toggle("active");
	windowItem.classList.toggle("show");
});

document.querySelector(".issuedetail-sortbtn").addEventListener("click", function(e){
	const btnItem = e.target.closest(".issuedetail-sortbtn");
	btnItem.classList.toggle("active");
});