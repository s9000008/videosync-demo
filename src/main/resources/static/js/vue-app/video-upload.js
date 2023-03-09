const submitUrl = "/video/";
const headers = { 'Content-Type': 'multipart/form-data' };
///video/resource/watch/mao.mp4
const videoVue = new Vue({
	el: '#video-form',
	data: {
		formData: new FormData() //因為我這邊沒有使用form，所以就自行new了一個FormData
	},
	methods: {
		upload() {
			document.getElementById("loadImg").setAttribute("class","");
			document.getElementById("video-form").setAttribute("class","d-none");
			let formData = new FormData();
			formData.append("file",file.files[0]);
			formData.append("file",subtitle.files[0]);
			//formData.append("file",file.files[0]);
			let name = document.getElementById("name").value;
			formData.append("name",name);
			
			var xhr = new XMLHttpRequest();
			xhr.open('POST', video_service.save, true);
			xhr.send(formData);
			xhr.onload = function(){
			  console.log(xhr.responseText);
			  let resultJson = JSON.parse(xhr.responseText)
			  alert(resultJson.message);
			  history.go(0);
			}
		}
	}
})


