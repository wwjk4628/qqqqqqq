document.addEventListener('DOMContentLoaded', function() {
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    
    // 페이지 로드 시 초기 데이터로 테이블을 업데이트하고 로컬 스토리지에 저장 공간 마련
    fetch('http://localhost:8080/Inventory/branch/initial/getListForform')
        .then(response => response.json())
        .then(data => {
            updateTable(data);
            initializeLocalStorage(data);  // 초기 데이터로 로컬 스토리지 설정
        })
        .catch(error => console.error('Error:', error));

	let orderByInput = document.getElementById('orderBy');
    let orderBy = orderByInput.value || '';
	// orderBy 설정 로직
	window.updateOrderBy = function(field) {
        let orderByArray = orderBy.split(',').map(s => s.trim()).filter(s => s);
        let fieldIndex = orderByArray.findIndex(s => s.startsWith(field + ' '));

        if (fieldIndex > -1) {
            let currentOrder = orderByArray[fieldIndex].split(' ')[1];
            if (currentOrder === 'asc') {
                orderByArray[fieldIndex] = field + ' desc';
            } else {
                orderByArray.splice(fieldIndex, 1);
            }
        } else {
            orderByArray.push(field + ' asc');
        }

        orderBy = orderByArray.join(', ');
        document.getElementById('orderBy').value = orderBy;
        sendAjaxRequest();
    }
    
    // 안 쓰는 부분
    /*document.getElementById('resetOrderBy').addEventListener('click', function() {
        orderByInput.value = '';
        sendAjaxRequest();
    });*/

    document.getElementById('search-form').addEventListener('submit', function(event) {
        event.preventDefault();
        sendAjaxRequest();
    });
    
    function sendAjaxRequest() {
        let form = document.getElementById('search-form');
        let formData = new FormData(form);

        let xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://localhost:8080/Inventory/branch/initial/search', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader(csrfHeader, csrfToken);

        xhr.onload = function() {
            if (xhr.status === 200) {
                let response = JSON.parse(xhr.responseText);
                updateTable(response);
            }
        };

        xhr.send(new URLSearchParams(formData).toString());
    }
    
    function updateTable(data) {
        const table = document.getElementById('table');
    	
    	table.innerHTML = `
        <thead>
            <tr>
                <th>번호</th>
                <th onclick="updateOrderBy('kindcode')">구분
                    ${orderBy.includes('kindcode asc') ? '▲' : orderBy.includes('kindcode desc') ? '▼' : ''}
                </th>
                <th onclick="updateOrderBy('bookName')">교재명
                    ${orderBy.includes('bookName asc') ? '▲' : orderBy.includes('bookName desc') ? '▼' : ''}
                </th>
                <th onclick="updateOrderBy('inventory')">재고
                    ${orderBy.includes('inventory asc') ? '▲' : orderBy.includes('inventory desc') ? '▼' : ''}
                </th>
                <th>작업</th>
            </tr>
        </thead>
        <tbody id="table-body">
        </tbody>
    	`;
    	const tbody = table.querySelector('#table-body');
        tbody.innerHTML = '';

        data.forEach((item, index) => {
            let row = document.createElement('tr');
            row.innerHTML = `
                <td>${index + 1}</td>
                <td>${getKindCode(item.kindCode)}</td>
                <td>${item.bookName}</td>
                	<input type="hidden" name="bookCode" value="${item.bookCode}">
                	<input type="hidden" name="bookName" value="${item.bookName}">
                <td>${item.inventory}</td>
                <td>
                <input type="number" name="quantity" min="0"
                    data-book-code="${item.bookCode}"
                    oninput="handleQuantityInput(this)"
                    onpaste="handlePaste(event)">
            </td>
            `;

            tbody.appendChild(row);
        });
        loadLocalStorage();
    }
    
    function getKindCode(kindCode) {
        switch (parseInt(kindCode, 10)) {
            case 1: return '초등';
            case 2: return '중등';
            case 3: return '고등';
            case 4: return '수능';
            default: return '몰라';
        }
    }
    
    // 검색어 초기화 로직
    window.resetKeyword = function() {
        document.querySelector('input[name="keyword"]').value = '';
        document.getElementById('search-form').dispatchEvent(new Event('submit')); // 폼 제출 이벤트를 강제로 발생
    };

    // 페이지를 떠날 때 로컬 스토리지 초기화
    window.addEventListener('beforeunload', function() {
        localStorage.clear();
    });
});

// 초기 데이터로 로컬 스토리지 설정
function initializeLocalStorage(data) {
    const quantities = {};
    const bookNames = {};

	// bookCode 기반의 빈 배열 만들어놓기
    data.forEach(item => {
        const bookCode = item.bookCode;
        quantities[bookCode] = null;
        bookNames[bookCode] = item.bookName;
    });

	// localStorage에 json으로 배열 저장
    localStorage.setItem('quantities', JSON.stringify(quantities));
    localStorage.setItem('bookNames', JSON.stringify(bookNames));
}

// 입력값을 필터링하는 함수
function handleQuantityInput(input) {
    const value = input.value.replace(/[^0-9]/g, '');
    
    input.value = Math.max(value, 0);
    saveLocalStorage();
}

