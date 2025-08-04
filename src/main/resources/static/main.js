async function fetchProducts(params = '') {
    const res = await fetch('/api/products' + params);
    const products = await res.json();
    const tbody = document.querySelector('#productsTable tbody');
    tbody.innerHTML = '';
    products.forEach(prod => {
        tbody.innerHTML += `
            <tr>
                <td>${prod.itemId}</td>
                <td>${prod.itemName}</td>
                <td>${prod.itemQty}</td>
                <td>${prod.itemPrice}</td>
                <td>${prod.itemCategory}</td>
                <td>
                    <div class="d-flex flex-column flex-md-row gap-2">
                        <button class="btn btn-warning btn-sm" onclick="showEditModal(${prod.id})">Edit</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteProduct(${prod.id})">Delete</button>
                    </div>
                </td>
            </tr>
        `;
    });
}

async function addProduct(e) {
    e.preventDefault();
    const itemId = document.getElementById('itemId').value;
    const itemName = document.getElementById('name').value;
    const itemQty = document.getElementById('quantity').value;
    const itemPrice = document.getElementById('price').value;
    const itemCategory = document.getElementById('category').value;
    await fetch('/api/products', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({itemId, itemName, itemQty, itemPrice, itemCategory})
    });
    e.target.reset();
    fetchProducts();
}

async function deleteProduct(id) {
    await fetch(`/api/products/${id}`, { method: 'DELETE' });
    fetchProducts();
}

// Edit modal logic (implement modal in HTML)
async function showEditModal(id) {
    const res = await fetch(`/api/products/${id}`);
    const product = await res.json();

    document.getElementById('editProductId').value = product.id;
    document.getElementById('editItemId').value = product.itemId;
    document.getElementById('editName').value = product.itemName;
    document.getElementById('editQuantity').value = product.itemQty;
    document.getElementById('editPrice').value = product.itemPrice;
    document.getElementById('editCategory').value = product.itemCategory;

    const editProductModal = new bootstrap.Modal(document.getElementById('editProductModal'));
    editProductModal.show();
}

async function saveProductChanges(e) {
    e.preventDefault();
    const id = document.getElementById('editProductId').value;
    const itemId = document.getElementById('editItemId').value;
    const itemName = document.getElementById('editName').value;
    const itemQty = document.getElementById('editQuantity').value;
    const itemPrice = document.getElementById('editPrice').value;
    const itemCategory = document.getElementById('editCategory').value;

    await fetch(`/api/products/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            itemId,
            itemName,
            itemQty,
            itemPrice,
            itemCategory
        })
    });

    const editProductModalEl = document.getElementById('editProductModal');
    const editProductModal = bootstrap.Modal.getInstance(editProductModalEl);
    editProductModal.hide();

    fetchProducts();
}

document.addEventListener('DOMContentLoaded', () => {
    fetchProducts();
    document.getElementById('searchInput').addEventListener('input', function() {
        const val = this.value;
        fetchProducts('/search?name=' + encodeURIComponent(val));
    });
    document.getElementById('sortSelect').addEventListener('change', function() {
        const value = this.value;
        const [by, order] = value.split('_');
        fetchProducts(`/sort?by=${by}&order=${order}`);
    });
    document.getElementById('addProductForm').addEventListener('submit', addProduct);
    document.getElementById('editProductForm').addEventListener('submit', saveProductChanges);
});