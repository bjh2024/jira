document.querySelector("body").addEventListener("click", function(e) {
	const menu = e.target.closest(".filter_menu");  // 클릭된 메뉴 찾기
	const menuWithShow = document.querySelector(".filter_issue_box.show");  // 열린 .filter_issue_box 찾기
	const menuBoxWithActive = document.querySelector(".filter_category_div.active");  // 활성화된 메뉴 찾기
	const button = document.querySelector(".filter_category_div");
	
	// 이미 열린 .filter_issue_box를 클릭한 경우 아무 작업도 하지 않음
	if (menuWithShow && menuWithShow.contains(e.target)) {
		return;
	}

	// 열린 메뉴가 있다면 그 메뉴의 show 클래스를 제거하고 스타일 초기화
	if (menuWithShow) {
					menuWithShow.classList.remove("show");  // 열린 메뉴 숨기기
					if (menuBoxWithActive) {
							menuBoxWithActive.classList.remove("active");  // 활성화된 메뉴 스타일 초기화
						}
					return;
				}

	// 클릭된 메뉴가 존재하면 해당 메뉴 열기
	if (menu !== null) {
		const filterBox = menu.querySelector(".filter_issue_box");  // 클릭한 메뉴의 filter_issue_box 찾기
		const menuBox = menu.querySelector(".filter_category_div");  // 해당 메뉴의 부모 .filter_category_div 찾기

		
		// 이미 열린 상태라면 메뉴 닫기
		if (filterBox.classList.contains("show")) {
			filterBox.classList.remove("show");  // 메뉴 닫기
			menuBox.classList.remove("active");  // 스타일 초기화
			
		} else {
			// 클릭된 메뉴 열기
			filterBox.classList.add("show");  // 해당 메뉴 열기
			menuBox.classList.add("active");  // 메뉴 스타일 활성화
		}
	
	}
});

document.querySelector("body").addEventListener("click", function(e) {
	const account = e.target.closest(".filter_save_button");
	const modal = document.querySelector(".modal");
	const active = document.querySelector(".modal.active")
	const modalContent = document.querySelector(".modal_content")
	const cancleButton = modalContent.querySelector(".cancle_button")
	if (account) {
		modal.classList.add("active");
	}
	if(modal.classList.contains("active")){
		if(cancleButton && cancleButton.contains(e.target)){
			modal.classList.remove("active");
		}else if (!modalContent.contains(e.target) && !e.target.closest(".filter_save_button")) {
			modal.classList.remove("active");
		}
		
	}
		
});

