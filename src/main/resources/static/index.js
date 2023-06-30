// Helper for storing the base URL for requests.
const url = "http://localhost";

const loginDiv = document.getElementById("loginDiv");
const registerDiv = document.getElementById("registerDiv");

let toggleLogin = document.getElementById("toggleLogin");
if(toggleLogin) toggleLogin.addEventListener("click", () => {
	loginDiv.setAttribute("disabled", "");
	registerDiv.removeAttribute("disabled");
});

let toggleRegister = document.getElementById("toggleRegister");
if(toggleRegister) toggleRegister.addEventListener("click", () => {
	loginDiv.removeAttribute("disabled");
	registerDiv.setAttribute("disabled", "");
});

let loginButton = document.getElementById("loginButton");
if(loginButton) loginButton.addEventListener("click", async () => {
	loginButton.setAttribute("disabled", "");

	let username = document.getElementById("loginUsername").value;
	let password = document.getElementById("loginPassword").value;
	let loginDTO = { username:username, password:password };

	console.log(loginDTO);

	// Label the fetch "await" to pause execution of the function until the call returns.
	await fetch(`${url}/api/auth/login`, {
		method: "POST",
		headers: {"Content-Type":"application/json"},
		body: JSON.stringify(loginDTO)
	})
	.then((response) => response.json())
	.then((data) => {
		console.log(data);
		console.log(data.accessToken);
		let claims = parseJWT(data.accessToken);
		console.log(claims);
		document.cookie = data.accessToken;
		if(claims.role === "Manager") {
			// Redirect to the Manager page.
			window.location.href = "/dashboard";
		} else {
			// Redirect to the Customer page.
			window.location.href = "/dashboard/customer";
		}
	}).catch(() => {
		loginButton.removeAttribute("disabled");
		document.getElementById("loginPassword").setAttribute("error", "");
	});
});

let registerUsername = document.getElementById("registerUsername");
if(registerUsername) registerUsername.addEventListener("input", async () => {
	if(document.getElementById("registerUsername").value.length === 0) {
		document.getElementById("labelAvailable").setAttribute("disabled", "");
		document.getElementById("labelTaken").setAttribute("disabled", "");
		return;
	}
	await fetch(`${url}/api/users/exists`, {
		method: "POST",
		headers: {"Content-Type":"text/plain"},
		body: document.getElementById("registerUsername").value
	})
	.then((response) => response.text())
	.then((exists) => {
		console.log(exists);
		if(parseInt(exists)) {
			document.getElementById("labelAvailable").setAttribute("disabled", "");
			document.getElementById("labelTaken").removeAttribute("disabled");
		} else {
			document.getElementById("labelAvailable").removeAttribute("disabled");
			document.getElementById("labelTaken").setAttribute("disabled", "");
		}
	})
});

let registerButton = document.getElementById("registerButton");
if(registerButton) registerButton.addEventListener("click", async () => {
	registerButton.setAttribute("disabled", "");

	let registerDTO = {
		firstName:document.getElementById("registerFirstName").value,
		lastName:document.getElementById("registerLastName").value,
		username:document.getElementById("registerUsername").value,
		password:document.getElementById("registerPassword").value
	};

	await fetch(`${url}/api/auth/register`, {
		method: "POST",
		headers: {"Content-Type":"application/json"},
		body: JSON.stringify(registerDTO)
	})
	.then((response) => response.json())
	.then((data) => {
		console.log(data);
		console.log(data.accessToken);
		let claims = parseJWT(data.accessToken);
		console.log(claims);
		document.cookie = data.accessToken;
		if(claims.role === "Manager") {
			// Redirect to the Manager page.
			window.location.href = "/dashboard";
		} else {
			// Redirect to the Customer page.
			window.location.href = "/dashboard/customer";
		}
	});
});

