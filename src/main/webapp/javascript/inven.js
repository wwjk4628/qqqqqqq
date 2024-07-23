document.addEventListener('DOMContentLoaded', function() {
	//	일단 토큰 넣기
	const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
        
    // 입력 날짜 저장하는 변수
    let rememberedStartDate = '';
    let rememberedEndDate = '';
    
    //	초기 페이지 로드를 위한 리스트 받기
    fetch('http://localhost:8080/Inventory/branch/initialList')
    .then(response => response.json())
    .then(data => {
		createTable(data);
	}).catch(error => console.error(error));
	
    // orderBy 설정 로직
    let orderByInput = document.getElementById('orderBy');
    let orderBy = orderByInput.value || '';
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
        document.getElementById('search-form').dispatchEvent(new Event('submit'));
    }
    // 정렬 초기화 버튼 이벤트
    document.getElementById('resetOrderBy').addEventListener('click', function() {
        orderByInput.value = '';
        document.getElementById('search-form').dispatchEvent(new Event('submit'));
    });
    
	// Ajax요청 List 받기 함수
	document.getElementById('search-form').addEventListener('submit', function(event) {
		event.preventDefault();
		
		let form = document.getElementById('search-form');
		let formData = new FormData(form);
		
		let startDateValue = document.getElementById('startDate').value;
		if (startDateValue) {
		    formData.append('startDate', startDateValue);
		    rememberedStartDate = startDateValue; // 값을 기억
		} else {
		    formData.append('startDate', ''); // 날짜가 없으면 빈 문자열을 서버에 전송
		    rememberedStartDate = ''; // 값을 초기화
		}
		let endDateValue = document.getElementById('endDate').value;
		if(endDateValue){
		    formData.append('endDate', endDateValue);
		    rememberedEndDate = endDateValue;
		} else {
		    formData.append('endDate', '');
		    rememberedEndDate = '';
		}
		
		fetch('http://localhost:8080/Inventory/branch/search', {
		    method: 'POST',
		    headers: {
		        'Content-Type': 'application/x-www-form-urlencoded',
		        [csrfHeader]: csrfToken
		    },
		    body: new URLSearchParams(formData).toString()
		})
		.then(response => response.json())
		.then(data => createTable(data))
		.catch(error => console.error(error));
	})
	
	// 검색어 초기화 로직
    window.resetKeyword = function() {
        document.querySelector('input[name="keyword"]').value = '';
        document.getElementById('search-form').dispatchEvent(new Event('submit')); // 폼 제출 이벤트를 강제로 발생
    };
	
	// Table toggle 버튼 이벤트 추가
    document.getElementById('toggleTable').addEventListener('click', function() {
        let table1 = document.getElementById('inventory-table1');
        let table2 = document.getElementById('inventory-table2');
        if (table1.style.display === 'none') {
            table1.style.display = '';
            table2.style.display = 'none';
        } else {
            table1.style.display = 'none';
            table2.style.display = '';
        }
    });
	
	// List로 table 만들어주는 함수
	function createTable(data){
		const table1 = document.getElementById('inventory-table1');
        const table2 = document.getElementById('inventory-table2');
        table1.innerHTML = '';
        table2.innerHTML = '';

        table1.innerHTML = `
            <thead>
                <tr>
                    <th rowspan="2" class = "mordan">번호</th>
                    <th onclick="updateOrderBy('kindcode')" rowspan="2" class = "mordan">분류
                        ${orderBy.includes('kindcode asc') ? '▲' : orderBy.includes('kindcode desc') ? '▼' : ''}
                    </th>
                    <th onclick="updateOrderBy('bookName')" rowspan="2" class = "mordan">책 이름
                        ${orderBy.includes('bookName asc') ? '▲' : orderBy.includes('bookName desc') ? '▼' : ''}
                    </th>
                    <th onclick="updateOrderBy('price')" rowspan="2" class = "mordan">가격
                        ${orderBy.includes('price asc') ? '▲' : orderBy.includes('price desc') ? '▼' : ''}
                    </th>
                    <th onclick="updateOrderBy('inventory')" rowspan="2" class = "mordan">현재 재고
                        ${orderBy.includes('inventory asc') ? '▲' : orderBy.includes('inventory desc') ? '▼' : ''}
                    </th>
                    <th rowspan="2" class = "mordan">재고*가격</th>
                    <th onclick="updateOrderBy('inDate')" rowspan="2" class = "mordan">최근 입고일
                        ${orderBy.includes('inDate asc') ? '▲' : orderBy.includes('inDate desc') ? '▼' : ''}
                    </th>
                    <th onclick="updateOrderBy('outDate')" rowspan="2" class = "mordan">최근 출고일
                        ${orderBy.includes('outDate asc') ? '▲' : orderBy.includes('outDate desc') ? '▼' : ''}
                    </th>
                </tr>
            </thead>
            <tbody id="table-body1">
            </tbody>
        `;
        
        table2.innerHTML = `
            <thead>
                <tr>
                    <th rowspan="2" class = "mordan">번호</th>
                    <th onclick="updateOrderBy('kindcode')" rowspan="2" class = "mordan">분류
                        ${orderBy.includes('kindcode asc') ? '▲' : orderBy.includes('kindcode desc') ? '▼' : ''}
                    </th>
                    <th onclick="updateOrderBy('bookName')" rowspan="2" class = "mordan">책 이름
                        ${orderBy.includes('bookName asc') ? '▲' : orderBy.includes('bookName desc') ? '▼' : ''}
                    </th>
                    <th onclick="updateOrderBy('price')" rowspan="2" class = "mordan">가격
                        ${orderBy.includes('price asc') ? '▲' : orderBy.includes('price desc') ? '▼' : ''}
                    </th>
                    <th onclick="updateOrderBy('inventory')" rowspan="2" class = "mordan">현재 재고
                        ${orderBy.includes('inventory asc') ? '▲' : orderBy.includes('inventory desc') ? '▼' : ''}
                    </th>
                    <th class="date-input-th" colspan="2">
                        <input type="date" id="startDate" />
                    </th>
                    <th class="date-input-th" colspan="2">
                        <input type="date" id="endDate" />
                    </th>
                </tr>
                <tr>
                    <th class = "sita">시작 재고</th>
                    <th class = "sita">입고 총합</th>
                    <th class = "sita">출고 총합</th>
                    <th class = "sita">예상 재고</th>
                </tr>
            </thead>
            <tbody id="table-body2">
            </tbody>
        `;

        const tbody1 = table1.querySelector('#table-body1');
        const tbody2 = table2.querySelector('#table-body2');
        tbody1.innerHTML = '';
        tbody2.innerHTML = '';
        const numberFormatter = new Intl.NumberFormat('ko-KR', { style: 'decimal', minimumFractionDigits: 0 });
        data.forEach((item, index) => {
            let row1 = document.createElement('tr');
            let row2 = document.createElement('tr');
            row1.innerHTML = `
                <td>${index + 1}</td>
                <td>${getKindCode(item.kindCode)}</td>
                <td>${item.bookName}</td>
                <td>${numberFormatter.format(item.price)}</td>
                <td><strong>${numberFormatter.format(item.inventory)}</strong></td>
                <td>${numberFormatter.format(item.inventory * item.price)}</td>
                <td>${item.inDate}</td>
                <td>${item.outDate}</td>
            `;

            row2.innerHTML = `
                <td>${index + 1}</td>
                <td>${getKindCode(item.kindCode)}</td>
                <td>${item.bookName}</td>
                <td>${numberFormatter.format(item.price)}</td>
                <td><strong>${numberFormatter.format(item.inventory)}</strong></td>
                <td>${numberFormatter.format(item.startInventory)}</td>
                <td>${numberFormatter.format(item.sumInInventory)}</td>
                <td>${numberFormatter.format(item.sumOutInventory)}</td>
                <td>${numberFormatter.format(item.startInventory + item.sumInInventory - item.sumOutInventory)}</td>
            `;

            tbody1.appendChild(row1);
            tbody2.appendChild(row2);
        });
	
		document.getElementById('startDate').value = rememberedStartDate;
		document.getElementById('endDate').value = rememberedEndDate;
	
		document.getElementById('startDate').addEventListener('change', function() {
			document.getElementById('search-form').dispatchEvent(new Event('submit'));
		});
		document.getElementById('endDate').addEventListener('change', function(){
			document.getElementById('search-form').dispatchEvent(new Event('submit'));
		})
	
	}
	document.getElementById('inventory-table2').style.display = 'none';
	
})

function getKindCode(kindCode) {
    switch (parseInt(kindCode, 10)) {
        case 1: return '초등';
        case 2: return '중등';
        case 3: return '고등';
        case 4: return '수능';
        default: return '몰라';
    }
}