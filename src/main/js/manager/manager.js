// Helper for storing the base URL for requests.
const url = "http://localhost:8080";

// Load all of the courses.
window.onload = async function()
{
	await fetch(`${url}/courses`)
	.then((response) => response.json())
	.then((data) => {
		console.log(data);
	});
};
