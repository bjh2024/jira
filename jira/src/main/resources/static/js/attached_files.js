document.querySelectorAll(".attached-file-box").forEach(function(box, index){
	box.addEventListener("mouseover", function(e){
		
		const fileBoxItem = e.target.closest(".attached-file-box");
		if(fileBoxItem !== null){
			document.querySelectorAll(".attached-file-detail")[index].style.backgroundColor = "#F1F2F4";
			document.querySelectorAll(".attached-file-options")[index].style.display = "flex";
		}
	})
});

document.querySelectorAll(".attached-file-box").forEach(function(box, index){
	box.addEventListener("mouseout", function(e){
		
		const fileBoxItem = e.target.closest(".attached-file-box");
		if(fileBoxItem !== null){
			document.querySelectorAll(".attached-file-detail")[index].style.backgroundColor = "white";
			document.querySelectorAll(".attached-file-options")[index].style.display = "none";
		}
	})
});