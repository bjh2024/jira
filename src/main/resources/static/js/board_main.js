document.querySelectorAll(".editor").forEach(function(editor, index){
	const contentEditor = new toastui.Editor({
	    el: editor,
	    height: 'auto',
		minHeight: '74px',
	    initialEditType: 'wysiwyg',
	    initialValue: 'markdown',
	    previewStyle: 'vertical',
	  });
});

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

document.querySelectorAll(".subissuebtn").forEach(function(btn, index){
	btn.addEventListener("click", function(e) {
		
		const defBoxItem = document.querySelectorAll(".subissuebox.show");
		const defBtnItem = document.querySelectorAll(".subissuebtnimg.rotate");

		for(let i = 0; i < defBoxItem.length; i++){
			defBoxItem.item(i).classList.remove("show");
			defBtnItem.item(i).classList.remove("rotate");
		}
		
		const subissueBtn = e.target.closest(".subissuebtn");
		const subissueBtnIcon = document.querySelectorAll(".subissuebtnimg")[index];
		const subissueItem = document.querySelectorAll(".subissuebox")[index];

		if(subissueBtn !== null && subissueBtn.children[0].children[2].className.includes("rotate")){
			subissueItem.classList.remove("show");
			subissueBtnIcon.classList.remove("rotate");
			return;
		}
		
		if(subissueBtn !== null){
			subissueItem.classList.add("show");
			subissueBtnIcon.classList.add("rotate");
		}
	});
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
		if(e.target.closest(".subissuebtn") !== null || e.target.closest(".issue-menubtn") !== null
			|| e.target.closest(".subissuebox") !== null){
			return;
		}
		
		const lblCntItem = document.querySelectorAll(".issuedetail-graphval.label-def")[index]?.childElementCount;
		
		if(lblCntItem < 2){
			document.querySelectorAll(".graphval-label-def")[index].classList.remove("none");
		}else{
			document.querySelectorAll(".graphval-label-def")[index].classList.add("none");
		}
		
		const dlCntItem = document.querySelectorAll(".issuedetail-graphval.dl-def")[index]?.childElementCount;
				
		if(dlCntItem < 2){
			document.querySelectorAll(".graphval-dl-def")[index].classList.remove("none");
		}else{
			document.querySelectorAll(".graphval-dl-def")[index].classList.add("none");
		}
		
		const issueItem = document.querySelectorAll(".issues")[index];
		const issueDetailItem = document.querySelectorAll(".issuedetail-container")[index];
		
		if(issueItem !== null){
			issueDetailItem.classList.add("show");
		}
	});
});

document.querySelectorAll(".issuedetail-container").forEach(function(container, index){
	container.addEventListener("mousedown", function(e) {
		document.querySelectorAll(".issuedetail-container")[index]?.classList.remove("show");
		
		const bgItem = e.target.closest(".issuedetail-off");
		const issueDetailItem = e.target.closest(".issuedetailbox");
		
		const containerItem = document.querySelectorAll(".issuedetail-container")[index];
		
		if(bgItem == null && issueDetailItem !== null){
			containerItem.classList.add("show");
		}else{
			containerItem.classList.remove("show");
		}
	});
});



document.querySelectorAll(".issuedetail-exarea").forEach(function(area, index){
	area.addEventListener("click", function(e) {
		const areaItem = e.target.closest(".issuedetail-exarea");
		const editorItem = areaItem.children[0];
		const valItem = areaItem.children[1];
		const btnItem = e.target.closest(".editarea-cancel");
		
	
		if(btnItem !== null && editorItem !== null){
			editorItem.style.display = "none";
			valItem.style.display = "block";
			areaItem.style.padding = "6px 8px";
			return;
		}
		
		if(areaItem !== null){
			editorItem.style.display = "block";
			valItem.style.display = "none";
			areaItem.style.padding = "0px";
			editorItem.style.marginLeft = "8px";
		}
	});

});

