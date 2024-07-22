function addToCart() {
	var bookCode = document.getElementById("bookSelect").value;
	var quantity = document.getElementById("quantity").value;

	// 교재 선택 여부 확인
	if (bookCode === "") {
		alert("교재를 선택해주세요.");
		return;
	}

	// 수량 확인
	if (quantity.trim() === "" || isNaN(quantity) || parseFloat(quantity) < 1) {
		alert("올바른 수량을 입력해주세요.");
		return;
	}

	// 수량이 숫자인지 검사 (추가된 부분)
	if (!isNumber(quantity)) {
		alert("수량은 숫자만 입력해주세요.");
		return;
	}

	// 수량 제한 확인
	var maxQuantity = 100000;
	if (parseInt(quantity) > maxQuantity) {
		alert("최대 발주 수량은 100,000개입니다. 다시 입력해주세요.");
		return;
	}
	// 폼 제출
	var form = document.getElementById("addToCartForm");
	form.submit();
}

// 숫자인지 확인하는 함수
function isNumber(value) {
	return /^\d+$/.test(value);
}


/* function filterBooks() {
	var input, filter, select, options, option, i, txtValue;
	input = document.getElementById("bookSearch");
	filter = input.value.toUpperCase();
	select = document.getElementById("bookSelect");
	options = select.getElementsByTagName("option");

	for (i = 0; i < options.length; i++) {
		option = options[i];
		txtValue = option.textContent || option.innerText;
		if (txtValue.toUpperCase().indexOf(filter) > -1) {
			option.style.display = "";
		} else {
			option.style.display = "none";
		}
	}
} */


function confirmSubmit() {
	// 장바구니 테이블의 행 수를 가져옵니다
	var cartRows = document.querySelectorAll("#cartTable tr").length;

	// 만약 테이블에 행이 하나도 없다면
	if (cartRows <= 2) { // 테이블에는 헤더 행만 있으므로 길이는 1입니다
		alert("장바구니에 교재를 추가해야 발주를 제출할 수 있습니다.");
		return false; // 발주 제출을 막습니다
	}

	// 발주 제출 전에 사용자에게 확인 메시지를 표시합니다
	var confirmed = confirm('정말로 제출하시겠습니까?');
	return confirmed;
}

function updateExpectedStock(input, inventory) {
	var quantity = parseInt(input.value);
	var row = input.parentNode.parentNode; // 해당 input 태그가 속한 tr 요소
	var expectedStockCell = row
		.querySelector(".expected-stock");

	if (!isNaN(quantity) && quantity >= 0) {
		expectedStockCell.textContent = (inventory + quantity)
			.toString();
	} else {
		expectedStockCell.textContent = inventory.toString();
	}
}

function addToCart2() {
	var form = document.getElementById("addToCartForm2");
	var quantities = document.querySelectorAll(".quantity-input");

	var anyQuantitySelected = false;
	var maxQuantityExceeded = false;

	for (var i = 0; i < quantities.length; i++) {
		var value = quantities[i].value.trim();

		// 입력값이 숫자인지 확인
		if (!isValidNumber(value)) {
			alert('올바른 수량을 입력해주세요.');
			quantities[i].value = ""; // 입력 필드 초기화
			return;
		}

		// 숫자로 변환하여 설정
		quantities[i].value = parseFloat(value);

		// 발주 수량이 0 초과인지 확인
		if (parseInt(quantities[i].value) > 0) {
			anyQuantitySelected = true;
		}

		// 발주 수량이 100,000을 초과하는지 확인
		if (parseInt(quantities[i].value) > 100000) {
			maxQuantityExceeded = true;
		}
	}

	if (!anyQuantitySelected) {
		alert("최소 한 권 이상의 교재를 선택해야 합니다.");
		return;
	}

	if (maxQuantityExceeded) {
		alert("100,000개 이하의 교재를 선택해야 합니다.");
		return;
	}


	form.submit();
}

// 숫자 유효성 검사 함수
function isValidNumber(value) {
	// 숫자 패턴 확인
	return /^-?\d*\.?\d*$/.test(value);
}


// 검색 필드에 입력이 들어올 때마다 호출되도록 이벤트 핸들러 설정
document.getElementById("bookSearch").addEventListener("input",
	filterBooks);