document.querySelector(".button_div")?.addEventListener("click", function(e) {
	const issueListFilter = e.target.closest(".button_div");
	const issueListFilterbox = document.querySelector(".button_div_box");
	const issueListFiltershow = document.querySelector(".button_div_box.show");
	
	if(issueListFilterbox){
		issueListFilterbox.classList.toggle("show");
	}
		
});
document.querySelector(".user_div_1")?.addEventListener("click",function(e){
	event.stopPropagation()
	let view = e.target.closest(".user_div_1"); 
	let view3 = view.querySelector(".user_div_category");
	if (view3) {
	      let view2Category = view.querySelector(".user_div_category");
	      let style = window.getComputedStyle(view2Category); 
	      if (style.display === "block" ) {
	        view2Category.style.display = "none";
			view.style.border = "1px solid #091E424F"
	      } else {
	        view2Category.style.display = "block";
			view.style.border = "1px solid #0C66E4"
	      }
	    }
})
document.querySelector(".user_div_2")?.addEventListener("click",function(e){
	event.stopPropagation()
	let view = e.target.closest(".user_div_2"); 
	let view3 = view.querySelector(".user_div_category");
	if (view3) {
	      let view2Category = view.querySelector(".user_div_category");
	      let style = window.getComputedStyle(view2Category); 
	      if (style.display === "block" ) {
	        view2Category.style.display = "none";
			view.style.border = "1px solid #091E424F"
	      } else {
	        view2Category.style.display = "block";
			view.style.border = "1px solid #0C66E4"
	      }
	    }
})
document.querySelector(".project_div_1")?.addEventListener("click",function(e){
	event.stopPropagation()
	let view = e.target.closest(".project_div_1"); 
	let view3 = view.querySelector(".project_div_category");
	if (view3) {
	      let view2Category = view.querySelector(".project_div_category");
	      let style = window.getComputedStyle(view2Category); 
	      if (style.display === "block" ) {
	        view2Category.style.display = "none";
			view.style.border = "1px solid #091E424F"
	      } else {
	        view2Category.style.display = "block";
			view.style.border = "1px solid #0C66E4"
	      }
	    }
})
document.querySelector(".project_div_2")?.addEventListener("click",function(e){
	event.stopPropagation()
	let view = e.target.closest(".project_div_2"); 
	let view3 = view.querySelector(".project_div_category");
	if (view3) {
	      let view2Category = view.querySelector(".project_div_category");
	      let style = window.getComputedStyle(view2Category); 
	      if (style.display === "block" ) {
	        view2Category.style.display = "none";
			view.style.border = "1px solid #091E424F"
	      } else {
	        view2Category.style.display = "block";
			view.style.border = "1px solid #0C66E4"
	      }
	    }
})
document.querySelector(".team_div_1")?.addEventListener("click",function(e){
	event.stopPropagation()
	let view = e.target.closest(".team_div_1"); 
	let view3 = view.querySelector(".team_div_category");
	if (view3) {
	      let view2Category = view.querySelector(".team_div_category");
	      let style = window.getComputedStyle(view2Category); 
	      if (style.display === "block" ) {
	        view2Category.style.display = "none";
			view.style.border = "1px solid #091E424F"
	      } else {
	        view2Category.style.display = "block";
			view.style.border = "1px solid #0C66E4"
	      }
	    }
})
document.querySelector(".team_div_2")?.addEventListener("click",function(e){
	event.stopPropagation()
	let view = e.target.closest(".team_div_2"); 
	let view3 = view.querySelector(".team_div_category");
	if (view3) {
	      let view2Category = view.querySelector(".team_div_category");
	      let style = window.getComputedStyle(view2Category); 
	      if (style.display === "block" ) {
	        view2Category.style.display = "none";
			view.style.border = "1px solid #091E424F"
	      } else {
	        view2Category.style.display = "block";
			view.style.border = "1px solid #0C66E4"
	      }
	    }
})
document.querySelectorAll(".viewer_div_1")?.forEach(function(item){
  item.addEventListener("click", function(e){
    let view = e.target.closest(".viewer_div_1");  // 클릭된 요소를 바로 가져옵니다.

    let view1 = view.querySelector(".viewer_div_1_category");
		
    if (view1) {
      let view1Category = view.querySelector(".viewer_div_1_category");
      let style = window.getComputedStyle(view1Category);
      if (style.display === "block") {
        view1Category.style.display = "none";
		view.style.border = "1px solid #091E424F"
      } else {
        view1Category.style.display = "block";
		view.style.border = "1px solid #0C66E4"
      }
    }
  });
});
const label1 = document.querySelector('.label1');
const options = document.querySelectorAll('.optionItem');
const label2 = document.querySelector('.label2');
const options2 = document.querySelectorAll('.optionItem2');
const projectLabel = document.querySelector('.projectLabel');
const projectLabel2 = document.querySelector('.projectLabel2');
const projectItem = document.querySelectorAll('.projectItem');
const projectItem2 = document.querySelectorAll('.projectItem2');
const teamLabel = document.querySelector('.teamLabel');
const teamLabel2 = document.querySelector('.teamLabel2');
const teamItem = document.querySelectorAll('.teamItem');
const teamItem2 = document.querySelectorAll('.teamItem2');
const userLabel = document.querySelector('.userLabel');
const userLabel2 = document.querySelector('.userLabel2');
const userItem = document.querySelectorAll('.userItem');
const userItem2 = document.querySelectorAll('.userItem2');

// 클릭한 옵션의 텍스트를 라벨 안에 넣음
const handleSelect = (item) => {
  label1.innerHTML = ''; // 기존 내용 제거 (이미지 및 텍스트)
  // 클릭한 옵션에서 이미지와 텍스트 가져오기
  const img = item.querySelector('img');
  const text = item.textContent.trim(); // 텍스트 가져오기
  if (img) {
    const imgClone = img.cloneNode(true); // 이미지를 복제
    label1.appendChild(imgClone); // 라벨에 복제한 이미지 추가
  }
  label1.innerHTML += text; // 라벨에 텍스트 추가
}

