// 나에게 할당된 이슈 목록
function allotFetch(pageNum, col, tbody) {
	const uri = `?pageNum=${pageNum}&col=${col}`;
	fetch(uri, { method: "get" })
		.then(res => res.json())
		.then(res => {
			tbody.innerHTML = `
						<tr>
							<td>
								<a href="#" class="img_box"><img src="/images/${type_iconFilename}" width="16" height="16" /></a>
							</td>
							<td><a href="#">${key}</a></td>
							<td><a href="#">${name}</a></td>
							<td><img src="/images/${priority_iconFilename}" width="16" height="16" /></td>
						</tr>`
		})
		.catch(err => {
			console.error(err);
		});
}

window.onload = function() {
	// 나에게 할당
	const allot = document.querySelectorAll(".dashboard_allot");
	if (allot !== null) {
		allot.forEach(function(item) {
			const col = allot.getAttribute("col-data");
			const tbody = item.querySelector("tbody");
			allotFetch(1, col, tbody);
			item.querySelector(".dynamic_box").innerHTML += `
			<div class="paging_box">
				<p>
					<a href="#">3</a>개의 이슈 중 <span>1</span>에서 <span>2</span>까지 표시.
				</p>
				<div class="number_box">
					<span>1</span>
					<span>2</span>
				</div>
			</div>
			`
		});
	}
}

