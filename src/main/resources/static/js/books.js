const API = 'http://localhost:8080/api';

function showAlert(id, msg, type) {
    const el = document.getElementById(id);
    el.textContent = msg;
    el.className = `alert alert-${type}`;
    el.style.display = 'block';
    setTimeout(() => el.style.display = 'none', 3000);
}

async function loadBooks() {
    const res = await fetch(`${API}/books`);
    const books = await res.json();
    renderBooks(books);
}

function renderBooks(books) {
    const tbody = document.getElementById('booksTable');
    if (books.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" style="text-align:center">No books found</td></tr>';
        return;
    }
    tbody.innerHTML = books.map(b => `
        <tr>
            <td>${b.id}</td>
            <td>${b.title}</td>
            <td>${b.author}</td>
            <td>${b.isbn}</td>
            <td>${b.category}</td>
            <td>${b.totalCopies}</td>
            <td><span class="badge ${b.availableCopies > 0 ?
                'badge-success' : 'badge-danger'}">
                ${b.availableCopies}</span></td>
            <td>
                <button class="btn btn-danger btn-sm"
                    onclick="deleteBook(${b.id})">Delete</button>
            </td>
        </tr>`).join('');
}

async function addBook() {
    const book = {
        title: document.getElementById('title').value,
        author: document.getElementById('author').value,
        isbn: document.getElementById('isbn').value,
        category: document.getElementById('category').value,
        totalCopies: parseInt(document.getElementById('totalCopies').value)
    };

    if (!book.title || !book.author || !book.isbn ||
        !book.category || !book.totalCopies) {
        showAlert('bookAlert', 'Please fill all fields!', 'error');
        return;
    }

    const res = await fetch(`${API}/books`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(book)
    });

    if (res.ok) {
        showAlert('bookAlert', 'Book added successfully!', 'success');
        document.getElementById('title').value = '';
        document.getElementById('author').value = '';
        document.getElementById('isbn').value = '';
        document.getElementById('category').value = '';
        document.getElementById('totalCopies').value = '';
        loadBooks();
    } else {
        showAlert('bookAlert', 'Error adding book!', 'error');
    }
}

async function deleteBook(id) {
    if (!confirm('Delete this book?')) return;
    await fetch(`${API}/books/${id}`, { method: 'DELETE' });
    loadBooks();
}

async function searchBooks() {
    const q = document.getElementById('searchInput').value;
    if (!q) { loadBooks(); return; }
    const res = await fetch(`${API}/books/search/title?title=${q}`);
    const books = await res.json();
    renderBooks(books);
}

loadBooks();