document.querySelectorAll(".writereplybox").forEach(function(box, index){
	box.addEventListener("click", function(e) {
		const areaItem = e.target.closest(".writereplybox");
		const editorItem = areaItem.children[1];
		const btnItem = e.target.closest(".editarea-cancel");
		
	
		if(btnItem !== null && editorItem !== null){
			editorItem.style.display = "none";
			areaItem.style.padding = "6px 8px";
			return;
		}
		
		if(areaItem !== null){
			editorItem.style.display = "block";
			areaItem.style.padding = "0px";
			editorItem.style.marginLeft = "8px";
		}
	});

});

document.querySelectorAll(".issuedetail-replylist").forEach(function(btn, index){
	btn.addEventListener("click", function(e) {
		const areaItem = e.target.closest(".issuedetail-reply");
		const editorItem = areaItem.querySelector(".editor-container");
		const replyBtnItem = e.target.closest(".reply-geteditbtn");
		const btnItem = e.target.closest(".editarea-cancel");
		
		const barItem = areaItem.querySelector(".replydetail-managebar");
		const contentItem = areaItem.querySelector(".replydetail-content");
		
		
		if(btnItem !== null && editorItem !== null){
			editorItem.style.display = "none";
			barItem.style.display = "flex";
			contentItem.style.display = "block";
			return;
		}
		
		if(replyBtnItem !== null){
			editorItem.style.display = "block";
			editorItem.style.marginLeft = `${-1}px`;
			editorItem.style.marginTop = `${-16}px`;
			barItem.style.display = "none";
			contentItem.style.display = "none";
		}
	});

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

document.querySelectorAll(".rightdetail-subissue-status").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		const btnItem = e.target.closest(".rightdetail-subissue-status");
		const windowItem = btnItem.children[0];
		
		windowItem.classList.toggle("show");
	});

});

document.querySelector(".issuedetail-sortbtn").addEventListener("click", function(e){
	const btnItem = e.target.closest(".issuedetail-sortbtn");
	btnItem.classList.toggle("active");
});

document.querySelectorAll(".issues").forEach(function(box, index){
	box.addEventListener("dragstart", function(e){
		box.classList.add("dragging");
	});
	box.addEventListener("dragend", function(e){
		box.classList.remove("dragging");
	});
});

/*document.querySelectorAll(".issue-container").forEach(function(box, index){
	box.addEventListener("click", function(e){
		if(e.target.closest(".issuebox-moveicon") !== null){
			return;
		}
		
		const defItem = document.querySelectorAll(".issue-container");
		for(let i = 0; i < defItem.length; i++){
			
		}
		
		const boxItem = e.target.closest(".issue-container");
		if(boxItem !== null){
			boxItem.draggable = "false";
		}
	});
});*/

/*const columns = document.querySelectorAll(".board-body-container");

columns.forEach((column) => {
    new Sortable(column, {
        group: "shared",
        animation: 150,
        ghostClass: "blue-background-class"
    });
});*/

/*document.querySelectorAll(".issuebox-issues").forEach(function(box, index){
	box.addEventListener("dragover", function(e){
		e.preventDefault();
	    const afterElement = getDragAfterElement(box, e.clientX);
	    const draggable = document.querySelector(".dragging");
	    if (afterElement === undefined) {
	      box.appendChild(draggable);
	    } else {
	      box.insertBefore(draggable, afterElement);
	    }
	});
	
	box.addEventListener("drop", function(e){
			
	});
});*/

/*function getDragAfterElement(box, x) {
  const draggableElements = [
    ...box.querySelectorAll(".draggable:not(.dragging)"),
];

  return draggableElements.reduce(
    (closest, child) => {
      const box = child.getBoundingClientRect();
      const offset = x - box.left - box.width / 2;
      // console.log(offset);
      if (offset < 0 && offset > closest.offset) {
        return { offset: offset, element: child };
      } else {
        return closest;
      }
    },
    { offset: Number.NEGATIVE_INFINITY },
  ).element;
}*/