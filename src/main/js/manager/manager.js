import { parseJWT } from "/login.js";

// Helper for storing the base URL for requests.
const url = "http://localhost:8080";

// Load all of the reimbursements.
window.onload = async function()
{
	console.log(document.cookie);
	console.log(parseJWT(document.cookie))

	await fetch(`${url}/reimbursements`, {
		headers: {"Authorization":`Bearer ${document.cookie}`}
	})
	.then((response) => response.json())
	.then((data) => {
		console.log(data);

		for(let reimbursement of data) {
			let row = document.createElement("tr");

			let cells = [];

			cells.push(document.createElement("td"));
			cells.push(document.createElement("td"));
			cells.push(document.createElement("td"));
			cells.push(document.createElement("td"));
			cells.push(document.createElement("td"));

			cells[0].innerText = reimbursement.id;
			cells[1].innerText = reimbursement.amount;
			cells[2].innerText = reimbursement.description;
			cells[3].innerText = reimbursement.status;

			let link = document.createElement("a");
			link.setAttribute("href", reimbursement.links.user);
			link.innerText = "View";
			cells[4].appendChild(link);

			row.appendChild(cells[0]);
			row.appendChild(cells[1]);
			row.appendChild(cells[2]);
			row.appendChild(cells[3]);
			row.appendChild(cells[4]);

			document.getElementById("reimbursementsBody").appendChild(row);
		}
	});
};

async function createReimbursement()
{
	let reimbursement = {
		name: document.getElementById("reimbursementAmount").value,
		name: document.getElementById("reimbursementDescription").value,
	};

	console.log(reimbursement);

	await fetch(`${url}/reimbursements`, {
		method: "POST",
		headers: {"Content-Type":"application/json"},
		body: JSON.stringify(reimbursement)
	})
	.then(response => response.json())
	.then(data => {
		console.log(data);
	});
};