const handleSelect2 = (item) => {
  label2.innerHTML = ''; // 기존 내용 제거 (이미지 및 텍스트)  
  // 클릭한 옵션에서 이미지와 텍스트 가져오기
  const img = item.querySelector('img');
  const text = item.textContent.trim(); // 텍스트 가져오기
  if (img) {
    const imgClone = img.cloneNode(true); // 이미지를 복제
    label2.appendChild(imgClone); // 라벨에 복제한 이미지 추가
  }
  label2.innerHTML += text; // 라벨에 텍스트 추가
}
const handleSelect3 = (item) => {
  projectLabel.innerHTML = ''; // 기존 내용 제거 (이미지 및 텍스트)  
  // 클릭한 옵션에서 이미지와 텍스트 가져오기
  const img = item.querySelector('img');
  const text = item.textContent.trim(); // 텍스트 가져오기

  if (img) {
    const imgClone = img.cloneNode(true); // 이미지를 복제
    projectLabel.appendChild(imgClone); // 라벨에 복제한 이미지 추가
  }
  projectLabel.innerHTML += text; // 라벨에 텍스트 추가
}
const handleSelect3_1 = (item) => {
  projectLabel2.innerHTML = ''; // 기존 내용 제거 (이미지 및 텍스트)  
  // 클릭한 옵션에서 이미지와 텍스트 가져오기
  const img = item.querySelector('img');
  const text = item.textContent.trim(); // 텍스트 가져오기

  if (img) {
    const imgClone = img.cloneNode(true); // 이미지를 복제
    projectLabel2.appendChild(imgClone); // 라벨에 복제한 이미지 추가
  }
  projectLabel2.innerHTML += text; // 라벨에 텍스트 추가
}
const handleSelect4 = (item) => {
  teamLabel.innerHTML = ''; // 기존 내용 제거 (이미지 및 텍스트)  
  
  const img = item.querySelector('img');
   const text = item.textContent.trim(); // 텍스트 가져오기

   if (img) {
     const imgClone = img.cloneNode(true); // 이미지를 복제
     teamLabel.appendChild(imgClone); // 라벨에 복제한 이미지 추가
   }
  teamLabel.innerHTML += text; // 라벨에 텍스트 추가
}
const handleSelect4_1 = (item) => {
	teamLabel2.innerHTML = ''; // 기존 내용 제거 (이미지 및 텍스트)  
	  
	  const img = item.querySelector('img');
	   const text = item.textContent.trim(); // 텍스트 가져오기

	   if (img) {
	     const imgClone = img.cloneNode(true); // 이미지를 복제
	     teamLabel2.appendChild(imgClone); // 라벨에 복제한 이미지 추가
	   }
	  teamLabel2.innerHTML += text; // 라벨에 텍스트 추가
}
const handleSelect5 = (item) => {
	userLabel.innerHTML = ''; // 기존 내용 제거 (이미지 및 텍스트)  
	 // 클릭한 옵션에서 이미지와 텍스트 가져오기
	 const img = item.querySelector('img');
	 const text = item.textContent.trim(); // 텍스트 가져오기

	 if (img) {
	   const imgClone = img.cloneNode(true); // 이미지를 복제
	   userLabel.appendChild(imgClone); // 라벨에 복제한 이미지 추가
	 }
	 userLabel.innerHTML +=text; // 라벨에 텍스트 추가
}
const handleSelect5_1 = (item) => {
	userLabel2.innerHTML = ''; // 기존 내용 제거 (이미지 및 텍스트)  
	 // 클릭한 옵션에서 이미지와 텍스트 가져오기
	 const img = item.querySelector('img');
	 const text = item.textContent.trim(); // 텍스트 가져오기

	 if (img) {
	   const imgClone = img.cloneNode(true); // 이미지를 복제
	   userLabel2.appendChild(imgClone); // 라벨에 복제한 이미지 추가
	 }
	 userLabel2.innerHTML +=text; // 라벨에 텍스트 추가
}

