var MaxNumSessionUsers = 3;
var webSocket = new WebSocket("ws://192.168.43.175:8080/projectXTomCatServer/serverendpoint");
var div = document.getElementById("GameSessionsDiv");
var query = getQueryParams(document.location.search);
var UserID = query.vsrtdyfugfdgsfd;
webSocket.onopen = function (message) { processOpen(message); };
webSocket.onclose = function (message) { processClose(message); };
webSocket.onerror = function (message) { processError(message); };
webSocket.onmessage = function (message) { processMessage(message); };
function processOpen(message) {
    webSocket.send("GET_GAME_SESSIONS:::::::::-::::::::::::::::-:::::::::::-:::::-::::null");
}
function processClose(message) {
}
function processError(message) {
}
function processMessage(message) {
    var data = message.data.split(",");
    div.innerHTML = "";
    if (data[0] == 0) {
        div.innerHTML = "<input onclick='JoingSession(" + data[0] + "," + data[0] + ");' value='Session " + 0 + " - (" + data[0] + "/" + MaxNumSessionUsers + ")' type='button'><br/>";
    }
    for (i = 0; i < data[0]; i++) {
        div.innerHTML += "<input onclick='JoingSession(" + i + "," + data[i + 1] + ");' value='Session " + i + " - (" + data[i + 1] + "/" + MaxNumSessionUsers + ")' type='button'><br/>";
    }
    if (data[data.length - 2] == MaxNumSessionUsers) {
        if (data[0] == 1) {
            div.innerHTML += "<input onclick='JoingSession(" + data[0] + "," + 0 + ");' value='Session " + (data[0]) + " - (" + 0 + "/" + MaxNumSessionUsers + ")' type='button'><br/>";
        }
        else {
            div.innerHTML += "<input onclick='JoingSession(" + data[0] + "," + data[data.length - 2] + ");' value='Session " + (data[0] - 1) + " - (" + data[data.length - 2] + "/" + MaxNumSessionUsers + ")' type='button'><br/>";
        }
    }

}
function JoingSession(GameSessionID, NumOfSessionUsers) {

    if (NumOfSessionUsers == MaxNumSessionUsers) {
        alert("The session you chose is full......");
    }
    else {
        window.location = 'gameSession.aspx?feawtrsrgbdfsfrvteastd=' + GameSessionID + '&vsrtdyfugfdgsfd=' + UserID;
    }
}
function getQueryParams(qs) {
    qs = qs.split('+').join(' ');
    var params = {},
        tokens,
        re = /[?&]?([^=]+)=([^&]*)/g;
    while (tokens = re.exec(qs)) {
        params[decodeURIComponent(tokens[1])] = decodeURIComponent(tokens[2]);
    }
    return params;
}