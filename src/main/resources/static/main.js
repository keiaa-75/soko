async function fetchProducts(params = '') {
    try {
        const res = await fetch('/api/products' + params);
        if (!res.ok) throw new Error(`Failed to fetch products: ${res.statusText}`);
        const products = await res.json();
        const tbody = document.querySelector('#productsTable tbody');
        const rows = products.map(prod => `
                <tr class="product-row" onclick="showEditModal(${prod.id})">
                    <td>${prod.itemId}</td>
                    <td>${prod.itemName}</td>
                    <td>${prod.itemQty}</td>
                    <td>${prod.itemPrice.toFixed(2)}</td>
                    <td>${prod.itemCategory}</td>
                </tr>
            `).join('');
        tbody.innerHTML = rows;
    } catch (error) {
        console.error("Error fetching products:", error);
        // Optionally, display an error message to the user on the page
        const tbody = document.querySelector('#productsTable tbody');
        tbody.innerHTML = `<tr><td colspan="6" class="text-center text-danger">Could not load products. Please try again later.</td></tr>`;
    }
}

async function addProduct(e) {
    e.preventDefault();
    const itemId = document.getElementById('itemId').value;
    const itemName = document.getElementById('name').value;
    const itemQty = document.getElementById('quantity').value;
    const itemPrice = document.getElementById('price').value;
    const itemCategory = document.getElementById('category').value;
    try {
        await fetch('/api/products', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({itemId, itemName, itemQty, itemPrice, itemCategory})
        });
        e.target.reset();
        applyFiltersAndFetch(); // Refresh with current filters
    } catch (error) {
        console.error("Error adding product:", error);
        alert("Failed to add product. Please check the console for details.");
    }
}

async function deleteProduct(id) {
    // The browser confirm is removed. Confirmation is now handled by a Bootstrap modal.
    await fetch(`/api/products/${id}`, { method: 'DELETE' });
    applyFiltersAndFetch(); // Refresh with current filters
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

function buildFilterQueryString() {
    const params = new URLSearchParams();

    const name = document.getElementById('searchInput').value;
    if (name) params.append('name', name);

    const sortValue = document.getElementById('sortSelect').value;
    if (sortValue) {
        const [sortBy, sortOrder] = sortValue.split('_');
        params.append('sortBy', sortBy);
        params.append('sortOrder', sortOrder);
    }

    const category = document.getElementById('categoryFilterSelect').value;
    if (category) params.append('category', category);

    return params.toString();
}

function applyFiltersAndFetch() {
    const queryString = buildFilterQueryString();
    fetchProducts(queryString ? `?${queryString}` : '');
}

function exportToCsv() {
    const queryString = buildFilterQueryString();
    window.location.href = `/api/products/export${queryString ? `?${queryString}` : ''}`;
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
    document.getElementById('exportBtn').addEventListener('click', exportToCsv);

    document.getElementById('deleteProductFromModalBtn').addEventListener('click', () => {
        const id = document.getElementById('editProductId').value;
        if (!id) return;

        // Get modal instances
        const editModalEl = document.getElementById('editProductModal');
        const editModal = bootstrap.Modal.getInstance(editModalEl);
        const deleteConfirmModal = new bootstrap.Modal(document.getElementById('deleteConfirmModal'));

        // Pass the product ID to the confirmation modal's delete button
        document.getElementById('confirmDeleteBtn').dataset.productId = id;
        
        // Hide the edit modal and show the confirmation modal
        if (editModal) editModal.hide();
        deleteConfirmModal.show();
    });

    document.getElementById('confirmDeleteBtn').addEventListener('click', (e) => {
        const id = e.target.dataset.productId;
        if (!id) return;

        const deleteConfirmModal = bootstrap.Modal.getInstance(document.getElementById('deleteConfirmModal'));
        deleteProduct(id);
        if (deleteConfirmModal) deleteConfirmModal.hide();
    });

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