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
		const subissueBtnIcon = btn.children[0].children[2];
		const subissueItem = btn.parentElement.nextElementSibling;

		if(btn !== null && subissueItem.className.includes("show")){
			subissueItem.classList.remove("show");
			subissueBtn.children[0].children[2].classList.remove("rotate");
			return;
		}
		
		if(btn !== null){
			subissueItem.classList.add("show");
			subissueBtnIcon.classList.add("rotate");
		}
	});
});

document.querySelectorAll(".subissues").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		const lblItem = btn.querySelector(".issuedetail-graphval.label-def");
		
		if(lblItem?.childElementCount < 2){
			lblItem.querySelector(".graphval-label-def").classList.remove("none");
		}else{
			lblItem.querySelector(".graphval-label-def").classList.add("none");
		}
		
		const dlItem = btn.querySelector(".issuedetail-graphval.dl-def");
				
		if(dlItem?.childElementCount < 3){
			dlItem.querySelector(".graphval-dl-def").classList.remove("none");
		}else{
			dlItem.querySelector(".graphval-dl-def").classList.add("none");
		}
		
		if(btn !== null){
			btn.querySelector(".subissuedetail-container").classList.add("show");
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
		
		const lblItem = btn.querySelector(".issuedetail-graphval.label-def");
		
		if(lblItem?.childElementCount < 2){
			lblItem.querySelector(".graphval-label-def").classList.remove("none");
		}else{
			lblItem.querySelector(".graphval-label-def").classList.add("none");
		}
		
		const dlItem = btn.querySelector(".issuedetail-graphval.dl-def");
				
		if(dlItem?.childElementCount < 3){
			dlItem.querySelector(".graphval-dl-def").classList.remove("none");
		}else{
			dlItem.querySelector(".graphval-dl-def").classList.add("none");
		}
		
		const issueItem = document.querySelectorAll(".issues")[index];
		const issueDetailItem = document.querySelectorAll(".issuedetail-container")[index];
		
		if(issueItem !== null){
			btn.querySelector(".issuedetail-container").classList.add("show");
		}
	});
});

document.querySelectorAll(".issuedetail-container").forEach(function(container, index){
	container.addEventListener("mousedown", function(e) {
		document.querySelectorAll(".issuedetail-container")[index]?.classList.remove("show");
		
		const bgItem = e.target.closest(".issuedetail-off");
		const subIssueItem = e.target.closest(".subissue-list");
		const issueDetailItem = e.target.closest(".issuedetailbox");
		
		if(bgItem == null && issueDetailItem !== null){
			
			container.classList.add("show");
		}else{
			container.classList.remove("show");
			/*if(e.target.closest(".subissue-list-rightdetail") !== null){
				container.classList.add("show");	
			}*/
		}
	});
});

