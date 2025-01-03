document.querySelectorAll(".borderline").forEach(function(input, index){
	input.addEventListener("keyup", function(e){
		const inputItem = document.querySelectorAll(".borderline");
		if(input.value.length == input.maxLength){
			if (inputItem[index + 1]) {
				inputItem[index + 1].focus();
			}else{
				input.blur();
			}
		}
		if (e.key === 'Backspace') {
            if (inputItem[index - 1]) {
                inputItem[index - 1].focus(); // 이전 input으로 포커스
            }else{
				input.blur();
			}
        }
	});
});

let resetData = {
	"email" : ""
}

function resetCode(){
	let url = "/api/account/reset_code";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(resetData)
	})
	.then(response => {
        if (response.ok) {
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
    }).catch(error => {
		console.error("Fetch error:", error);
	});
}