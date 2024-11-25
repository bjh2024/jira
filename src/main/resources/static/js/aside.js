// filterBox reset
function filterBoxReset() {
  document
    .querySelectorAll(".like_filter_box > button")
    .forEach(function (btn) {
      btn.classList.remove("active");
    });
  document
    .querySelector(".like_filter_box > button:first-of-type")
    .classList.add("active");

  document
    .querySelector(".like_filter_container.active")
    ?.classList.remove("active");
  document
    .querySelector(".filter_select_result_box.show")
    ?.classList.remove("show");
  document.querySelector(".filter_select_result_item > span").innerText =
    "전체";
}

// 최근, 별표 표시됨
let prevRightBtn = "";
document.querySelector("body").addEventListener("click", function (e) {
  const filterContainer = e.target.closest(".like_filter_container");

  if (filterContainer !== null) {
    document.querySelector(".like_filter_box").classList.toggle("show");
  } else {
    document.querySelector(".like_filter_box").classList.remove("show");
    if (e.target.closest(".aside_click_view_container.show") === null) {
      filterBoxReset();
    }
  }

  const rightBoxBtn = e.target.closest(".view_right_box_btn");

  if (e.target.closest(".aside_click_view_container.show")) return;
  if (prevRightBtn === rightBoxBtn) {
    rightBoxBtn?.classList.toggle("active");
    rightBoxBtn
      ?.querySelector(".aside_click_view_container")
      .classList.toggle("show");
  } else {
    document
      .querySelector(".view_right_box_btn.active")
      ?.classList.remove("active");
    rightBoxBtn?.classList.add("active");

    document
      .querySelector(".aside_click_view_container.show")
      ?.classList.remove("show");
    rightBoxBtn
      ?.querySelector(".aside_click_view_container")
      .classList.add("show");
  }
  prevRightBtn = rightBoxBtn;
});

document
  .querySelectorAll(".like_filter_box > button")
  .forEach(function (btn, idx) {
    btn.addEventListener("click", function () {
      const filterSelectBox = document.querySelector(
        ".filter_select_result_box"
      );
      const filterContainer = document.querySelector(".like_filter_container");

      document
        .querySelector(".like_filter_box > button.active")
        .classList.remove("active");
      this.classList.add("active");
      if (idx !== 0) {
        filterContainer.classList.add("active");
        filterSelectBox.classList.add("show");
        filterSelectBox.querySelector("span").innerText =
          this.querySelector("span").innerText;
      } else {
        filterContainer.classList.remove("active");
        filterSelectBox.classList.remove("show");
      }
    });
  });

// 프로젝트, 필터, 대시보드
document.querySelectorAll(".view_under_box_btn").forEach(function (btn) {
  btn.addEventListener("click", function (e) {
    if (e.target.closest(".img_container") !== null) return;
    this.parentElement.nextElementSibling.classList.toggle("show");

    if (this.parentElement.nextElementSibling.className.includes("show")) {
      this.querySelector(".img_box.mouseover img").setAttribute(
        "src",
        "/images/arrow_under_icon.svg"
      );
    } else {
      this.querySelector(".img_box.mouseover img").setAttribute(
        "src",
        "/images/arrow_right_icon.svg"
      );
    }
  });
});

// more sub Box show 이벤트
let prevMoreSubBox = "";
document.querySelector("body").addEventListener("click", function (e) {
  const moreItemContainer = e.target.closest(".more_item_container");
  const viewMoreBox = e.target.closest(".view_more_box");
  const imgContainer = e.target.closest(".img_container");
  const imgBox = e.target.closest(".img_box");
  
  if (e.target.closest(".more_sub_box.show") !== null) return;
  if (
    e.target.closest(".img_box") === prevMoreSubBox &&
    prevMoreSubBox !== null
  ) {
    e.preventDefault();
	
    imgContainer?.classList.toggle("show");
    imgBox?.classList.toggle("active");
    viewMoreBox?.classList.toggle("active");
    if (imgBox.className.includes("more")) {
      moreItemContainer
        .querySelector(".more_sub_box.more")
        .classList.toggle("show");
    } else if (imgBox.className.includes("plus")) {
      moreItemContainer
        .querySelector(".more_sub_box.plus")
        .classList.toggle("show");
    }
  } else {
    document.querySelector(".more_sub_box.show")?.classList.remove("show");
    document.querySelector(".img_container.show")?.classList.remove("show");
    document.querySelector(".img_box.active")?.classList.remove("active");
    document.querySelector(".view_more_box.active")?.classList.remove("active");

    if (e.target.closest(".img_container") !== null) {
      e.preventDefault();
      imgContainer.classList.add("show");
      imgBox.classList.add("active");
      viewMoreBox.classList.add("active");
      if (imgBox.className.includes("more")) {
        moreItemContainer
          .querySelector(".more_sub_box.more")
          .classList.add("show");
      } else if (imgBox.className.includes("plus")) {
        moreItemContainer
          .querySelector(".more_sub_box.plus")
          .classList.add("show");
      }
    }
  }
  prevMoreSubBox = e.target.closest(".img_box");
});

// 기본값 필터 click 이벤트
document
  .querySelector(".more_item_box.default_filter_btn")
  .addEventListener("click", function () {
    if (
      this.querySelector("img").getAttribute("src") ===
      "/images/arrow_right_icon.svg"
    ) {
      this.querySelector("img").setAttribute(
        "src",
        "/images/arrow_under_icon.svg"
      );
    } else {
      this.querySelector("img").setAttribute(
        "src",
        "/images/arrow_right_icon.svg"
      );
    }
    this.nextElementSibling.classList.toggle("show");
  });
