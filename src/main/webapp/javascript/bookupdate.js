// scripts.js
document.addEventListener("DOMContentLoaded", function() {
			displayAlert();
		});

// 서버에서 전달받은 error 메시지를 alert 창에 출력하는 함수
function displayAlert() {
    var error = document.body.getAttribute("data-error"); // JSP에서 전달받은 error 메시지
    if (error && error.trim() !== "") {
        alert(error + " 다시 처리해주세요"); // alert 창에 error 메시지 출력
    }
}

document.addEventListener("DOMContentLoaded", function() {
    var addList = document.body.getAttribute("data-addList");
    if (addList === "true") {
        alert("교재 리스트에 추가되었습니다.");
    }
});
document.addEventListener("DOMContentLoaded", function() {
    var modify = document.body.getAttribute("data-modify");
    if (modify === "true") {
        alert("교재가 수정되었습니다.");
    }
});

function addToBookList() {
    var bookCode = document.getElementsByName("bookCode")[0].value;
    var bookName = document.getElementsByName("bookName")[0].value;
    var price = document.getElementById("priceInput").value;
    var kindCode = document.getElementById("kindInput").value;

    // 교재 코드 확인
    if (bookCode.trim() === "") {
        alert("교재 코드를 입력해 주세요.");
        return;
    }

    // 교재명 확인
    if (bookName.trim() === "") {
        alert("교재명을 입력해 주세요.");
        return;
    }

    // 가격 확인
    if (price.trim() === "" || isNaN(price) || parseFloat(price) <= 0) {
        alert("올바른 가격을 입력해주세요.");
        return;
    }

    // 과목 코드 확인
    if (kindCode.trim() === "" || isNaN(kindCode) || parseFloat(kindCode) <= 0) {
        alert("과목 코드를 입력해 주세요.");
        return;
    }

    // 가격이 숫자인지 검사
    if (!isNumber(price)) {
        alert("가격은 숫자만 입력해주세요.");
        return;
    }

    // 폼 제출
    var form = document.getElementById("addToBookList");
    form.submit();
}

function isNumber(value) {
    return /^\d+$/.test(value);
}

function handleQuantityInput(input) {
    // 입력된 값을 정수로 변환합니다.
    let value = parseInt(input.value, 10);

    // 최소값(min)과 최대값(max) 사이의 값으로 제한합니다.
    if (isNaN(value)) {
        value = 0; // 숫자가 아니거나 값이 없으면 기본값으로 1을 설정합니다.
    } else {
        value = Math.min(Math.max(value, 0), 9999999);
    }

    // 제한된 값을 입력 필드에 반영합니다.
    input.value = value;
}

function validateBookCode(input) {
    var bookCode = input.value.trim();
    // 정규표현식을 사용하여 숫자와 '-'만 입력 가능하도록 검사합니다.
    if (!/^[0-9-]*$/.test(bookCode)) {
        alert("교재 코드는 숫자와 '-'만 입력 가능합니다.");
        input.value = ""; // 잘못된 입력을 제거합니다.
    } else if (bookCode.length > 20) {
        alert("교재 코드는 최대 20자까지 입력 가능합니다.");
    }
}

function validateBookName(input) {
    var bookName = input.value.trim();
    if (bookName.length > 30) {
        alert("교재명은 최대 30자까지 입력 가능합니다.");
    }
}
