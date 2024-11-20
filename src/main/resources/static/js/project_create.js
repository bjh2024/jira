const projectNameInput = document.querySelector(".project_name_input_box input");

projectNameInput.addEventListener("keydown", function(e) {
	const projectName = document.querySelector(".project_name_example");
	console.log(e.target.value);
	if (e.target.value === "") {
		projectName.innerText = "제목 없는 프로젝트";
	} else {
		projectName.innerText = e.target.value;
	}
});

projectNameInput.addEventListener("keyup", function(e) {
	const projectName = document.querySelector(".project_name_example");
	if (e.target.value === "") {
		projectName.innerText = "제목 없는 프로젝트";
	} else {
		projectName.innerText = e.target.value;
	}
});

const projectKeyInput = document.querySelector(".more_box input#key");
const keyBoxs = document.querySelectorAll(".table_box2 td:nth-of-type(2)>div");

projectKeyInput.addEventListener("keydown", function(e) {
	if (e.target.value === "") {
		keyBoxs.forEach(function(key, idx) {
			key.innerText = `KEY-${idx + 1}`;
		});
	} else {
		keyBoxs.forEach(function(key, idx) {
			key.innerText = `${e.target.value}-${idx + 1}`;
		});
	}
});

projectKeyInput.addEventListener("keyup", function(e) {
	if (e.target.value === "") {
		keyBoxs.forEach(function(key, idx) {
			key.innerText = `KEY-${idx + 1}`;
		});
	} else {
		keyBoxs.forEach(function(key, idx) {
			key.innerText = `${e.target.value}-${idx + 1}`;
		});
	}
});

const moreBtnBox = document.querySelector(".more_btn_box");
let check = false;
moreBtnBox.addEventListener("click", function(){
	const moreBtn = document.querySelector(".more_btn");
	const img = moreBtn.children[0];
	const button = moreBtn.children[1];
	
	const moreBox = document.querySelector(".more_box");
	check = !check;
	if(check){
		moreBox.classList.add("show");
		img.src="/images/arrow_bottom_icon.svg"
		button.innerText = "간략히 보기";
	}else{
		moreBox.classList.remove("show");
		img.src="/images/arrow_right_icon.svg"
		button.innerText = "더 보기";
	}
});