// 옵션 클릭시 클릭한 옵션을 넘김
options.forEach(option => {
	option.addEventListener('click', () => handleSelect(option))
})
options2.forEach(option => {
	option.addEventListener('click', () => handleSelect2(option))
})
projectItem.forEach(option => {
	option.addEventListener('click', () => handleSelect3(option))
})
projectItem2.forEach(option => {
	option.addEventListener('click', () => handleSelect3_1(option))
})
teamItem.forEach(option => {
	option.addEventListener('click', () => handleSelect4(option))
})
teamItem2.forEach(option => {
	option.addEventListener('click', () => handleSelect4_1(option))
})
userItem.forEach(option => {
	option.addEventListener('click', () => handleSelect5(option))
})
userItem2.forEach(option => {
	option.addEventListener('click', () => handleSelect5_1(option))
})

// 라벨을 클릭시 옵션 목록이 열림/닫힘
document.querySelectorAll(".viewer_div_1_category").forEach(function(item) {
  item.addEventListener("click", function(e) {
    e.stopPropagation();	
	if (e.target.classList.contains("optionItem")) {
	    if (e.target.innerText.trim() === "프로젝트") {
	      document.querySelector(".project_div_1").style.display = "flex";
	      document.querySelector(".team_div_1").style.display = "none";
	      document.querySelector(".user_div_1").style.display = "none";
		  document.querySelector(".teamLabel").innerHTML = 
		  		  `<img src="/images/default_team_icon_file.svg" style="width: 24px; margin:0px 4px;">
		  			그룹 선택`;
		 document.querySelector(".userLabel").innerHTML = 
		 		  `<img src="/images/default_user_icon_file.svg" style="width: 24px; margin:0px 4px;">
		 			사용자 선택`;
	    } else if (e.target.innerText.trim() === "그룹") {
	      document.querySelector(".team_div_1").style.display = "flex";
	      document.querySelector(".project_div_1").style.display = "none";
	      document.querySelector(".user_div_1").style.display = "none";
		  document.querySelector(".projectLabel").innerHTML = 
		   		  `<img src="/images/default_project_icon_file.svg" style="width: 24px; margin:0px 4px;">
		  	    프로젝트 선택`;
		  document.querySelector(".userLabel").innerHTML = 
		  		  `<img src="/images/default_user_icon_file.svg" style="width: 24px; margin:0px 4px;">
		  			사용자 선택`;
	    } else if (e.target.innerText.trim() === "사용자") {
	      document.querySelector(".user_div_1").style.display = "flex";
	      document.querySelector(".team_div_1").style.display = "none";
	      document.querySelector(".project_div_1").style.display = "none";
		  document.querySelector(".teamLabel").innerHTML = 
		  		  `<img src="/images/default_team_icon_file.svg" style="width: 24px; margin:0px 4px;">
		  			그룹 선택`;
					document.querySelector(".projectLabel").innerHTML = 
					 		  `<img src="/images/default_project_icon_file.svg" style="width: 24px; margin:0px 4px;">
						    프로젝트 선택`;
	    } else if (e.target.innerText.trim() === "비공개") {
	      document.querySelector(".user_div_1").style.display = "none";
	      document.querySelector(".team_div_1").style.display = "none";
	      document.querySelector(".project_div_1").style.display = "none";
	      document.querySelector(".userLabel").innerHTML = 
		  `<img src="/images/default_user_icon_file.svg" style="width: 24px; margin:0px 4px;">
			사용자 선택`;
	      document.querySelector(".teamLabel").innerHTML = 
		  `<img src="/images/default_team_icon_file.svg" style="width: 24px; margin:0px 4px;">
			그룹 선택`;
	      document.querySelector(".projectLabel").innerHTML = 
  		  `<img src="/images/default_project_icon_file.svg" style="width: 24px; margin:0px 4px;">
		    프로젝트 선택`;
	    }
		let viewerCategory = document.querySelector(".viewer_div_1_category");
		    let displayStyle = getComputedStyle(viewerCategory).display;
		    
		    if (displayStyle === "block") {
		      viewerCategory.style.display = "none";
		      document.querySelector(".viewer_div_1").style.border = "1px solid #091E424F";
		      return;
		    }
  	 }else if(e.target.classList.contains("optionItem2")){
		
		if(e.target.innerText === "프로젝트"){
				document.querySelector(".project_div_2").style.display = "flex";
				document.querySelector(".team_div_2").style.display = "none";
				document.querySelector(".teamLabel2").innerHTML = 
				`<img src="/images/default_team_icon_file.svg" style="width: 24px; margin:0px 4px;">
							그룹 선택`;
				document.querySelector(".user_div_2").style.display = "none";
				document.querySelector(".userLabel2").innerHTML = 
				`<img src="/images/default_user_icon_file.svg" style="width: 24px; margin:0px 4px;">
						사용자 선택`;
			}else if(e.target.innerText === "그룹"){
				document.querySelector(".team_div_2").style.display = "flex";
				document.querySelector(".project_div_2").style.display = "none";
				document.querySelector(".projectLabel2").innerHTML = 
				`<img src="/images/default_project_icon_file.svg" style="width: 24px; margin:0px 4px;">
					    프로젝트 선택`;
				document.querySelector(".user_div_2").style.display = "none";
				document.querySelector(".userLabel2").innerHTML = 
				`<img src="/images/default_user_icon_file.svg" style="width: 24px; margin:0px 4px;">
						사용자 선택`;
			}else if(e.target.innerText === "사용자"){
				document.querySelector(".user_div_2").style.display = "flex";
				document.querySelector(".team_div_2").style.display = "none";
				document.querySelector(".teamLabel2").innerHTML = 
				`<img src="/images/default_team_icon_file.svg" style="width: 24px; margin:0px 4px;">
							그룹 선택`;
				document.querySelector(".project_div_2").style.display = "none";
				document.querySelector(".projectLabel2").innerHTML = 
				`<img src="/images/default_project_icon_file.svg" style="width: 24px; margin:0px 4px;">
					    프로젝트 선택`;
			}else if(e.target.innerText === "비공개"){
				document.querySelector(".user_div_2").style.display = "none";
				document.querySelector(".team_div_2").style.display = "none";
				document.querySelector(".project_div_2").style.display = "none";
				document.querySelector(".projectLabel2").innerHTML = 
				`<img src="/images/default_project_icon_file.svg" style="width: 24px; margin:0px 4px;">
					    프로젝트 선택`;
				document.querySelector(".teamLabel2").innerHTML = 
				`<img src="/images/default_team_icon_file.svg" style="width: 24px; margin:0px 4px;">
							그룹 선택`;
				document.querySelector(".userLabel2").innerHTML = 
				`<img src="/images/default_user_icon_file.svg" style="width: 24px; margin:0px 4px;">
						사용자 선택`;
			}
		  let parentDiv = e.target.closest(".viewer_div_1"); // optionItem2의 부모 요소를 찾음
		  let parentCategory = e.target.closest(".viewer_div_1_category"); // optionItem2의 부모 요소를 찾음
		   if (parentDiv) {
		     parentDiv.style.border = "1px solid #091E424F"; // 원하는 border 스타일로 변경
			 parentCategory.style.display = "none";
		   }
 	  }
  });
});
document.querySelector(".project_div_category")?.addEventListener("click",function(e){
	event.stopPropagation()
	if(document.querySelector(".project_div_category").style.display === "block"){
		document.querySelector(".project_div_category").style.display = "none"
		document.querySelector(".project_div_category").closest(".project_div_1").style.border = "1px solid #091E424F"
		return;
	}
})

