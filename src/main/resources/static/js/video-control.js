const baseUrl = video_service.resource;//"/video/resource/watch/";
var stompClient = null;
var yourUid = "";
//預定使用UID進行影音同步隔離
$(document).ready(function() {
	fetch(user_service.def)
	.then(function(response) {
		//console.log(response)
		return response.json();
	})
	.then(function(myJson) {
		if(myJson.uid){
			yourUid = myJson.uid;
			connect();
		}
	});
	//videoControlInit();
});
var videoRow = null;


function videoControlInit(videoSrc, videoType, subtitleSrc){
	videojs.log.history.enable();
	console.log(videoSrc);
	let videoJsConfig = {
		controls: true,
		nativeControlsForTouch: false,
		width: 1280,
        height: 720,
        fluid: true,
        plugins: {

        },
        sources: [{"type": videoType, "src": videoSrc}]
	};
	if(subtitleSrc != null && subtitleSrc != ""){
		videoJsConfig.plugins["ass"] = {
	        'src': [subtitleSrc],//"subs/OuterScienceSubs.ass"
	        'delay': -0.1,
	      };
    }

	videoRow = videojs("video-screen",videoJsConfig);
	videoRow.on("play",function(){
		sendAction();
	});
	videoRow.on("pause",function(){
		sendAction();
	});
}
function videoReload(source){
	console.log(source);
	if(videoRow != null){
		videoRow.src(source);
	}
}
//傳送訊息方法
function sendAction() {
	let actionRow = {id: "", action: 0, timestamp: 0, userUid: yourUid, vuid: ""};
	let timestamp = videoRow.currentTime();
	actionRow.timestamp = timestamp;
	actionRow.action = (videoRow.paused() ? 0 : 1);
    stompClient.send("/channelControl", {}, JSON.stringify(actionRow));
}
//傳送訊息方法
function sendChangeAction(videoUid) {
	console.log("change");
	let actionRow = {id: "", action: 0, timestamp: 0, userUid: yourUid, videoUid: videoUid};
	actionRow.action = 2;
	console.log(actionRow);
    stompClient.send("/channelControl", {}, JSON.stringify(actionRow));
}
//顯示接收回來的訊息方法
function showConversation(actionRow) {
	if(actionRow){
		if(actionRow.action == 0){
			videoRow.pause();
		}else if(actionRow.action == 1){
			videoRow.play();
			if(videoRow.muted()){
				delyUnmuted();
			}
		}else if(actionRow.action == 2){
			console.log(actionRow)
			let videoUid = actionRow.videoUid;
			console.log(`videoUid: ${videoUid}`)
			fetch(`${baseUrl}${videoUid}`,{method:"get"})
            .then(function(response) {
                return response.json();
            })
            .then(function(resultJson) {
                videoReload({type: "video/mp4",src: resultJson.item.path.replace("/o/","/")});
            });
		}
		videoRow.currentTime(actionRow.timestamp)
	}
}
function delyUnmuted(){
	setTimeout(() => videoRow.muted(false), 1000);
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected); //將id = connect 按鈕屬性改為disabled
    $("#disconnect").prop("disabled", !connected);  //將id = disconnect 按鈕屬性改為disabled
    if (connected) {
        $("#conversation").show();  //當連接上Websocket id = conversation 顯示
    }
    else {
        $("#conversation").hide(); // 將 conversation隱藏
    }
    $("#chatRoom").html(""); //將chatRoom內容清空
}
//連結WebSocket方法
function connect() {
    var socket = new SockJS('/endpointVideoRoom'); //建立一個socket物件 名稱為:/endpointChatRoom
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        stompClient.subscribe(`/room/getResponse/`, function (response) {
			/*console.log("response")
        	console.log(response)*/
            showConversation(JSON.parse(response.body)); //
        });
    });
}

//關閉WebSocket方法
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendAction(); });
});