let reimbursements = [];
let lookupTable = [];
let filterPending = document.getElementById("filterPending");
let filterApproved = document.getElementById("filterApproved");
let filterDenied = document.getElementById("filterDenied");

window.onload = async function()
{
	console.log(document.cookie);
	console.log(parseJWT(document.cookie))

	if(filterPending) filterPending.addEventListener("click", filter);
	if(filterApproved) filterApproved.addEventListener("click", filter);
	if(filterDenied) filterDenied.addEventListener("click", filter);

	let reimbursementsBody = document.getElementById("reimbursementsBody");

	let reimbursementsURL;
	if(reimbursementsBody)
	{
		let jwt = parseJWT(document.cookie);

		if(document.getElementById("managerTable")) {
			reimbursementsURL = `${url}/api/reimbursements`;
		} else {
			reimbursementsURL = `${url}/api/users/${jwt.id}/reimbursements`;
		}
	}

	if(reimbursementsBody) await fetch(reimbursementsURL, {
		headers: {"Authorization":`Bearer ${document.cookie}`}
	})
	.then((response) => response.json())
	.then((data) => {
		console.log(data);

		let manager = document.getElementById("managerTable") != null;
		for(let reimbursement of data) {
			let row = document.createElement("div");
			row.setAttribute("class", reimbursement.status.toLowerCase());

			let cells = [];

			cells.push(document.createElement("span"));
			cells.push(document.createElement("span"));
			cells.push(document.createElement("span"));
			cells.push(document.createElement("span"));
			cells.push(document.createElement("span"));
			cells.push(document.createElement("span"));

			cells[0].innerText = reimbursement.id;
			cells[1].innerText = reimbursement.amount;
			cells[2].innerText = reimbursement.description;
			cells[3].innerText = reimbursement.status;
			cells[3].setAttribute("class", "status");

			if(manager)
			{
				let approveButton = document.createElement("button");
				approveButton.setAttribute("id", reimbursement.id);
				approveButton.setAttribute("class", "approve");
				approveButton.setAttribute("onclick", "approve(this)");
				approveButton.innerText = "Approve";

				let denyButton = document.createElement("button");
				denyButton.setAttribute("id", reimbursement.id);
				denyButton.setAttribute("class", "deny");
				denyButton.setAttribute("onclick", "deny(this)");
				denyButton.innerText = "Deny";

				if(reimbursement.status != "Pending")
				{
					approveButton.setAttribute("disabled", "");
					denyButton.setAttribute("disabled", "");
				}

				cells[4].appendChild(approveButton);
				cells[4].appendChild(denyButton);
			}

			let link = document.createElement("a");
			link.setAttribute("href", reimbursement.links.user);
			link.innerText = "View";
			cells[5].appendChild(link);

			row.appendChild(cells[0]);
			row.appendChild(cells[1]);
			row.appendChild(cells[2]);
			row.appendChild(cells[3]);
			if(manager) row.appendChild(cells[4]);
			row.appendChild(cells[5]);

			lookupTable.push({key: parseInt(reimbursement.id), value: reimbursements.length})
			reimbursements.push(row);
			reimbursementsBody.appendChild(row);
		}
	});

	if(document.getElementById("mainDashboard"))
	{
		let jwt = parseJWT(document.cookie);
		if(jwt.role === "Customer")
		{
			window.location.href = "/dashboard/customer"
		}

		document.getElementById("customerButton").addEventListener("click", () => {
			window.location.href += "/customer";
		});;
		document.getElementById("managerButton").addEventListener("click", () => {
			window.location.href += "/manager";
		});;
	}

	if(document.getElementById("customerTable"))
	{
		let jwt = parseJWT(document.cookie);
		let newButton = document.getElementById("newButton");

		document.getElementById("newAmount").addEventListener("input", () => {
			document.getElementById("newAmount").removeAttribute("error");
		});
		document.getElementById("newDescription").addEventListener("input", () => {
			document.getElementById("newDescription").removeAttribute("error");
		});

		newButton.addEventListener("click", async () => {
			newButton.setAttribute("disabled", "");

			let err = false;
			let newAmount = document.getElementById("newAmount");
			if(newAmount.value.length === 0) {
				newAmount.setAttribute("error", "");
				err = true;
			}
			let newDescription = document.getElementById("newDescription");
			if(newDescription.value.length === 0) {
				newDescription.setAttribute("error", "");
				err = true;
			}

			if(err) {
				newButton.removeAttribute("disabled");
				return;
			};

			await fetch(`/api/users/${jwt.id}/reimbursements`, {
				method: "POST",
				headers: {
					"Authorization":`Bearer ${document.cookie}`,
					"Content-Type":"application/json"
				},
				body: JSON.stringify({
					amount: parseInt(document.getElementById("newAmount").value),
					description: document.getElementById("newDescription").value
				})
			})
			.then((response) => response.json())
			.then((reimbursement) => {
				newButton.removeAttribute("disabled");
				console.log(reimbursement);
				let row = document.createElement("div");
				row.setAttribute("class", reimbursement.status.toLowerCase());

				let cells = [];

				cells.push(document.createElement("span"));
				cells.push(document.createElement("span"));
				cells.push(document.createElement("span"));
				cells.push(document.createElement("span"));
				cells.push(document.createElement("span"));

				cells[0].innerText = reimbursement.id;
				cells[1].innerText = reimbursement.amount;
				cells[2].innerText = reimbursement.description;
				cells[3].innerText = reimbursement.status;
				cells[3].setAttribute("class", "status");

				let link = document.createElement("a");
				link.setAttribute("href", reimbursement.links.user);
				link.innerText = "View";
				cells[4].appendChild(link);

				row.appendChild(cells[0]);
				row.appendChild(cells[1]);
				row.appendChild(cells[2]);
				row.appendChild(cells[3]);
				row.appendChild(cells[4]);

				lookupTable.push({key: parseInt(reimbursement.id), value: reimbursements.length})
				reimbursements.push(row);
				reimbursementsBody.insertBefore(row, reimbursementsBody.firstChild);
			});
		});
	}
};

