document.querySelector("body").addEventListener("click", function(e) {
	if(e.target.closest(".show")?.className.includes("show")){
		return;
	}
	
	document.querySelector(".sidebarbtn.active")?.classList.remove("active");
	document.querySelector(".btnwindow.show")?.classList.remove("show");
	document.querySelector(".btnwindow-filter.show")?.classList.remove("show");

	const sidebarItem = e.target.closest(".sidebarbtn");
	if(sidebarItem !== null){
		sidebarItem.classList.add("active");
		sidebarItem.children[0].classList.add("show");
	}
});

document.querySelector(".subissuebtn").addEventListener("click", function(e) {
	if(e.target.closest(".show")?.className.includes("show")){
		return;
	}
	document.querySelector(".subissuebox.show")?.classList.remove("show");
	document.querySelector(".subissuebtnimg").classList.remove("rotate");

	const subissueBtn = e.target.closest(".subissuebtn");
	const subissueBtnIcon = document.querySelector(".subissuebtnimg");
	const subissueItem = document.querySelector(".subissuebox");
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
		const issueCreateItem = e.target.closest(".createissuebtn");
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
		
		const temp1 = 500;
		const temp2 = xyItem.top;
		
		console.log(xyItem.x);
 		if(issueTypeBtn !== null){
			issueTypeList.classList.add("show");
			if(xyItem.top >= 500){
				console.log("fefef");
				issueTypeList.style.bottom = xyItem.top;
				issueTypeList.style.left = xyItem.left;
				issueTypeList.style.marginBottom = "60px";
				issueTypeList.style.marginLeft = "0px";
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
		document.querySelector(".issuedetail-container.show")?.classList.remove("show");
		
		const issueItem = document.querySelectorAll(".issues")[index];
		const issueDetailItem = document.querySelector(".issuedetail-container");

		const createIssueItem = e.target.closest(".issuebox-create");
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

	document.querySelector(".editor-container.show")?.classList.remove("show");
	document.querySelector(".issuedetail-exvalue")?.classList.add("show");
	
	const areaItem = e.target.closest(".issuedetail-exarea");
	const editorItem = document.querySelector(".editor-container");
	const btnItem = e.target.closest(".editarea-cancel");
	

	if(btnItem !== null && editorItem !== null){
		document.querySelector(".editor-container").style.display = "none";
		areaItem.style.padding = "6px 8px";
		return;
	}
	
	if(areaItem !== null){
		areaItem.children[0].style.display = "block";
		areaItem.children[1].classList.remove("show");
		areaItem.style.padding = "0px";
		areaItem.children[0].style.marginLeft = "8px";
	}
});

document.querySelector(".writereplybox").addEventListener("click", function(e) {

	document.querySelector(".editor-container.show")?.classList.remove("show");
	
	const areaItem = e.target.closest(".writereplybox");
	const editorItem = document.querySelector(".editor-container");
	const btnItem = e.target.closest(".editarea-cancel");
	

	if(btnItem !== null && editorItem !== null){
		areaItem.children[1].style.display = "none";
		return;
	}
	
	if(areaItem !== null){
		areaItem.children[1].style.display = "block";
		areaItem.children[1].style.width = `${690}px`;
		areaItem.children[2].classList.remove("show");
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