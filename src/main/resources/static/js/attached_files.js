document.querySelectorAll(".attached-file-box").forEach(function(box, index){
	box.addEventListener("mouseover", function(e){
		const fileBoxItem = e.target.closest(".attached-file-box");
		if(fileBoxItem !== null){
			document.querySelectorAll(".attached-file-detail")[index].style.backgroundColor = "#F1F2F4";
			document.querySelectorAll(".attached-file-options")[index].style.display = "flex";
		}
	});
	
	box.addEventListener("mouseout", function(e){
		const fileBoxItem = e.target.closest(".attached-file-box");
		if(fileBoxItem !== null){
			document.querySelectorAll(".attached-file-detail")[index].style.backgroundColor = "white";
			document.querySelectorAll(".attached-file-options")[index].style.display = "none";
		}
	});
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
		bgItem.style.backgroundColor = "#F1F2F4";
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

document.querySelectorAll(".attached-file-box").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		if(e.target.closest(".attached-file-btn") !== null){
			return;
		}
		
		document.querySelector(".file-detail-container.show")?.classList.remove("show");
		
		const issueItem = document.querySelectorAll(".attached-file-box")[index];
		const issueDetailItem = document.querySelectorAll(".file-detail-container")[index];
		
		if(issueItem !== null){
			issueDetailItem.classList.add("show");
		}
	})
});

document.querySelectorAll(".file-detail-container").forEach(function(container, index){
	container.addEventListener("mousedown", function(e) {
		if(e.target.closest(".file-download-btn") !== null || e.target.closest(".footer-risize-btns") !== null){
			return;
		}
		
		document.querySelectorAll(".file-detail-container")[index]?.classList.remove("show");
		
		const bgItem = e.target.closest(".file-exit-btn");
		const issueDetailItem = e.target.closest(".file-detail");
		
		if(bgItem == null && issueDetailItem !== null){
			container.classList.add("show");
		}else{
			container.classList.remove("show");
			container.querySelector(".file-detail").style.zoom = "100%";
			container.querySelector(".footer-percent").innerText = "100%";
			nowZoom = 100;
		}
	});
});

let nowZoom = 100;

document.querySelectorAll(".resizebtn").forEach(function(btn){
	btn.addEventListener("click", function(e){
		const detailItem = e.target.closest(".file-detail-container.show").querySelector(".file-detail");
		const perCentItem = e.target.closest(".file-detail-container.show").querySelector(".footer-percent");
		if(btn.className.includes("in")){
			nowZoom = nowZoom + 10;
			perCentItem.innerText = `${nowZoom}%`;
			detailItem.style.zoom = nowZoom + "%";
		}else{
			nowZoom = nowZoom - 10;
			perCentItem.innerText = `${nowZoom}%`;
			detailItem.style.zoom = nowZoom + "%";
		}
	})
});

document.querySelectorAll(".attached-file-btn.delete").forEach(function(btn){
	btn.addEventListener("click", function(e){
		fileIdxData.fileIdx = btn.parentElement.dataset.fileidx;
		
		document.querySelector(".file-delete-alert-container").classList.add("show");
	});
});

let fileIdxData = {
	"fileIdx": ""
}

document.querySelector(".file-delete-alert-container").addEventListener("mousedown", function(e) {
	document.querySelector(".file-delete-alert-container")?.classList.remove("show");
	
	const bgItem = e.target.closest(".delete-alert-cancelbtn");
	const detailItem = e.target.closest(".delete-alert-box");
	
	const submitItem = e.target.closest(".delete-alert-submitbtn");
	if(submitItem !== null){
		fetch(`/api/issue_files/delete/${fileIdxData.fileIdx}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                alert('파일 삭제 실패');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('파일 삭제 중 오류 발생');
        });
	}
	
	if(bgItem == null && detailItem !== null){
		document.querySelector(".file-delete-alert-container").classList.add("show");
	}else{
		document.querySelector(".file-delete-alert-container").classList.remove("show");
	}
});