function parseJWT(token)
{
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

async function approve(button)
{
	button.setAttribute("disabled", "");
	await fetch(`${url}/api/reimbursements/${button.id}/approve`, {
		method: "POST"
	})
	.then((response) => response.text())
	.then((data) => {
		id = parseInt(button.id);
		for(let lookup of lookupTable)
		{
			if(id === lookup.key)
			{
				let row = reimbursements[lookup.value];
				row.setAttribute("class", "approved");
				row.children[3].innerText = "Approved";
				row.children[4].children[0].setAttribute("disabled", "");
				row.children[4].children[1].setAttribute("disabled", "");
			}
		}
	});
};

async function deny(button)
{
	button.setAttribute("disabled", "");
	await fetch(`${url}/api/reimbursements/${button.id}/deny`, {
		method: "POST"
	})
	.then((response) => response.text())
	.then((data) => {
		id = parseInt(button.id);
		for(let lookup of lookupTable)
		{
			if(id === lookup.key)
			{
				let row = reimbursements[lookup.value];
				row.setAttribute("class", "denied");
				row.children[3].innerText = "Denied";
				row.children[4].children[0].setAttribute("disabled", "");
				row.children[4].children[1].setAttribute("disabled", "");
			}
		}
	});
};

function filter()
{
	for(let row of reimbursements)
	{
		if(row.className === "pending") {
			filterPending.checked ? row.removeAttribute("style") : row.setAttribute("style", "display:none;");
		}
		if(row.className === "approved") {
			filterApproved.checked ? row.removeAttribute("style") : row.setAttribute("style", "display:none;");
		}
		if(row.className === "denied") {
			filterDenied.checked ? row.removeAttribute("style") : row.setAttribute("style", "display:none;");
		}
	}
}