document.querySelectorAll(".subissuedetail-container").forEach(function(container, index){
	container.addEventListener("mousedown", function(e) {
		document.querySelectorAll(".issuedetail-container")[index]?.classList.remove("show");
		
		const bgItem = e.target.closest(".issuedetail-off");
		const issueDetailItem = e.target.closest(".issuedetailbox");
		
		if(bgItem == null && issueDetailItem !== null){
			container.classList.add("show");
		}else{
			container.classList.remove("show");
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

document.querySelectorAll(".subissue-list").forEach(function(list, index){
	list.addEventListener("click", function(e){
		if(e.target.closest(".subissue-list-rightdetail") !== null){
			return;
		}
		if(list !== null){
			list.closest(".issuedetail-container")?.classList.remove("show");
			document.querySelectorAll(".subissuedetail-container")[index]?.classList.add("show");
		}
	});
});

document.querySelectorAll(".issuedetail-statusbtn").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		if(e.target.closest(".issuedetail-statuswindow") !== null){
			return;
		}
		btn.children[0].classList.toggle("show");
	})
});

/*// Event listener for status change
document.querySelectorAll('.statuswindow-status').forEach(statusElement => {
    statusElement.addEventListener('click', function (event) {
        // Get the clicked statuswindow-title element
        const titleElement = event.target.closest('.statuswindow-status').querySelector('.statuswindow-title');

        if (titleElement !== null) {
            // Extract data attributes
            const projectIdx = titleElement.getAttribute('data-project-idx');
            const issueIdx = titleElement.getAttribute('data-issue-idx');
            const newStatus = titleElement.getAttribute('data-issue-status');

            // Construct the payload for the update request
            const payload = {
                projectIdx: projectIdx,
                issueIdx: issueIdx,
                status: newStatus
            };

            // Make an AJAX request to update the status
            fetch('/api/project/updateStatus', {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            })
            .then(response => response.json())
            .then(statusList => {
                console.log('Update successful:', data);

                // Optional: Update the UI to reflect the new status
                const statusButton = statusElement.parentElement.parentElement.parentElement.children[1];
                statusButton.textContent = titleElement.textContent; // Update the button's displayed status
            })
            .catch(error => {
                console.error('Error:', error);
            });

            // Toggle the visibility of the status window
            const statusWindow = titleElement.closest('.issuedetail-statuswindow');
            if (statusWindow) {
                statusWindow.classList.toggle('show');
            }
        }
    });
});*/


/*document.querySelectorAll('.statuswindow-status').forEach(function(statusElement) {
        statusElement.addEventListener('click', function() {
            // 클릭한 statuswindow-status에서 상태 정보 가져오기
            const titleElement = statusElement.querySelector('.statuswindow-title');
            const status = titleElement.getAttribute('data-status');
            const statusName = titleElement.getAttribute('data-status-name');
			
            // 상태에 따른 클래스를 설정
            let statusBtn = statusElement.parentNode.parentNode.parentNode;
			
            // 기존의 상태 클래스를 제거
            statusBtn.classList.remove('status1', 'status2', 'status3');
			
            // 새로운 상태 클래스를 추가
            if (status == 1) {
                statusBtn.classList.add('status1');
            } else if (status == 2) {
                statusBtn.classList.add('status2');
            } else if (status == 3) {
                statusBtn.classList.add('status3');
            }

            // 버튼 내부 텍스트를 업데이트 (선택한 상태 이름)
            statusBtn.children[1].innerHTML = statusName;

            // issuedetail-statuswindow 클래스에 show 토글
            const statusWindow = statusElement.parentNode.parentNode;
            statusWindow.classList.toggle('show');
        });
    });*/


document.querySelectorAll(".issuedetail-insertbtn").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		const btnItem = e.target.closest(".issuedetail-insertbtn");
			const windowItem = btnItem.children[0];
			btnItem.classList.toggle("active");
			windowItem.classList.toggle("show");
	});
});

document.querySelectorAll(".rightdetail-subissue-status").forEach(function(btn, index){
	btn.addEventListener("mousedown", function(e){
		if(e.target.closest(".statuswindow-menubox") !== null){
			return;
		}
		const windowItem = btn.children[0];
		
		windowItem.classList.toggle("show");
	});

});

document.querySelector(".issuedetail-sortbtn").addEventListener("click", function(e){
	const btnItem = e.target.closest(".issuedetail-sortbtn");
	btnItem.classList.toggle("active");
});

document.querySelectorAll(".issues").forEach(function(box, index){
	box.addEventListener("dragstart", function(e){
		if(e.target.closest(".subissuebtn") !== null){
			return;
		}
		box.classList.add("dragging");
	});
	box.addEventListener("dragend", function(e){
		box.classList.remove("dragging");
	});
});

document.querySelectorAll(".issuedetail-graphval").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		if(e.target.closest(".graphval-selectwindow") !== null){
			return;
		}
		btn.querySelector(".graphval-selectwindow").classList.toggle("show");

	});
	
	/*btn.addEventListener("mouseover", function(e){
		
	});
	
	btn.addEventListener("mouseout", function(e){
		
	});*/
});

let labelDatas = {
	"idx": []
}

function fetchInput(){
	let url = "/api/project/get_label_list";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(labelDatas)
	})
	.then(response => response.json())
	.then(alterLabelList => {
		console.log(alterLabelList);
		const uniqueLabels = alterLabelList.filter((value, index, self) =>
           index === self.findIndex(item => item.name === value.name)
       );
		document.querySelectorAll(".graphval-selectwindow.label").forEach(function(label, index){
			label.innerHTML = "";
			const labelTitle = document.createElement("span");
			labelTitle.innerHTML = `<span class="graphval-selectwindow-title">모든 레이블</span>`;
			label.appendChild(labelTitle);
			
			uniqueLabels.forEach(function(value){	
				const labelValue = document.createElement("div");
				labelValue.classList.add("graphvalwindow-value");
				labelValue.innerHTML = `<span>${value.name}</span>`;
				label.appendChild(labelValue);
			});
		});
	}).catch(error => {
		console.error("Fetch error:", error);
	});
}

document.querySelectorAll(".issuedetail-graphval.label-def").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		if(e.target.closest(".graphval-selectwindow") !== null){
			return;
		}
		
		let label = [...btn.querySelectorAll(".send-label-value")].map(input => input.value);
		labelDatas.idx = label;
		fetchInput();
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

const columns = document.querySelectorAll(".issuebox-issues");

columns.forEach((column) => {
    new Sortable(column, {
        group: "shared",
        animation: 150,
        ghostClass: "blue-background-class"
    });
});

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