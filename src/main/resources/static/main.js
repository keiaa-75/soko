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
    applyFiltersAndFetch(); // Refresh with current filters
}

async function deleteProduct(id) {
    if (confirm('Are you sure you want to delete this product?')) {
        await fetch(`/api/products/${id}`, { method: 'DELETE' });
        applyFiltersAndFetch(); // Refresh with current filters
    }
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

    applyFiltersAndFetch(); // Refresh with current filters
}

async function populateCategories() {
    try {
        const res = await fetch('/api/products/categories');
        if (!res.ok) {
            throw new Error(`HTTP error! status: ${res.status}`);
        }
        const categories = await res.json();
        const select = document.getElementById('categoryFilterSelect');
        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category;
            option.textContent = category;
            select.appendChild(option);
        });
    } catch (error) {
        console.error("Could not fetch categories:", error);
    }
}

function applyFiltersAndFetch() {
    const params = new URLSearchParams();

    const name = document.getElementById('searchInput').value;
    if (name) {
        params.append('name', name);
    }

    const sortValue = document.getElementById('sortSelect').value;
    if (sortValue) {
        const [sortBy, sortOrder] = sortValue.split('_');
        params.append('sortBy', sortBy);
        params.append('sortOrder', sortOrder);
    }

    const category = document.getElementById('categoryFilterSelect').value;
    if (category) {
        params.append('category', category);
    }

    const queryString = params.toString();
    // Pass the built query string to fetchProducts
    fetchProducts(queryString ? `?${queryString}` : '');
}

document.addEventListener('DOMContentLoaded', () => {
    applyFiltersAndFetch(); // Initial fetch with default filters
    populateCategories();

    // Centralize event handling
    document.getElementById('searchInput').addEventListener('input', applyFiltersAndFetch);
    document.getElementById('sortSelect').addEventListener('change', applyFiltersAndFetch);
    document.getElementById('categoryFilterSelect').addEventListener('change', applyFiltersAndFetch);

    document.getElementById('addProductForm').addEventListener('submit', addProduct);
    document.getElementById('editProductForm').addEventListener('submit', saveProductChanges);
    document.getElementById('printBtn').addEventListener('click', () => window.print());

    // --- Theme Toggling Logic ---
    const themingSwitcher = document.getElementById('themingSwitcher');

    // Function to get the current theme from the <html> attribute
    const getCurrentTheme = () => document.documentElement.getAttribute('data-bs-theme');

    // Set the switch to the correct initial state based on the theme
    if (getCurrentTheme() === 'dark') {
        themingSwitcher.checked = true;
    }

    // Listen for changes on the switch
    themingSwitcher.addEventListener('change', () => {
        const newTheme = themingSwitcher.checked ? 'dark' : 'light';
        // Set the new theme on the <html> element
        document.documentElement.setAttribute('data-bs-theme', newTheme);
        // Save the user's preference to local storage
        localStorage.setItem('theme', newTheme);
    });
});