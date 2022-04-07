let ur= window.location.href;
let arr = ur.split("/");
let ss=arr[2];
let ws = null;
let url = "ws://"+ss+"/echo";



function setConnected(connected)
{
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('echo').disabled = !connected;
}

function connect()
{
    ws=new WebSocket(url);

    ws.onopen = function() {
        setConnected(true);
        log('Info: Connection Established.');
    };

    ws.onmessage = function(event) {
        log(event.data);
    };

    ws.onerror =  function(err) {
        let ris=(JSON.stringify(err, ["code"]));
        if (ws!=null) log('Info:  Connection Error. '+ris)
        else log('Info: Closing Connection.');
        if (ws!=null) setTimeout(function(){connect()}, 2000);
    };

    ws.onclose = function(event) {
        ws=null;
        setConnected(false);
        log('Info: Closing Connection.');
    };
}

function disconnect()
{
    if (ws != null) {
        ws.close();
        ws = null;
    }
    setConnected(false);
}

function echo()
{
    if (ws != null)
    {
        var message = document.getElementById('message').value;
        log('Sent to server :: ' + message);
        ws.send(message);
    } else {
        alert('connection not established, please connect.');
    }
}

function log(message)
{
    document.getElementById('message').value=message;
}