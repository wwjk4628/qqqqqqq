document.addEventListener("DOMContentLoaded", function() {
    let orderByInput = document.getElementById('orderBy');
    let orderBy = orderByInput.value || '';
    
    window.updateOrderBy = function(field) {
        let orderByArray = orderBy.split(',').map(s => s.trim()).filter(s => s);
        let fieldIndex = orderByArray.findIndex(s => s.startsWith(field + ' '));

		console.log(fieldIndex);
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
        document.getElementById('search-form').submit();
    }
    
    document.getElementById('resetOrderBy').addEventListener('click', function() {
        orderByInput.value = '';
        document.getElementById('search-form').submit();
    });
});
