// 대시보드 삭제 모달창 이벤트
document.querySelector("body").addEventListener("mousedown", function(e){
	const deleteModal = document.querySelector(".dashboard_delete_modal.list");
	const deleteBox = e.target.closest(".dashboard_delete_modal.list .dashboard_delete_box");
	const cancleBtn = e.target.closest(".dashboard_delete_modal.list .cancle_btn");
	const deleteBtn = e.target.closest(".dashboard_delete_modal.list .delete_btn");
	
	if(deleteBox === null || cancleBtn !== null){
		deleteModal.classList.remove("show");
		return;
	}
	
	if(deleteBtn !== null){
		const uri = "/api/dashboard/delete";
		const idx = deleteModal.getAttribute("idx-data");
		fetch(uri, {method:"post", 
					headers:{"Content-Type":"application/json"}, 
					body:JSON.stringify(idx)}
		)
		.catch(err => {
			console.error(err);
		});
		location.href = "/dashboard/list";
	}
});