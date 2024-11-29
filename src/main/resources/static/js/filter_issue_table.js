document.querySelector("body").addEventListener("click", function(e) {
    const menu = e.target.closest(".filter_menu");  // 클릭된 메뉴 찾기
    const menuWithShow = document.querySelector(".filter_issue_box.show");  // 열린 .filter_issue_box 찾기
    const menuBoxWithActive = document.querySelector(".filter_category_div.active");  // 활성화된 메뉴 찾기

    console.log(e.target.closest(".filter_menu"));
    console.log(menuWithShow);

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

document.querySelectorAll(".filter_issue_box input[type='checkbox']").forEach(function(input){
	console.log(input);
	input.addEventListener("click", function(e) {
		console.log("sdsd");
		});
})

function fetchInput() {
	let datas = {
		"projecIdxArr" : []
	}
	// fetch()를 사용하여 AJAX 요청
	let url = "/api/filter_issue_table/project_filter";
	console.log("sdsd");
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(datas)
	})
		.then(response => response.json())  // JSON 형태로 응답 받기
		.then(issueListByProjectKey => {
			console.log(issueListByProjectKey);
			
		})
		.catch(error => {
			console.error("Fetch error:", error);
		});
}

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
