const API_URL = "http://localhost:8080/api/orders";

async function loadOrders() {
    const response = await fetch(API_URL);
    const orders = await response.json();
    displayOrders(orders);
}

async function filterOrders() {
    const startDate = document.getElementById("filterStartDate").value;
    const endDate = document.getElementById("filterEndDate").value;

    if (!startDate || !endDate) {
        alert("Please select both start and end dates for filtering.");
        return;
    }

    const formattedStartDate = formatDateTime(startDate, "00:00:00");
    const formattedEndDate = formatDateTime(endDate, "23:59:59");

    const apiUrl = `${API_URL}/filter?startDate=${formattedStartDate}&endDate=${formattedEndDate}`;

    console.log("API URL for filtering orders:", apiUrl);

    try {
        const response = await fetch(apiUrl);

        if (!response.ok) {
            alert("Error fetching filtered orders.");
            return;
        }

        const filteredOrders = await response.json();
        displayOrders(filteredOrders);
    } catch (error) {
        console.error("Error fetching orders:", error);
    }
}

function formatDateTime(inputDate, time) {
    const date = new Date(inputDate);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}T${time}`;
}

function displayOrders(orders) {
    const tableBody = document.querySelector("#ordersTable tbody");
    tableBody.innerHTML = "";

    orders.forEach(order => {
        const row = `<tr>
            <td>${order.id}</td>
            <td>${order.viewer ? order.viewer.name : "N/A"}</td>
            <td>${order.ticket ? order.ticket.movieName : "N/A"}</td>
            <td>${order.orderDate}</td>
        </tr>`;
        tableBody.insertAdjacentHTML("beforeend", row);
    });
}

document.addEventListener("DOMContentLoaded", loadOrders);