// 붙여넣기 방지 함수
function handlePaste(e) {
    e.preventDefault();
    return false;
}

// localStorage에 입력값 저장하는 함수
function saveLocalStorage() {
    const quantities = JSON.parse(localStorage.getItem('quantities') || '{}');

    document.querySelectorAll('input[name="quantity"]').forEach(input => {
        const bookCode = input.dataset.bookCode;
        if (bookCode) {
            quantities[bookCode] = input.value;
        }
    });

    localStorage.setItem('quantities', JSON.stringify(quantities));
    addGije();
}

// 로드 함수
function loadLocalStorage() {
    const quantities = JSON.parse(localStorage.getItem('quantities') || '{}');

    document.querySelectorAll('input[name="quantity"]').forEach(input => {
        const bookCode = input.dataset.bookCode;
        if (quantities[bookCode] !== undefined) {
            input.value = quantities[bookCode];
        }
    });

}

// 모달 닫기 함수
function closeModal() {
    const modal = document.getElementById('confirmationModal');
    modal.style.display = 'none';
}

// 모달 바깥을 클릭하면 닫히는 기능을 추가
document.addEventListener('click', function(event) {
    const modal = document.getElementById('confirmationModal');
    if (modal.style.display === 'block' && event.target === modal) {
        closeModal();
    }
});

// 모달 확인 함수
function showConfirmationModal() {
    const modal = document.getElementById('confirmationModal');
    const modalBody = document.getElementById('modal-body');
    modalBody.innerHTML = ''; // 기존 내용을 지움

    // LocalStorage에서 수량과 코멘트를 불러옴
    const quantities = JSON.parse(localStorage.getItem('quantities') || '{}');
    const bookNames = JSON.parse(localStorage.getItem('bookNames') || '{}');

    // LocalStorage의 모든 항목 불러오기
    Object.keys(quantities).forEach(bookCode => {
        const quantity = quantities[bookCode];
        const bookName = bookNames[bookCode] || '정보 없음';
        
        // 수량이 0보다 큰 경우만 모달에 추가
        if (quantity > 0) {
            modalBody.innerHTML += `
                <div>
                    <p><strong>교재명:</strong> ${bookName}</p>
                    <p><strong>수량:</strong> ${quantity}</p>
                    <hr>
                </div>
            `;
        }
    });

    // 모달을 보여줍니다.
    modal.style.display = 'block';
}

function addGije() {
    const gije = document.getElementById('gije');
    gije.innerHTML = ''; // 기존 내용을 지움

    // LocalStorage에서 수량과 코멘트를 불러옴
    const quantities = JSON.parse(localStorage.getItem('quantities') || '{}');
    const bookNames = JSON.parse(localStorage.getItem('bookNames') || '{}');

    // LocalStorage의 모든 항목 불러오기
    Object.keys(quantities).forEach(bookCode => {
        const quantity = quantities[bookCode];
        const bookName = bookNames[bookCode] || '정보 없음';
        
        // 수량이 0보다 큰 경우만 모달에 추가
        if (quantity > 0) {
            gije.innerHTML += `
                <div>
                    <p><strong>교재명:</strong> ${bookName}</p>
                    <p><strong>수량:</strong> ${quantity}</p>
                    <button type="button" onclick="deleteGije('${bookCode}')" class="delete">삭제</button>
                    <hr>
                </div>
            `;
        }
    });
}

function deleteGije(bookCode) {
    const quantities = JSON.parse(localStorage.getItem('quantities') || '{}');
    
    if (quantities.hasOwnProperty(bookCode)) {
        quantities[bookCode] = 0; 
        localStorage.setItem('quantities', JSON.stringify(quantities));
        addGije();  // 모달 업데이트

        
        const input = document.querySelector(`input[data-book-code="${bookCode}"]`);
        if (input) {
            input.value = null;
        }
    }
}

// 폼 제출 함수
function submitOrderForm() {
    // LocalStorage에서 수량을 불러옴
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    
    const quantities = JSON.parse(localStorage.getItem('quantities') || '{}');
    const bookNames = JSON.parse(localStorage.getItem('bookNames') || '{}');

    const orderData = Object.keys(quantities)
        .map(bookCode => {
            const quantity = quantities[bookCode];
            const bookName = bookNames[bookCode] || '정보 없음';
            return quantity > 0 ? { bookCode, quantity, bookName } : null;
        })
        .filter(item => item);
        
    fetch('http://localhost:8080/Inventory/branch/initial/confirm', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', [csrfHeader]: csrfToken  },
        body: JSON.stringify(orderData)
    }).then(response => {	// post 전송 결과를 ResponseEntity<String> 으로 받음
        if (!response.ok) {
            return response.text().then(errorText => {
                alert('오류 발생: ' + errorText);
                throw new Error('서버 오류: ' + errorText);
            });
        }
        return response.text();
    }).then(message => {
        alert(message); // 성공 메시지
        localStorage.clear(); // 성공 후에는 비워주기~~
        window.location.href = 'http://localhost:8080/Inventory/branch/inventory';
    }).catch(error => console.error('Error:', error));
}
