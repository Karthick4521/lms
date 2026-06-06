const API = 'http://localhost:8080/api';

function showAlert(id, msg, type) {
    const el = document.getElementById(id);
    el.textContent = msg;
    el.className = `alert alert-${type}`;
    el.style.display = 'block';
    setTimeout(() => el.style.display = 'none', 3000);
}

async function loadDropdowns() {
    const [members, books] = await Promise.all([
        fetch(`${API}/members`).then(r => r.json()),
        fetch(`${API}/books/available`).then(r => r.json())
    ]);

    const memberSel = document.getElementById('memberId');
    members.forEach(m => {
        const opt = document.createElement('option');
        opt.value = m.id;
        opt.textContent = `${m.name} (ID: ${m.id})`;
        memberSel.appendChild(opt);
    });

    const bookSel = document.getElementById('bookId');
    books.forEach(b => {
        const opt = document.createElement('option');
        opt.value = b.id;
        opt.textContent = `${b.title} - ${b.author} (Available: ${b.availableCopies})`;
        bookSel.appendChild(opt);
    });
}

async function loadBorrowRecords() {
    const res = await fetch(`${API}/borrow`);
    const records = await res.json();
    const tbody = document.getElementById('borrowTable');

    if (records.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" style="text-align:center">No records found</td></tr>';
        return;
    }

    tbody.innerHTML = records.reverse().map(r => `
        <tr>
            <td>${r.id}</td>
            <td>${r.book.title}</td>
            <td>${r.member.name}</td>
            <td>${r.borrowDate}</td>
            <td>${r.dueDate}</td>
            <td>${r.returnDate || '-'}</td>
            <td><span class="badge ${r.status === 'RETURNED' ? 'badge-success' :
                r.status === 'OVERDUE' ? 'badge-danger' : 'badge-warning'}">
                ${r.status}</span></td>
            <td>
                ${r.status === 'BORROWED' ?
                `<button class="btn btn-success btn-sm"
                    onclick="returnBook(${r.id})">Return</button>` : '-'}
            </td>
        </tr>`).join('');
}

async function issueBook() {
    const bookId = document.getElementById('bookId').value;
    const memberId = document.getElementById('memberId').value;

    if (!bookId || !memberId) {
        showAlert('borrowAlert', 'Please select both member and book!', 'error');
        return;
    }

    const res = await fetch(
        `${API}/borrow/issue?bookId=${bookId}&memberId=${memberId}`,
        { method: 'POST' }
    );

    if (res.ok) {
        showAlert('borrowAlert', 'Book issued successfully!', 'success');
        loadBorrowRecords();
    } else {
        const msg = await res.text();
        showAlert('borrowAlert', msg || 'Error issuing book!', 'error');
    }
}

async function returnBook(recordId) {
    const res = await fetch(`${API}/borrow/return/${recordId}`,
        { method: 'PUT' });

    if (res.ok) {
        showAlert('borrowAlert', 'Book returned successfully!', 'success');
        loadBorrowRecords();
    } else {
        showAlert('borrowAlert', 'Error returning book!', 'error');
    }
}

loadDropdowns();
loadBorrowRecords();