document.querySelectorAll(".viewer_div_2").forEach(function(item) {
    item.addEventListener("click", function(e) {
        // 클릭된 item의 부모 요소인 .viewer_div를 찾기
        let viewerDiv = item.closest(".viewer_div");
		
	if(viewerDiv.querySelector(".projectLabel")){
        let projectDiv = viewerDiv.querySelector(".projectLabel");
        let teamDiv = viewerDiv.querySelector(".teamLabel");
        let userDiv = viewerDiv.querySelector(".userLabel");
			
		let projectText = projectDiv.innerText.trim();
		let teamText = teamDiv.innerText.trim();
		let userText = userDiv.innerText.trim();
		
		let viewerListBox = document.querySelector('.viewer_list_box');
		let viewerListBox2 = document.querySelector(".viewer_list_box2");
      const isDuplicate = Array.from(viewerListBox.querySelectorAll('.choice_list_filter_auth'))
	  .some(item => {
          let existingText = item.innerText.trim();
          // 하나라도 일치하면 중복
          return existingText === projectText || 
                 existingText === teamText || 
                 existingText === userText;
      });
	  const isDuplicate2 = Array.from(viewerListBox2.querySelectorAll('.choice_list_filter_auth'))
	  	  .some(item => {
	            let existingText = item.innerText.trim();
	            // 하나라도 일치하면 중복
	            return existingText === projectText || 
	                   existingText === teamText || 
	                   existingText === userText;
        });
		if(isDuplicate2){
			viewerListBox2.querySelectorAll('.choice_list_filter_auth').forEach(function(item){
				let dup = item.innerText;
				if(dup === projectText || dup === teamText || dup === userText){
					item.remove();	
				}
			})
		}
      // 중복이 없다면 추가
      if (!isDuplicate) {
          viewerListBox.innerHTML += `
              <div class="choice_list_filter_auth" 
			  data-project = "${projectDiv.innerText.trim() == "프로젝트 선택" ? '':projectDiv.innerText}"
		      data-account = "${userDiv.innerText.trim() == "사용자 선택" ? '':userDiv.innerText}"
			  data-team = "${teamDiv.innerText.trim() == "그룹 선택" ? '':teamDiv.innerText}">
                  ${projectText === "프로젝트 선택" ? '' : projectDiv.innerHTML}
                  ${teamText === "그룹 선택" ? '' : teamDiv.innerHTML}
                  ${userText === "사용자 선택" ? '' : userDiv.innerHTML}
              </div>`;
		  }
      }else if(viewerDiv.querySelector(".projectLabel2")){
        let projectDiv2 = viewerDiv.querySelector(".projectLabel2");
        let teamDiv2 = viewerDiv.querySelector(".teamLabel2");
        let userDiv2 = viewerDiv.querySelector(".userLabel2");
		
		let projectText2 = projectDiv2.innerText.trim();
		let teamText2 = teamDiv2.innerText.trim();
		let userText2 = userDiv2.innerText.trim();
		
		let viewerListBox = document.querySelector('.viewer_list_box');
		let viewerListBox2 = document.querySelector('.viewer_list_box2');
		const isDuplicate = Array.from(viewerListBox.querySelectorAll('.choice_list_filter_auth'))
		  .some(item => {
		         let existingText = item.innerText.trim();
		         // 하나라도 일치하면 중복
		         return existingText === projectText2 || 
		                existingText === teamText2 || 
		                existingText === userText2;
	     });
		 if(isDuplicate){
 			viewerListBox.querySelectorAll('.choice_list_filter_auth').forEach(function(item){
 				let dup = item.innerText;
 				if(dup === projectText2 || dup === teamText2 || dup === userText2){
 					item.remove();	
 				}
 			})
 		}
	      const isDuplicate2 = Array.from(viewerListBox2.querySelectorAll('.choice_list_filter_auth'))
		  .some(item => {
	          let existingText = item.innerText.trim();
	          // 하나라도 일치하면 중복
	          return existingText === projectText2 || 
	                 existingText === teamText2 || 
	                 existingText === userText2;
	      });
		
		if (!isDuplicate2) {
		viewerListBox2.innerHTML += `
				   <div class="choice_list_filter_auth" >           
				        ${projectText2 === "프로젝트 선택"? '':projectDiv2.innerHTML}
						${teamText2 === "그룹 선택" ? '':teamDiv2.innerHTML}
						${userText2 === "사용자 선택"? '':userDiv2.innerHTML}
			    	</div>`;
			}
		}
    });
});

document.querySelector(".filter_save_reset")?.addEventListener("click",function(){
	let viewerListBox = document.querySelector('.viewer_list_box');
	viewerListBox.innerHTML = '';
})
document.querySelector(".filter_save_reset2")?.addEventListener("click",function(){
	let viewerListBox = document.querySelector('.viewer_list_box2');
	viewerListBox.innerHTML = '';
})

document.querySelector(".save_button")?.addEventListener("click",function(){
	let name = document.querySelector(".hover_input").value;
})
document.querySelector(".cancle_button")?.addEventListener("click",function(){
	document.querySelector(".hover_input").value = '';
	document.querySelector(".viewer_list_box2").innerHTML = '';
	document.querySelector(".viewer_list_box").innerHTML = '';
})
