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