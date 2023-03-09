const form = document.querySelector('#video-form');
const videoDiv = document.querySelector('#video-player');
const videoScreen = document.querySelector('#video-screen');
const videoSource = document.querySelector('#video-source');
const queryParams = Object.fromEntries(new URLSearchParams(window.location.search));
let videoName = queryParams.video;
let baseSrc = "/video/resource/watch/"
let submitUrl = "/video/";
if(document.location.href.indexOf("video.html") != -1){
	videoName = videoName.substring(0,videoName.indexOf("."));
}
console.log(videoName)
fetch('/video/all')
    .then(result => result.json())
    .then(result => {
        const myVids = document.querySelector('#your-videos');
        if(result.length > 0){
			console.log(result)
            for(let videoRow of result){
                const li = document.createElement('LI');
                const link = document.createElement('A');
                let videoFilename = `${videoRow.uid}`;
                link.innerText = videoRow.name;
                link.href = window.location.origin + window.location.pathname + '?video=' + videoFilename;
                li.appendChild(link);
                myVids.appendChild(li);
            }
        }else{
            myVids.innerHTML = 'No videos found';
        }
    });
if(queryParams.video){
	videoSource.src = `${baseSrc}${queryParams.video}`;
	console.log(videoSource.src)
    videoDiv.style.display = 'block';
    console.log("TEST")
    /*document.querySelector('#now-playing')
        .innerText = 'Now playing ' + queryParams.video;*/
}
form.addEventListener('submit', ev => {
	//"http://localhost:8080/video"
	console.log(submitUrl)
    ev.preventDefault();
    let data = new FormData(form);
    fetch(submitUrl, {
        method: 'POST',
        body: data
    }).then(result => result.text()).then(_ => {
        //window.location.reload();
    });
    
});



fetch('/user/')
.then(result => {
	let promise = result.json()
	promise.then(userRow => {
		if(userRow.success){
			console.log(userRow.uid);
			console.log(userRow.username);
		}else{
			console.log(userRow.message);
		}
	});
})
.then(result => {
	console.log("user then 2")
    console.log(result)
    //console.log(result.json())
});



