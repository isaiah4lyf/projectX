var webSocket = new WebSocket("ws://192.168.43.175:8080/projectXTomCatServer/serverendpoint");
var query = getQueryParams(document.location.search);
var GameSessionID = 0;
var UserID = 1;
webSocket.onopen = function (message) { processOpen(message); };
webSocket.onclose = function (message) { processClose(message); };
webSocket.onerror = function (message) { processError(message); };
webSocket.onmessage = function (message) { processMessage(message); };
var JustJoin = true;
function processOpen(message) {

    webSocket.send("VIEW_POST:::::::::-::::::::::::::::-:::::::::::-:::::-::::" + GameSessionID + ":::::::::-::::::::::::::::-:::::::::::-:::::-::::" + UserID);
}
function processClose(message) {
    webSocket.send("Client disconnected........");
}
function processError(message) {
}
function processMessage(message) {
    if (message.data.startsWith(":::::::::-::::::::::::::::-:::::::::::-:::::-::::")) {
        var messagePassed = message.data.split(":::::::::-::::::::::::::::-:::::::::::-:::::-::::");
        if (messagePassed[1] == "SessionFull") {
            alert("Session Full");
            window.location = 'http://192.168.43.175:8060/GameSessions.html';
        }
        else if (messagePassed[1] == "RemainingTime") {
            document.getElementById("time").value = messagePassed[2];
        }
        else {
            if (JustJoin == true) {
                var QuestionColor = "slateblue";
                if (messagePassed[1] == 'science') {
                    QuestionColor = 'purple';
                }
                else if (messagePassed[1] == 'General') {
                    QuestionColor = 'indigo';
                }
                else if (messagePassed[1] == 'History') {
                    QuestionColor = 'forestgreen';
                }
                else if (messagePassed[1] == 'Economics') {
                    QuestionColor = 'crimson';
                }
                else if (messagePassed[1] == 'science') {
                    QuestionColor = 'mediumvioletred';
                }
                var question = "<div  style='width:100%;height:150px;background-color:silver;border-radius: 10px;opacity: 0.83;'>";
                question += "<table><tr>";
                question += "<td  style='width:70%;position:relative;left:20px'><b style='color:" + QuestionColor + ";font-size:28px;font-style: italic;'>" + messagePassed[1] + "</b><br/><br/>";
                var lables = "";
                for (var i = 0; i < messagePassed[3]; i++) {
                    lables += "<label style='border: 2px  solid " + QuestionColor + ";' class='div1'></label>";
                }

                question += "<label style='color:" + QuestionColor + ";font-size:15px;width:85%;'>" + messagePassed[2] + "</label><br/><br/>" + lables + "</td>";
                question += "<td style='width:30%'><b style='color:" + QuestionColor + ";position:relative;left:100px;'>Clue</b><br/><img style='position:relative;top:0px;left:50px;width:60%;height:110px;border-radius: 5px;' src='http://localhost/Images/" + messagePassed[4] + "'/></td></tr></table>";
                question += "</div>";
                document.getElementById("content").innerHTML += question;
                document.getElementById("content1").scrollTop = document.getElementById("content1").scrollHeight;
                JustJoin = false;
            }
        }
    }
    else {
        if (message.data.includes(":::::::::-::::::::::::::::-:::::::::::-:::::-::::")) {
            var questionTokens = message.data.split(":::::::::-::::::::::::::::-:::::::::::-:::::-::::");
            var QuestionColor = "slateblue";
            if (questionTokens[0] == 'science') {
                QuestionColor = 'purple';
            }
            else if (questionTokens[0] == 'General') {
                QuestionColor = 'indigo';
            }
            else if (questionTokens[0] == 'History') {
                QuestionColor = 'forestgreen';
            }
            else if (questionTokens[0] == 'Economics') {
                QuestionColor = 'crimson';
            }
            else if (questionTokens[0] == 'science') {
                QuestionColor = 'mediumvioletred';
            }
            var question = "<div  style='width:100%;height:150px;background-color:silver;border-radius: 10px;opacity: 0.83;'>";
            question += "<table><tr>";
            question += "<td  style='width:70%;position:relative;left:20px'><b style='color:" + QuestionColor + ";font-size:28px;font-style: italic;'>" + questionTokens[0] + "</b><br/><br/>";
            var lables = "";
            for (var i = 0; i < questionTokens[2]; i++) {
                lables += "<label style='border: 2px  solid " + QuestionColor + ";' class='div1'></label>";
            }
            question += "<label style='color:" + QuestionColor + ";font-size:15px;width:85%;'>" + questionTokens[1] + "</label><br/><br/>" + lables + "</td>";
            question += "<td style='width:30%'><b style='color:" + QuestionColor + ";position:relative;left:100px;'>Clue</b><br/><img style='position:relative;top:0px;height:110px;left:50px;width:60%;border-radius: 5px;' src='http://localhost/Images/" + questionTokens[3] + "'/></td></tr></table>";
            question += "</div><br/>";
            document.getElementById("content").innerHTML += question;
            document.getElementById("content1").scrollTop = document.getElementById("content1").scrollHeight;
        }
        else {
            //Copy the exact spliter for correct answer	
            if (message.data.includes("::::::::-::::::::::::::-:::::::::::-:::::-::::")) {
                var answerTokens = message.data.split("::::::::-::::::::::::::-:::::::::::-:::::-::::");
                document.getElementById("content").innerHTML += "<br/><label style='color:white;margin-right:5px'>Correct Answer: </label>";
                document.getElementById("content").innerHTML += "<label style='color:orange'> " + answerTokens[0] + "</label><br/>";
                document.getElementById("content").innerHTML += "<label style='color:white;margin-right:5px'>Total Points: </label>";
                document.getElementById("content").innerHTML += "<label style='color:orange'> " + answerTokens[1] + " points</label><br/>";
            }
            else {
                if (message.data.includes(":::::::-::::::::-:::::::::::-:::::::-::::")) {
                    var answerTokens = message.data.split(":::::::-::::::::-:::::::::::-:::::::-::::");
                    document.getElementById("content").innerHTML += "<label style='color:green;margin-right:5px;'>" + answerTokens[1] + "</label><i class='fa fa-check' style='color:green;font-size:16px'></i><br/>";
                }
                else if (message.data.includes(":::::-::::::::-:::::::-:::::::-::::::")) {
                    document.getElementById("content").innerHTML += "<label style='color:white'>" + message.data.split(":::::-::::::::-:::::::-:::::::-::::::")[1] + "</label><br/>";
                }
                else {
                    document.getElementById("content").innerHTML += "<label style='color:red'>" + message.data + "</label><br/>";
                }


            }

        }

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
function closeIt() {
    return "Closing or refreshing windows pop up....";
}
window.onbeforeunload = closeIt;