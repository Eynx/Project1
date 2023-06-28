// Helper for storing the base URL for requests.
const url = "http://localhost:8080";

// Label the lambda "async" to denote it'll return a Promise instead of directly returning a value.
let button = document.getElementById("loginButton").addEventListener("click", async () => {
	let username = document.getElementById("loginUsername").value;
	let password = document.getElementById("loginPassword").value;
	let loginDTO = { username:username, password:password };

	console.log(loginDTO);

	// Label the fetch "await" to pause execution of the function until the call returns.
	await fetch(`${url}/auth/login`, {
		method: "POST",
		headers: {"Content-Type":"application/json"},
		body: JSON.stringify(loginDTO)
	})
	.then((response) => response.json())
	.then((data) => {
		console.log(data);
		console.log(data.accessToken);
		console.log(parseJWT(data.accessToken));
		if(parseJWT(data.accessToken).role === "Manager") {
			// Redirect to the Teacher page.
			window.location.href = "manager";
		} else {
			// Redirect to the Student page.
			window.location.href = "customer";
		}
	});
});

function parseJWT(token)
{
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}
