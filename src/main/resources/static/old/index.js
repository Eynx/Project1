const loginDiv = document.getElementById("loginDiv");
const registerDiv = document.getElementById("registerDiv");

document.getElementById("toggleLogin").addEventListener("click", () => {
	loginDiv.setAttribute("disabled", "");
	registerDiv.removeAttribute("disabled");
});

document.getElementById("toggleRegister").addEventListener("click", () => {
	loginDiv.removeAttribute("disabled");
	registerDiv.setAttribute("disabled", "");
});
