const BASE_URL = window.location.origin;

/* ======================
   LOGIN FUNCTION
====================== */

async function login() {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch(BASE_URL + "/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            emailid: email,
            password: password
        })
    });

    const text = await response.text();
    const data = text ? JSON.parse(text) : {};

    // save JWT token automatically
    localStorage.setItem("token", data.token);
    localStorage.setItem("userId", data.userId);
    const roles = Array.isArray(data.role) ? data.role : [];
    const role = roles.includes("ROLE_ADMIN")
        ? "ROLE_ADMIN"
        : roles.includes("ROLE_SALON")
            ? "ROLE_SALON"
            : "ROLE_USER";
    localStorage.setItem("role", role);

    alert("Login Success");

    window.location.href = "user_dashboard.html";
}

/* ======================
   AUTO JWT ATTACH
====================== */

async function apiFetch(url, options = {}) {

    const token = localStorage.getItem("token");

    options.headers = {
        ...options.headers,
        "Authorization": "Bearer " + token,
        "Content-Type": "application/json"
    };

    return fetch(BASE_URL + url, options);
}

/* ======================
   SAMPLE SECURED API
====================== */

async function loadUsers() {

    const response = await apiFetch("/users");

    const users = await response.json();

    const ul = document.getElementById("users");
    ul.innerHTML = "";

    users.forEach(u => {
        const li = document.createElement("li");
        li.innerText = u.name;
        ul.appendChild(li);
    });
}
