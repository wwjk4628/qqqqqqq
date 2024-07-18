document.addEventListener('DOMContentLoaded', function() {
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    
    // 페이지 로드 시 초기 데이터로 테이블을 업데이트하고 로컬 스토리지에 저장 공간 마련
    fetch('http://localhost:8080/Inventory/branch/stockout/getListForform')
        .then(response => response.json())
        .then(data => {
            updateSearchResults(data);
            initializeLocalStorage(data);  // 초기 데이터로 로컬 스토리지 설정
        })
        .catch(error => console.error('Error:', error));

    // 검색 폼 제출 시 AJAX 요청
    document.getElementById('search-form').addEventListener('submit', function(event) {
        event.preventDefault(); // 폼의 기본 제출 동작을 방지
        const keyword = document.querySelector('input[name="keyword"]').value;


        fetch('http://localhost:8080/Inventory/branch/stockout/search', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded', [csrfHeader]: csrfToken },
            body: new URLSearchParams({ 'keyword': keyword })
        })
        .then(response => response.json())
        .then(data => {
            updateSearchResults(data);
        })
        .catch(error => console.error('Error:', error));
    });

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
    const comments = {};
    const bookNames = {};

    data.forEach(item => {
        const bookCode = item.bookCode;
        quantities[bookCode] = quantities[bookCode] || 0;
        comments[bookCode] = comments[bookCode] || '';
        bookNames[bookCode] = item.bookName;
    });

    localStorage.setItem('quantities', JSON.stringify(quantities));
    localStorage.setItem('comments', JSON.stringify(comments));
    localStorage.setItem('bookNames', JSON.stringify(bookNames));
}

// 검색 결과를 페이지에 업데이트
function updateSearchResults(data) {
    const tableBody = document.getElementById('table-body');
    tableBody.innerHTML = ''; // 기존 결과를 지웁니다.

    data.forEach(item => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${item.bookName}</td>
            	<input type="hidden" name="bookCode" value="${item.bookCode}">
                <input type="hidden" name="bookName" value="${item.bookName}">
            <td>${item.inventory}</td>
            <td>
                <input type="number" name="quantity" min="0" max="${item.inventory}"
                    data-book-code="${item.bookCode}"
                    oninput="validateAndHandleQuantity(this, ${item.inventory})">
            </td>
            <td>
                <textarea class="comment-box" data-book-code="${item.bookCode}" placeholder="코멘트를 입력하세요" oninput="saveLocalStorage()" disabled></textarea>
            </td>
        `;
        tableBody.appendChild(row);
    });

    // 로컬 스토리지에서 데이터 불러오기
    loadLocalStorage();
}

// 수량 입력 최대값은 보유 수량을 넘지 않는 함수
function validateQuantity(input, max) {
    const value = parseInt(input.value, 10) || 0;
    input.value = Math.min(Math.max(value, 0), max);
}

// 수량 변경 시 처리 함수
function handleQuantityChange(input) {
    const bookCode = input.dataset.bookCode;
    const textarea = document.querySelector(`textarea[data-book-code="${bookCode}"]`);
    if (!textarea) return;

    const quantity = parseInt(input.value, 10) || 0;
    textarea.disabled = quantity <= 0;
    saveLocalStorage();
}

// 수량 유효성 검사 및 처리 함수
function validateAndHandleQuantity(input, max) {
    validateQuantity(input, max);
    handleQuantityChange(input);
}

function saveLocalStorage() {
    const quantities = JSON.parse(localStorage.getItem('quantities') || '{}');
    const comments = JSON.parse(localStorage.getItem('comments') || '{}');

    document.querySelectorAll('input[name="quantity"]').forEach(input => {
        const bookCode = input.dataset.bookCode;
        if (bookCode) {
            quantities[bookCode] = input.value;
        }
    });

    document.querySelectorAll('textarea.comment-box').forEach(textarea => {
        const bookCode = textarea.dataset.bookCode;
        if (bookCode) {
            comments[bookCode] = textarea.value;
        }
    });

    localStorage.setItem('quantities', JSON.stringify(quantities));
    localStorage.setItem('comments', JSON.stringify(comments));
}

// 로드 함수
function loadLocalStorage() {
    const quantities = JSON.parse(localStorage.getItem('quantities') || '{}');
    const comments = JSON.parse(localStorage.getItem('comments') || '{}');

    document.querySelectorAll('input[name="quantity"]').forEach(input => {
        const bookCode = input.dataset.bookCode;
        if (quantities[bookCode] !== undefined) {
            input.value = quantities[bookCode];
            validateAndHandleQuantity(input, input.max);
        }
    });

    document.querySelectorAll('textarea.comment-box').forEach(textarea => {
        const bookCode = textarea.dataset.bookCode;
        if (comments[bookCode] !== undefined) {
            textarea.value = comments[bookCode];
            textarea.disabled = (quantities[bookCode] <= 0);
        }
    });
    saveLocalStorage();
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
    modalBody.innerHTML = ''; // 기존 내용을 지웁니다.

    // LocalStorage에서 수량과 코멘트를 불러옵니다.
    const quantities = JSON.parse(localStorage.getItem('quantities') || '{}');
    const comments = JSON.parse(localStorage.getItem('comments') || '{}');
    const bookNames = JSON.parse(localStorage.getItem('bookNames') || '{}');

    // LocalStorage의 모든 항목을 모달에 추가합니다.
    Object.keys(quantities).forEach(bookCode => {
        const quantity = quantities[bookCode];
        const comment = comments[bookCode] || '';
        const bookName = bookNames[bookCode] || '정보 없음';
        
        // 수량이 0보다 큰 경우만 모달에 추가합니다.
        if (quantity > 0) {
            modalBody.innerHTML += `
                <div>
                    <p><strong>교재명:</strong> ${bookName}</p>
                    <p><strong>수량:</strong> ${quantity}</p>
                    <p><strong>코멘트:</strong> ${comment}</p>
                    <hr>
                </div>
            `;
        }
    });

    // 모달을 보여줍니다.
    modal.style.display = 'block';
}

// 폼 제출 함수
function submitOrderForm() {
    // LocalStorage에서 수량과 코멘트를 불러옵니다.
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    
    const quantities = JSON.parse(localStorage.getItem('quantities') || '{}');
    const comments = JSON.parse(localStorage.getItem('comments') || '{}');
    const bookNames = JSON.parse(localStorage.getItem('bookNames') || '{}');

    const orderData = Object.keys(quantities)
        .map(bookCode => {
            const quantity = quantities[bookCode];
            const comment = comments[bookCode] || '';
            const bookName = bookNames[bookCode] || '정보 없음';
            return quantity > 0 || comment ? { bookCode, quantity, comments: comment, bookName } : null;
        })
        .filter(item => item);
        
    fetch('http://localhost:8080/Inventory/branch/stockout/confirm', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', [csrfHeader]: csrfToken  },
        body: JSON.stringify(orderData)
    }).then(response => {
        if (!response.ok) {
            return response.text().then(errorText => {
                alert('오류 발생: ' + errorText);
                throw new Error('서버 오류: ' + errorText);
            });
        }
        return response.text();
    }).then(message => {
        alert(message); // 성공 메시지
        localStorage.clear();
        window.location.href = 'http://localhost:8080/Inventory/branch/stockout/list';
    }).catch(error => console.error('Error:', error));
}
