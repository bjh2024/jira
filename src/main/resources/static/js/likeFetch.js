<<<<<<< HEAD
// 즐겨찾기 fetch
function likeFetch(type, idx, isLike) {
	let likeRequest = {"idx": idx,
		               "isLike" : isLike};
	let url = `/api/like/${type}`
	fetch(url, { method: "POST", 
				 headers: { "Content-Type": "application/json"},
			     body:JSON.stringify(likeRequest)
			    }
	)
	.catch(e => {
		console.error(e);
	});
	location.reload(true);
=======
// 즐겨찾기 fetch
function likeFetch(type, idx, isLike) {
	let likeRequest = {"idx": idx,
		               "isLike" : isLike};
	let url = `/api/like/${type}`
	fetch(url, { method: "POST", 
				 headers: { "Content-Type": "application/json"},
			     body:JSON.stringify(likeRequest)
			    }
	)
	.catch(e => {
		console.error(e);
	});
	location.reload(true);
>>>>>>> branch 'ndh' of https://github.com/bjh2024/jira.git
}