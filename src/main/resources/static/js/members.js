const API = 'http://localhost:8080/api';

function showAlert(id, msg, type) {
    const el = document.getElementById(id);
    el.textContent = msg;
    el.className = `alert alert-${type}`;
    el.style.display = 'block';
    setTimeout(() => el.style.display = 'none', 3000);
}

async function loadMembers() {
    const res = await fetch(`${API}/members`);
    const members = await res.json();
    renderMembers(members);
}

function renderMembers(members) {
    const tbody = document.getElementById('membersTable');
    if (members.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" style="text-align:center">No members found</td></tr>';
        return;
    }
    tbody.innerHTML = members.map(m => `
        <tr>
            <td>${m.id}</td>
            <td>${m.name}</td>
            <td>${m.email}</td>
            <td>${m.phone}</td>
            <td>${m.joinDate}</td>
            <td><span class="badge ${m.status === 'ACTIVE' ?
                'badge-success' : 'badge-danger'}">${m.status}</span></td>
            <td>
                <button class="btn btn-danger btn-sm"
                    onclick="deleteMember(${m.id})">Delete</button>
            </td>
        </tr>`).join('');
}

async function addMember() {
    const member = {
        name: document.getElementById('name').value,
        email: document.getElementById('email').value,
        phone: document.getElementById('phone').value
    };

    if (!member.name || !member.email || !member.phone) {
        showAlert('memberAlert', 'Please fill all fields!', 'error');
        return;
    }

    const res = await fetch(`${API}/members`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(member)
    });

    if (res.ok) {
        showAlert('memberAlert', 'Member added successfully!', 'success');
        document.getElementById('name').value = '';
        document.getElementById('email').value = '';
        document.getElementById('phone').value = '';
        loadMembers();
    } else {
        showAlert('memberAlert', 'Error adding member!', 'error');
    }
}

async function deleteMember(id) {
    if (!confirm('Delete this member?')) return;
    await fetch(`${API}/members/${id}`, { method: 'DELETE' });
    loadMembers();
}

async function searchMembers() {
    const q = document.getElementById('searchInput').value;
    if (!q) { loadMembers(); return; }
    const res = await fetch(`${API}/members/search?name=${q}`);
    const members = await res.json();
    renderMembers(members);
}

loadMembers();