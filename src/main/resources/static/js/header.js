let prevBtn = "";

document.querySelector("body").addEventListener("mousedown",function(e){

    if(e.target.closest(".my_app_btn") === null){
        document.querySelector(".my_app_content.item_lnb.show")?.classList.remove("show");
    }

    if(e.target.closest(".search_box") === null){
        document.querySelector(".search_lnb_box.show")?.classList.remove("show");
    }

    // gnb2_box click 이벤트
    if(e.target.closest(".gnb2_box_btn") === null){
        document.querySelectorAll(".gnb2_box_icon").forEach(function(btn){
            btn.nextElementSibling?.classList.remove("show");
        });
    }else{
        if(e.target.closest(".gnb2_box_content") !== null) return;
        if(e.target.closest(".gnb2_box_icon") !== prevBtn){
            document.querySelectorAll(".gnb2_box_icon").forEach(function(btn){
                btn.nextElementSibling?.classList.remove("show");
            });
        }
        prevBtn = e.target.closest(".gnb2_box_icon");
        e.target.closest(".gnb2_box_icon")?.nextElementSibling?.classList.toggle("show");
    }
    
});

// my_app btn click 이벤트
document.querySelector(".my_app_btn .img_box").addEventListener("click", function(){
    this.nextElementSibling.classList.toggle("show");
});

// search_box click 이벤트
document.querySelector(".header_input_container input").addEventListener("click", function(){
    this.nextElementSibling.classList.add("show");
    
});

// input 마지막 업데이트 click 이벤트
document.querySelectorAll(".last_update_box>ul>li").forEach(function(li){
    li.addEventListener("click", function(){
        document.querySelector(".last_update_box>ul>.active").classList.remove("active");
        this.classList.add("active");
    });
});




